








from openstax_accounts.interfaces import IOpenstaxAccountsAuthenticationPolicy
from pyramid import security
from pyramid.authorization import ACLAuthorizationPolicy
from pyramid.interfaces import IAuthenticationPolicy
from pyramid_multiauth import MultiAuthenticationPolicy
from zope.interface import implementer

from cnxpublishing.db import db_connect
from cnxpublishing.cache import cache_manager


ALL_KEY_INFO_SQL_STMT = "SELECT id, key, name, groups FROM api_keys"


@cache_manager.cache(expire=60 * 60 * 24)  
def lookup_api_key_info():
    

    info = {}
    with db_connect() as conn:
        with conn.cursor() as cursor:
            cursor.execute(ALL_KEY_INFO_SQL_STMT)
            for row in cursor.fetchall():
                id, key, name, groups = row
                user_id = "api_key:{}".format(id)
                info[key] = dict(id=id, user_id=user_id,
                                 name=name, groups=groups)
    return info


@implementer(IAuthenticationPolicy)
class APIKeyAuthenticationPolicy(object):
    


    @property
    def user_info_by_key(self):
        return lookup_api_key_info()

    def _discover_requesting_party(self, request):
        

        user_id = None
        api_key = request.headers.get('x-api-key', None)
        try:
            principal_info = self.user_info_by_key[api_key]
        except KeyError:
            principal_info = None
        if principal_info is not None:
            user_id = principal_info['user_id']
        return api_key, user_id, principal_info

    def authenticated_userid(self, request):
        api_key, user_id, _ = self._discover_requesting_party(request)
        return user_id

    
    
    unauthenticated_userid = authenticated_userid

    def effective_principals(self, request):
        

        api_key, user_id, info = self._discover_requesting_party(request)
        if api_key is None or user_id is None:
            return []
        try:
            principals = list(info['groups'])
        except TypeError:
            principals = []
        principals.append(security.Everyone)
        principals.append(security.Authenticated)
        return principals

    def remember(self, request, principal, **kw):
        return []  

    def forget(self, request):
        return []  


def includeme(config):
    

    api_key_authn_policy = APIKeyAuthenticationPolicy()
    config.include('openstax_accounts')
    openstax_authn_policy = config.registry.getUtility(
        IOpenstaxAccountsAuthenticationPolicy)

    
    policies = [api_key_authn_policy, openstax_authn_policy]
    authn_policy = MultiAuthenticationPolicy(policies)
    config.set_authentication_policy(authn_policy)

    
    authz_policy = ACLAuthorizationPolicy()
    config.set_authorization_policy(authz_policy)


__all__ = (
    'APIKeyAuthenticationPolicy',
    'lookup_api_key_info',
)
