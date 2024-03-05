


import gevent
import sys
import six
from logging import getLogger

logger = getLogger(__name__)


class SilentGreenletExceptionWrapper(object):
    def __init__(self, exc_info):
        self.exc_info = exc_info

    def get_exc_info(self):
        return self.exc_info


def wrap_uncaught_greenlet_exceptions(func):
    def _func(*args, **kwargs):
        try:
            return func(*args, **kwargs)
        except BaseException:
            logger.exception("uncaught exception in greenlet")
            return SilentGreenletExceptionWrapper(sys.exc_info())
    _func.__name__ = repr(func)
    return _func


def spawn(func, *args, **kwargs):
    

    return gevent.spawn(wrap_uncaught_greenlet_exceptions(func), *args, **kwargs)


def join(greenlet, timeout=None, raise_error=True):
    value = greenlet.get(block=True, timeout=timeout)
    if isinstance(value, SilentGreenletExceptionWrapper):
        if raise_error:
            six.reraise(*value.get_exc_info())
        else:
            return None
    return value


def joinall(greenlets, timeout=None, raise_error=True):
    
    gevent.joinall(greenlets, timeout=timeout)
    
    if raise_error:
        [join(greenlet) for greenlet in greenlets]
