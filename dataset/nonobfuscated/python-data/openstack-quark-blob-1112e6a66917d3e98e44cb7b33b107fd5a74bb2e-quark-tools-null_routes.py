import sys

import netaddr
from neutron.common import config
from neutron import context as neutron_context
from oslo_config import cfg
from oslo_log import log as logging
import requests
from sqlalchemy import not_

from quark.db import api as db_api
from quark.db import ip_types
from quark.db import models


CONF = cfg.CONF
LOG = logging.getLogger(__name__)
LOCK_NAME = "null-routed"

null_routes_opts = [
    cfg.StrOpt("null_routes_url",
               help=_("URL to GET null routes data")),
    cfg.StrOpt("null_routes_region",
               help=_("region to filter null routes data")),
    cfg.ListOpt("null_routes_network_ids",
                default=["00000000-0000-0000-0000-000000000000"],
                help=_("UUIDs of networks to query for null-routed IP "
                       "addresses"))
]

CONF.register_opts(null_routes_opts, "QUARK")


def main():
    config.init(sys.argv[1:])
    if not cfg.CONF.config_file:
        sys.exit(_("ERROR: Unable to find configuration file via the default"
                   " search paths (~/.neutron/, ~/, /etc/neutron/, /etc/) and"
                   " the '--config-file' option!"))
    config.setup_logging()

    
    from quark import network_strategy
    network_strategy.STRATEGY.load()

    context = neutron_context.get_admin_context()
    network_ids = cfg.CONF.QUARK.null_routes_network_ids
    ipset = get_subnets_cidr_set(context, network_ids)

    url = cfg.CONF.QUARK.null_routes_url
    region = cfg.CONF.QUARK.null_routes_region
    addresses = get_null_routes_addresses(url, region, ipset)

    delete_locks(context, network_ids, addresses)
    create_locks(context, network_ids, addresses)


def get_subnets_cidr_set(context, network_ids):
    ipset = netaddr.IPSet()
    subnets = db_api.subnet_find(context, network_id=network_ids,
                                 shared=[False])
    for subnet in subnets:
        net = netaddr.IPNetwork(subnet["cidr"])
        ipset.add(net)
    return ipset


def _make_request(url, region):
    response = requests.get(url, verify=False)
    data = response.json()

    
    assert len(data) == 1
    assert sorted(data[0].keys()) == sorted([
        "paginate", "request", "payload", "response"])
    assert sorted(data[0]["paginate"].keys()) == sorted([
        "total_count", "total_count_display", "total_pages",
        "author_comment", "per_page", "page"])
    
    
    assert len(data[0]["payload"]) == data[0]["paginate"]["total_count"]

    return data


def get_null_routes_addresses(url, region, ipset):
    data = _make_request(url, region)
    addresses = netaddr.IPSet()
    for datum in data[0]["payload"]:
        assert sorted(datum.keys()) == sorted([
            "status", "note", "updated", "name", "status_name",
            "region.id", "ip", "idql", "discovered", "netmask", "tag",
            "conf", "cidr", "id", "switch.hostname"])
        net = netaddr.IPNetwork(datum["cidr"])
        if datum["region.id"] != region or datum["status"] != "1":
            continue
        for addr in net:
            if addr in ipset:
                addresses.add(addr)
    return addresses


def _to_int(addr):
    return int(addr.ipv6())


def _find_addresses_to_be_unlocked(context, network_ids, addresses):
    addresses = [_to_int(address) for address in addresses]
    query = context.session.query(models.IPAddress)
    query = query.filter(models.IPAddress.network_id.in_(network_ids))
    if addresses:
        query = query.filter(not_(models.IPAddress.address.in_(addresses)))
    query = query.filter(not_(models.IPAddress.lock_id.is_(None)))
    return query.all()


def delete_locks(context, network_ids, addresses):
    

    addresses_no_longer_null_routed = _find_addresses_to_be_unlocked(
        context, network_ids, addresses)
    LOG.info("Deleting %s lock holders on IPAddress with ids: %s",
             len(addresses_no_longer_null_routed),
             [addr.id for addr in addresses_no_longer_null_routed])

    for address in addresses_no_longer_null_routed:
        lock_holder = None
        try:
            lock_holder = db_api.lock_holder_find(
                context, lock_id=address.lock_id, name=LOCK_NAME,
                scope=db_api.ONE)
            if lock_holder:
                db_api.lock_holder_delete(context, address, lock_holder)
        except Exception:
            LOG.exception("Failed to delete lock holder %s", lock_holder)
            continue
    context.session.flush()


def _find_or_create_address(context, network_ids, address):
    address_model = db_api.ip_address_find(
        context,
        network_id=network_ids, address=_to_int(address), scope=db_api.ONE)
    if not address_model:
        query = context.session.query(models.Subnet)
        query = query.filter(models.Subnet.network_id.in_(network_ids))
        query = query.filter(models.Subnet.ip_version == address.version)
        query = query.filter(_to_int(address) >= models.Subnet.first_ip)
        query = query.filter(_to_int(address) <= models.Subnet.last_ip)
        subnet = query.one()
        address_model = db_api.ip_address_create(
            context,
            address=address,
            subnet_id=subnet["id"],
            version=subnet["ip_version"],
            network_id=subnet["network_id"],
            address_type=ip_types.FIXED)
        address_model["deallocated"] = 1
        context.session.add(address_model)
    return address_model


def create_locks(context, network_ids, addresses):
    


    for address in addresses:
        address_model = None
        try:
            address_model = _find_or_create_address(
                context, network_ids, address)
            lock_holder = None
            if address_model.lock_id:
                lock_holder = db_api.lock_holder_find(
                    context,
                    lock_id=address_model.lock_id, name=LOCK_NAME,
                    scope=db_api.ONE)

            if not lock_holder:
                LOG.info("Creating lock holder on IPAddress %s with id %s",
                         address_model.address_readable,
                         address_model.id)
                db_api.lock_holder_create(
                    context, address_model, name=LOCK_NAME, type="ip_address")
        except Exception:
            LOG.exception("Failed to create lock holder on IPAddress %s",
                          address_model)
            continue
    context.session.flush()
