






















import time
import random

from collections import namedtuple
from heronpy.api.tuple import Tuple

HeronTuple = namedtuple('Tuple', Tuple._fields + ('creation_time', 'roots'))



class RootTupleInfo(namedtuple('RootTupleInfo', 'stream_id tuple_id insertion_time key')):
  __slots__ = ()
  def is_expired(self, current_time, timeout_sec):
    return self.insertion_time + timeout_sec - current_time <= 0

class TupleHelper(object):
  

  TICK_TUPLE_ID = "__tick"
  TICK_SOURCE_COMPONENT = "__system"

  
  MAX_SFIXED64_RAND_BITS = 61

  @staticmethod
  def make_tuple(stream, tuple_key, values, roots=None):
    

    component_name = stream.component_name
    stream_id = stream.id
    gen_task = roots[0].taskid if roots is not None and len(roots) > 0 else None
    return HeronTuple(id=str(tuple_key), component=component_name, stream=stream_id,
                      task=gen_task, values=values, creation_time=time.time(), roots=roots)
  @staticmethod
  def make_tick_tuple():
    

    return HeronTuple(id=TupleHelper.TICK_TUPLE_ID, component=TupleHelper.TICK_SOURCE_COMPONENT,
                      stream=TupleHelper.TICK_TUPLE_ID, task=None, values=None,
                      creation_time=time.time(), roots=None)

  @staticmethod
  def make_root_tuple_info(stream_id, tuple_id):
    

    key = random.getrandbits(TupleHelper.MAX_SFIXED64_RAND_BITS)
    return RootTupleInfo(stream_id=stream_id, tuple_id=tuple_id,
                         insertion_time=time.time(), key=key)
