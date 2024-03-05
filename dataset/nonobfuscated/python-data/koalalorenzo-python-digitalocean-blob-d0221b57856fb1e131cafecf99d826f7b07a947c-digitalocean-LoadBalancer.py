
from .baseapi import BaseAPI, GET, POST, PUT, DELETE


class StickySesions(object):
    

    def __init__(self, type='none', cookie_name='', cookie_ttl_seconds=None):
        self.type = type
        if type is 'cookies':
            self.cookie_name = 'DO-LB'
            self.cookie_ttl_seconds = 300
        self.cookie_name = cookie_name
        self.cookie_ttl_seconds = cookie_ttl_seconds


class ForwardingRule(object):
    

    def __init__(self, entry_protocol=None, entry_port=None,
                 target_protocol=None, target_port=None, certificate_id="",
                 tls_passthrough=False):
        self.entry_protocol = entry_protocol
        self.entry_port = entry_port
        self.target_protocol = target_protocol
        self.target_port = target_port
        self.certificate_id = certificate_id
        self.tls_passthrough = tls_passthrough


class HealthCheck(object):
    

    def __init__(self, protocol='http', port=80, path='/',
                 check_interval_seconds=10, response_timeout_seconds=5,
                 healthy_threshold=5, unhealthy_threshold=3):
        self.protocol = protocol
        self.port = port
        self.path = path
        self.check_interval_seconds = check_interval_seconds
        self.response_timeout_seconds = response_timeout_seconds
        self.healthy_threshold = healthy_threshold
        self.unhealthy_threshold = unhealthy_threshold


class LoadBalancer(BaseAPI):
    

    def __init__(self, *args, **kwargs):
        self.id = None
        self.name = None
        self.region = None
        self.algorithm = None
        self.forwarding_rules = []
        self.health_check = None
        self.sticky_sessions = None
        self.redirect_http_to_https = False
        self.droplet_ids = []
        self.tag = None
        self.status = None
        self.created_at = None

        super(LoadBalancer, self).__init__(*args, **kwargs)

    @classmethod
    def get_object(cls, api_token, id):
        

        load_balancer = cls(token=api_token, id=id)
        load_balancer.load()
        return load_balancer

    def load(self):
        

        data = self.get_data('load_balancers/%s' % self.id, type=GET)
        load_balancer = data['load_balancer']

        
        for attr in load_balancer.keys():
            if attr == 'health_check':
                health_check = HealthCheck(**load_balancer['health_check'])
                setattr(self, attr, health_check)
            elif attr == 'sticky_sessions':
                sticky_ses = StickySesions(**load_balancer['sticky_sessions'])
                setattr(self, attr, sticky_ses)
            elif attr == 'forwarding_rules':
                rules = list()
                for rule in load_balancer['forwarding_rules']:
                    rules.append(ForwardingRule(**rule))
                setattr(self, attr, rules)
            else:
                setattr(self, attr, load_balancer[attr])

        return self

    def create(self, *args, **kwargs):
        

        rules_dict = [rule.__dict__ for rule in self.forwarding_rules]

        params = {'name': self.name, 'region': self.region,
                  'forwarding_rules': rules_dict,
                  'redirect_http_to_https': self.redirect_http_to_https}

        if self.droplet_ids and self.tag:
            raise ValueError('droplet_ids and tag are mutually exclusive args')
        elif self.tag:
            params['tag'] = self.tag
        else:
            params['droplet_ids'] = self.droplet_ids

        if self.algorithm:
            params['algorithm'] = self.algorithm
        if self.health_check:
            params['health_check'] = self.health_check.__dict__
        if self.sticky_sessions:
            params['sticky_sessions'] = self.sticky_sessions.__dict__

        data = self.get_data('load_balancers/', type=POST, params=params)

        if data:
            self.id = data['load_balancer']['id']
            self.ip = data['load_balancer']['ip']
            self.algorithm = data['load_balancer']['algorithm']
            self.health_check = HealthCheck(
                **data['load_balancer']['health_check'])
            self.sticky_sessions = StickySesions(
                **data['load_balancer']['sticky_sessions'])
            self.droplet_ids = data['load_balancer']['droplet_ids']
            self.status = data['load_balancer']['status']
            self.created_at = data['load_balancer']['created_at']

        return self

    def save(self):
        

        forwarding_rules = [rule.__dict__ for rule in self.forwarding_rules]

        data = {
            'name': self.name,
            'region': self.region['slug'],
            'forwarding_rules': forwarding_rules,
            'redirect_http_to_https': self.redirect_http_to_https
        }

        if self.tag:
            data['tag'] = self.tag
        else:
            data['droplet_ids'] = self.droplet_ids

        if self.algorithm:
            data["algorithm"] = self.algorithm
        if self.health_check:
            data['health_check'] = self.health_check.__dict__
        if self.sticky_sessions:
            data['sticky_sessions'] = self.sticky_sessions.__dict__

        return self.get_data("load_balancers/%s/" % self.id,
                             type=PUT,
                             params=data)

    def destroy(self):
        

        return self.get_data('load_balancers/%s/' % self.id, type=DELETE)

    def add_droplets(self, droplet_ids):
        

        return self.get_data(
            "load_balancers/%s/droplets/" % self.id,
            type=POST,
            params={"droplet_ids": droplet_ids}
        )

    def remove_droplets(self, droplet_ids):
        

        return self.get_data(
            "load_balancers/%s/droplets/" % self.id,
            type=DELETE,
            params={"droplet_ids": droplet_ids}
        )

    def add_forwarding_rules(self, forwarding_rules):
        

        rules_dict = [rule.__dict__ for rule in forwarding_rules]

        return self.get_data(
            "load_balancers/%s/forwarding_rules/" % self.id,
            type=POST,
            params={"forwarding_rules": rules_dict}
        )

    def remove_forwarding_rules(self, forwarding_rules):
        

        rules_dict = [rule.__dict__ for rule in forwarding_rules]

        return self.get_data(
            "load_balancers/%s/forwarding_rules/" % self.id,
            type=DELETE,
            params={"forwarding_rules": rules_dict}
        )

    def __str__(self):
        return "%s" % (self.id)
