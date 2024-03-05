





























from __future__ import absolute_import, unicode_literals, print_function

import os
import re

from ._compat import string_types




__all__ = ['FileSet', 'includes', 'excludes']


def glob2re(part):
    

    return "[^/]*".join(
        re.escape(bit).replace(r'\[\^', '[^').replace(r'\[', '[').replace(r'\]', ']')
        for bit in part.split("*")
    )


def parse_glob(pattern):
    

    if not pattern:
        return

    bits = pattern.split("/")
    dirs, filename = bits[:-1], bits[-1]

    for dirname in dirs:
        if dirname == "**":
            yield  "(|.+/)"
        else:
            yield glob2re(dirname) + "/"

    yield glob2re(filename)


def compile_glob(spec):
    

    parsed = "".join(parse_glob(spec))
    regex = "^{0}$".format(parsed)
    return re.compile(regex)


class Pattern(object):
    


    def __init__(self, spec, inclusive):
        

        self.compiled = compile_glob(spec.rstrip('/'))
        self.inclusive = inclusive
        self.is_dir = spec.endswith('/')

    def __str__(self):
        

        return ('+' if self.inclusive else '-') + self.compiled.pattern

    def matches(self, path):
        

        return bool(self.compiled.match(path))


class FileSet(object):
    


    def __init__(self, root, patterns):
        if isinstance(patterns, string_types):
            patterns = [patterns]

        self.root = root
        self.patterns = [i if hasattr(i, 'inclusive') else includes(i) for i in patterns]

    def __repr__(self):
        return "<FileSet at {0} {1}>".format(repr(self.root), ' '.join(str(i) for i in self. patterns))

    def included(self, path, is_dir=False):
        

        inclusive = None
        for pattern in self.patterns:
            if pattern.is_dir == is_dir and pattern.matches(path):
                inclusive = pattern.inclusive

        
        return inclusive

    def __iter__(self):
        for path in self.walk():
            yield path

    def __or__(self, other):
        return set(self) | set(other)

    def __ror__(self, other):
        return self | other

    def __and__(self, other):
        return set(self) & set(other)

    def __rand__(self, other):
        return self & other

    def walk(self, **kwargs):
        

        lead = ''
        if 'with_root' in kwargs and kwargs.pop('with_root'):
            lead = self.root.rstrip(os.sep) + os.sep

        for base, dirs, files in os.walk(self.root, **kwargs):
            prefix = base[len(self.root):].lstrip(os.sep)
            bits = prefix.split(os.sep) if prefix else []

            for dirname in dirs[:]:
                path = '/'.join(bits + [dirname])
                inclusive = self.included(path, is_dir=True)
                if inclusive:
                    yield lead + path + '/'
                elif inclusive is False:
                    dirs.remove(dirname)

            for filename in files:
                path = '/'.join(bits + [filename])
                if self.included(path):
                    yield lead + path


def includes(pattern):
    

    return Pattern(pattern, inclusive=True)


def excludes(pattern):
    

    return Pattern(pattern, inclusive=False)
