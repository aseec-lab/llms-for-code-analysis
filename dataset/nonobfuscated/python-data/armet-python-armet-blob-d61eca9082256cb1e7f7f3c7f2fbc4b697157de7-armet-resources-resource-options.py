
from __future__ import absolute_import, unicode_literals, division
import re
import six
from importlib import import_module
from armet import utils, authentication, authorization
from armet.exceptions import ImproperlyConfigured
from armet import connectors as included_connectors


def _merge(options, name, bases, default=None):
    

    result = None
    for base in bases:
        if base is None:
            continue

        value = getattr(base, name, None)
        if value is None:
            continue

        result = utils.cons(result, value)

    value = options.get(name)
    if value is not None:
        result = utils.cons(result, value)

    return result or default


class ResourceOptions(object):

    def __init__(self, meta, name, data, bases):
        

        
        
        self.debug = meta.get('debug')
        if self.debug is None:
            self.debug = False

        
        
        
        
        
        self.abstract = data.get('abstract')

        
        
        
        
        
        self.name = meta.get('name')
        if self.name is None:
            
            
            dashed = utils.dasherize(name).strip()
            if dashed:
                
                self.name = re.sub(r'-resource$', '', dashed)

            else:
                
                self.name = name

        elif callable(self.name):
            
            
            self.name = self.name(name)

        
        
        
        
        
        
        self.asynchronous = meta.get('asynchronous', False)

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        self.connectors = connectors = _merge(meta, 'connectors', bases, {})

        if not connectors.get('http') and not self.abstract:
            raise ImproperlyConfigured('No valid HTTP connector was detected.')

        
        for key in connectors:
            connector = connectors[key]
            if isinstance(connector, six.string_types):
                if connector in getattr(included_connectors, key):
                    
                    connectors[key] = 'armet.connectors.{}'.format(connector)

        
        
        
        
        
        
        
        
        
        
        
        
        self.options = options = _merge(meta, 'options', bases, {})
        for name in options:
            
            setattr(self, name, meta.get(name))

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        self.patterns = meta.get('patterns', [])
        for index, pattern in enumerate(self.patterns):
            
            if isinstance(pattern, six.string_types):
                pattern = (None, pattern)

            
            self.patterns[index] = (pattern[0], re.compile(pattern[1]))

        
        
        
        
        self.trailing_slash = meta.get('trailing_slash', True)

        
        
        
        
        self.http_allowed_methods = meta.get('http_allowed_methods')
        if self.http_allowed_methods is None:
            self.http_allowed_methods = (
                'HEAD',
                'OPTIONS',
                'GET',
                'POST',
                'PUT',
                'PATCH',
                'DELETE',
                'LINK',
                'UNLINK'
            )

        
        
        self.http_allowed_headers = meta.get('http_allowed_headers')
        if self.http_allowed_headers is None:
            self.http_allowed_headers = (
                'Content-Type',
                'Authorization',
                'Accept',
                'Origin'
            )

        
        
        self.http_exposed_headers = meta.get('http_exposed_headers')
        if self.http_exposed_headers is None:
            self.http_exposed_headers = (
                'Content-Type',
                'Authorization',
                'Accept',
                'Origin'
            )

        
        
        
        
        
        self.http_allowed_origins = meta.get('http_allowed_origins')
        if self.http_allowed_origins is None:
            self.http_allowed_origins = ()

        
        
        
        
        
        
        
        
        
        
        self.legacy_redirect = meta.get('legacy_redirect', True)

        
        
        
        self.serializers = serializers = meta.get('serializers')
        if not serializers:
            self.serializers = {
                'json': 'armet.serializers.JSONSerializer',
                'url': 'armet.serializers.URLSerializer'
            }

        
        for name, serializer in six.iteritems(self.serializers):
            if isinstance(serializer, six.string_types):
                segments = serializer.split('.')
                module = '.'.join(segments[:-1])
                module = import_module(module)
                self.serializers[name] = getattr(module, segments[-1])

        
        self.allowed_serializers = meta.get('allowed_serializers')
        if not self.allowed_serializers:
            self.allowed_serializers = tuple(self.serializers.keys())

        
        
        for name in self.allowed_serializers:
            if name not in self.serializers:
                raise ImproperlyConfigured(
                    'The allowed serializer, {}, is not one of the '
                    'understood serializers'.format(name))

        
        
        self.default_serializer = meta.get('default_serializer')
        if not self.default_serializer:
            if 'json' in self.allowed_serializers:
                self.default_serializer = 'json'

            else:
                self.default_serializer = self.allowed_serializers[0]

        if self.default_serializer not in self.allowed_serializers:
            raise ImproperlyConfigured(
                'The chosen default serializer, {}, is not one of the '
                'allowed serializers'.format(self.default_serializer))

        
        
        
        self.deserializers = deserializers = meta.get('deserializers')
        if not deserializers:
            self.deserializers = {
                'json': 'armet.deserializers.JSONDeserializer',
                'url': 'armet.deserializers.URLDeserializer'
            }

        
        for name, deserializer in six.iteritems(self.deserializers):
            if isinstance(deserializer, six.string_types):
                segments = deserializer.split('.')
                module = '.'.join(segments[:-1])
                module = import_module(module)
                self.deserializers[name] = getattr(module, segments[-1])

        
        self.allowed_deserializers = meta.get('allowed_deserializers')
        if not self.allowed_deserializers:
            self.allowed_deserializers = tuple(self.deserializers.keys())

        
        
        for name in self.allowed_deserializers:
            if name not in self.deserializers:
                raise ImproperlyConfigured(
                    'The allowed deserializer, {}, is not one of the '
                    'understood deserializers'.format(name))

        
        
        self.authentication = meta.get('authentication')
        if self.authentication is None:
            
            self.authentication = (authentication.Authentication(),)

        
        
        
        self.authorization = meta.get('authorization')
        if self.authorization is None:
            
            self.authorization = authorization.Authorization()
