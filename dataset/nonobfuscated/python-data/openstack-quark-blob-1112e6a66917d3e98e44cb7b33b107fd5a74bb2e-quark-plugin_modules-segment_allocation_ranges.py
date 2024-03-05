














from neutron_lib import exceptions as n_exc
from oslo_log import log as logging

from quark.db import api as db_api
from quark import exceptions as q_exc
from quark import plugin_views as v

from quark import segment_allocations

SA_REGISTRY = segment_allocations.REGISTRY

LOG = logging.getLogger(__name__)


def get_segment_allocation_range(context, id, fields=None):
    LOG.info("get_segment_allocation_range %s for tenant %s fields %s" %
             (id, context.tenant_id, fields))

    if not context.is_admin:
        raise n_exc.NotAuthorized()

    sa_range = db_api.segment_allocation_range_find(
        context, id=id, scope=db_api.ONE)

    if not sa_range:
        raise q_exc.SegmentAllocationRangeNotFound(
            segment_allocation_range_id=id)

    
    allocs = db_api.segment_allocation_find(
        context,
        segment_allocation_range_id=sa_range["id"],
        deallocated=False).count()
    return v._make_segment_allocation_range_dict(
        sa_range, allocations=allocs)


def get_segment_allocation_ranges(context, **filters):
    LOG.info("get_segment_allocation_ranges for tenant %s" % context.tenant_id)
    if not context.is_admin:
        raise n_exc.NotAuthorized()

    sa_ranges = db_api.segment_allocation_range_find(
        context, scope=db_api.ALL, **filters)
    return [v._make_segment_allocation_range_dict(m) for m in sa_ranges]


def create_segment_allocation_range(context, sa_range):
    LOG.info("create_segment_allocation_range for tenant %s"
             % context.tenant_id)
    if not context.is_admin:
        raise n_exc.NotAuthorized()

    sa_range = sa_range.get("segment_allocation_range")
    if not sa_range:
        raise n_exc.BadRequest(resource="segment_allocation_range",
                               msg=("segment_allocation_range not in "
                                    "request body."))

    
    
    
    for k in ["first_id", "last_id", "segment_id", "segment_type"]:
        sa_range[k] = sa_range.get(k, None)
        if sa_range[k] is None:
            raise n_exc.BadRequest(
                resource="segment_allocation_range",
                msg=("Missing required key %s in request body." % (k)))

    
    for k in ["do_not_use"]:
        sa_range[k] = sa_range.get(k, None)

    
    if not SA_REGISTRY.is_valid_strategy(sa_range["segment_type"]):
        raise n_exc.BadRequest(
            resource="segment_allocation_range",
            msg=("Unknown segment type '%s'" % (k)))
    strategy = SA_REGISTRY.get_strategy(sa_range["segment_type"])

    
    with context.session.begin():
        new_range = strategy.create_range(context, sa_range)

    
    
    
    
    try:
        strategy.populate_range(context, new_range)
    except Exception:
        LOG.exception("Failed to populate segment allocation range.")
        delete_segment_allocation_range(context, new_range["id"])
        raise

    return v._make_segment_allocation_range_dict(new_range)


def _delete_segment_allocation_range(context, sa_range):

    allocs = db_api.segment_allocation_find(
        context,
        segment_allocation_range_id=sa_range["id"],
        deallocated=False).count()

    if allocs:
        raise q_exc.SegmentAllocationRangeInUse(
            segment_allocation_range_id=sa_range["id"])
    db_api.segment_allocation_range_delete(context, sa_range)


def delete_segment_allocation_range(context, sa_id):
    

    LOG.info("delete_segment_allocation_range %s for tenant %s" %
             (sa_id, context.tenant_id))
    if not context.is_admin:
        raise n_exc.NotAuthorized()

    with context.session.begin():
        sa_range = db_api.segment_allocation_range_find(
            context, id=sa_id, scope=db_api.ONE)
        if not sa_range:
            raise q_exc.SegmentAllocationRangeNotFound(
                segment_allocation_range_id=sa_id)
        _delete_segment_allocation_range(context, sa_range)
