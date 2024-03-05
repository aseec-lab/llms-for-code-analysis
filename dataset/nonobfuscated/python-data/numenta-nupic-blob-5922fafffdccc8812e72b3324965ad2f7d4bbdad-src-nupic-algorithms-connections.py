




















from bisect import bisect_left
from collections import defaultdict

from nupic.serializable import Serializable
try:
  import capnp
except ImportError:
  capnp = None
if capnp:
  from nupic.proto.ConnectionsProto_capnp import ConnectionsProto

EPSILON = 0.00001 
                  



class Segment(object):
  


  __slots__ = ["cell", "flatIdx", "_synapses", "_ordinal"]

  def __init__(self, cell, flatIdx, ordinal):
    self.cell = cell
    self.flatIdx = flatIdx
    self._synapses = set()
    self._ordinal = ordinal


  def __eq__(self, other):
    


    return (self.cell == other.cell and
            (sorted(self._synapses, key=lambda x: x._ordinal) ==
             sorted(other._synapses, key=lambda x: x._ordinal)))



class Synapse(object):
  


  __slots__ = ["segment", "presynapticCell", "permanence", "_ordinal"]

  def __init__(self, segment, presynapticCell, permanence, ordinal):
    self.segment = segment
    self.presynapticCell = presynapticCell
    self.permanence = permanence
    self._ordinal = ordinal


  def __eq__(self, other):
    

    return (self.segment.cell == other.segment.cell and
            self.presynapticCell == other.presynapticCell and
            abs(self.permanence - other.permanence) < EPSILON)



class CellData(object):
  

  __slots__ = ["_segments"]

  def __init__(self):
    self._segments = []

  def __eq__(self, other):
      return self._segments == other._segments

  def __ne__(self, other):
    return not self.__eq__(other)



def binSearch(arr, val):
  

  i = bisect_left(arr, val)
  if i != len(arr) and arr[i] == val:
    return i
  return -1



class Connections(Serializable):
  


  def __init__(self, numCells):

    
    self.numCells = numCells

    self._cells = [CellData() for _ in xrange(numCells)]
    self._synapsesForPresynapticCell = defaultdict(set)
    self._segmentForFlatIdx = []

    self._numSynapses = 0
    self._freeFlatIdxs = []
    self._nextFlatIdx = 0

    
    
    self._nextSynapseOrdinal = long(0)
    self._nextSegmentOrdinal = long(0)


  def segmentsForCell(self, cell):
    


    return self._cells[cell]._segments


  def synapsesForSegment(self, segment):
    


    return segment._synapses


  def dataForSynapse(self, synapse):
    

    return synapse


  def dataForSegment(self, segment):
    

    return segment


  def getSegment(self, cell, idx):
    


    return self._cells[cell]._segments[idx]


  def segmentForFlatIdx(self, flatIdx):
    

    return self._segmentForFlatIdx[flatIdx]


  def segmentFlatListLength(self):
    

    return self._nextFlatIdx


  def synapsesForPresynapticCell(self, presynapticCell):
    

    return self._synapsesForPresynapticCell[presynapticCell]


  def createSegment(self, cell):
    

    cellData = self._cells[cell]

    if len(self._freeFlatIdxs) > 0:
      flatIdx = self._freeFlatIdxs.pop()
    else:
      flatIdx = self._nextFlatIdx
      self._segmentForFlatIdx.append(None)
      self._nextFlatIdx += 1

    ordinal = self._nextSegmentOrdinal
    self._nextSegmentOrdinal += 1

    segment = Segment(cell, flatIdx, ordinal)
    cellData._segments.append(segment)
    self._segmentForFlatIdx[flatIdx] = segment

    return segment


  def destroySegment(self, segment):
    

    
    for synapse in segment._synapses:
      self._removeSynapseFromPresynapticMap(synapse)
    self._numSynapses -= len(segment._synapses)

    
    segments = self._cells[segment.cell]._segments
    i = segments.index(segment)
    del segments[i]

    
    
    self._freeFlatIdxs.append(segment.flatIdx)
    self._segmentForFlatIdx[segment.flatIdx] = None


  def createSynapse(self, segment, presynapticCell, permanence):
    

    idx = len(segment._synapses)
    synapse = Synapse(segment, presynapticCell, permanence,
                      self._nextSynapseOrdinal)
    self._nextSynapseOrdinal += 1
    segment._synapses.add(synapse)

    self._synapsesForPresynapticCell[presynapticCell].add(synapse)

    self._numSynapses += 1

    return synapse


  def _removeSynapseFromPresynapticMap(self, synapse):
    inputSynapses = self._synapsesForPresynapticCell[synapse.presynapticCell]

    inputSynapses.remove(synapse)

    if len(inputSynapses) == 0:
      del self._synapsesForPresynapticCell[synapse.presynapticCell]


  def destroySynapse(self, synapse):
    


    self._numSynapses -= 1

    self._removeSynapseFromPresynapticMap(synapse)

    synapse.segment._synapses.remove(synapse)


  def updateSynapsePermanence(self, synapse, permanence):
    


    synapse.permanence = permanence


  def computeActivity(self, activePresynapticCells, connectedPermanence):
    


    numActiveConnectedSynapsesForSegment = [0] * self._nextFlatIdx
    numActivePotentialSynapsesForSegment = [0] * self._nextFlatIdx

    threshold = connectedPermanence - EPSILON

    for cell in activePresynapticCells:
      for synapse in self._synapsesForPresynapticCell[cell]:
        flatIdx = synapse.segment.flatIdx
        numActivePotentialSynapsesForSegment[flatIdx] += 1
        if synapse.permanence > threshold:
          numActiveConnectedSynapsesForSegment[flatIdx] += 1

    return (numActiveConnectedSynapsesForSegment,
            numActivePotentialSynapsesForSegment)


  def numSegments(self, cell=None):
    

    if cell is not None:
      return len(self._cells[cell]._segments)

    return self._nextFlatIdx - len(self._freeFlatIdxs)


  def numSynapses(self, segment=None):
    

    if segment is not None:
      return len(segment._synapses)
    return self._numSynapses


  def segmentPositionSortKey(self, segment):
    

    return segment.cell + (segment._ordinal / float(self._nextSegmentOrdinal))


  def write(self, proto):
    

    protoCells = proto.init('cells', self.numCells)

    for i in xrange(self.numCells):
      segments = self._cells[i]._segments
      protoSegments = protoCells[i].init('segments', len(segments))

      for j, segment in enumerate(segments):
        synapses = segment._synapses
        protoSynapses = protoSegments[j].init('synapses', len(synapses))

        for k, synapse in enumerate(sorted(synapses, key=lambda s: s._ordinal)):
          protoSynapses[k].presynapticCell = synapse.presynapticCell
          protoSynapses[k].permanence = synapse.permanence


  @classmethod
  def getSchema(cls):
    return ConnectionsProto


  @classmethod
  def read(cls, proto):
    

    
    protoCells = proto.cells
    connections = cls(len(protoCells))

    for cellIdx, protoCell in enumerate(protoCells):
      protoCell = protoCells[cellIdx]
      protoSegments = protoCell.segments
      connections._cells[cellIdx] = CellData()
      segments = connections._cells[cellIdx]._segments

      for segmentIdx, protoSegment in enumerate(protoSegments):
        segment = Segment(cellIdx, connections._nextFlatIdx,
                          connections._nextSegmentOrdinal)

        segments.append(segment)
        connections._segmentForFlatIdx.append(segment)
        connections._nextFlatIdx += 1
        connections._nextSegmentOrdinal += 1

        synapses = segment._synapses
        protoSynapses = protoSegment.synapses

        for synapseIdx, protoSynapse in enumerate(protoSynapses):
          presynapticCell = protoSynapse.presynapticCell
          synapse = Synapse(segment, presynapticCell, protoSynapse.permanence,
                            ordinal=connections._nextSynapseOrdinal)
          connections._nextSynapseOrdinal += 1
          synapses.add(synapse)
          connections._synapsesForPresynapticCell[presynapticCell].add(synapse)

          connections._numSynapses += 1

    
    return connections


  def __eq__(self, other):
    

    
    for i in xrange(self.numCells):
      segments = self._cells[i]._segments
      otherSegments = other._cells[i]._segments

      if len(segments) != len(otherSegments):
        return False

      for j in xrange(len(segments)):
        segment = segments[j]
        otherSegment = otherSegments[j]
        synapses = segment._synapses
        otherSynapses = otherSegment._synapses

        if len(synapses) != len(otherSynapses):
          return False

        for synapse in synapses:
          found = False
          for candidate in otherSynapses:
            if synapse == candidate:
              found = True
              break

          if not found:
            return False

    if (len(self._synapsesForPresynapticCell) !=
        len(self._synapsesForPresynapticCell)):
      return False

    for i in self._synapsesForPresynapticCell.keys():
      synapses = self._synapsesForPresynapticCell[i]
      otherSynapses = other._synapsesForPresynapticCell[i]
      if len(synapses) != len(otherSynapses):
        return False

      for synapse in synapses:
        found = False
        for candidate in otherSynapses:
          if synapse == candidate:
            found = True
            break

        if not found:
          return False

    if self._numSynapses != other._numSynapses:
      return False

    
    return True


  def __ne__(self, other):
    

    return not self.__eq__(other)
