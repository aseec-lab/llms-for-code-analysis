




















import numpy
import os

try:
  import capnp
except ImportError:
  capnp = None

from nupic.bindings.regions.PyRegion import PyRegion

from nupic.algorithms import (anomaly, backtracking_tm, backtracking_tm_cpp,
                              backtracking_tm_shim)
if capnp:
  from nupic.regions.tm_region_capnp import TMRegionProto

from nupic.support import getArgumentDescriptions



gDefaultTemporalImp = 'py'



def _getTPClass(temporalImp):
  


  if temporalImp == 'py':
    return backtracking_tm.BacktrackingTM
  elif temporalImp == 'cpp':
    return backtracking_tm_cpp.BacktrackingTMCPP
  elif temporalImp == 'tm_py':
    return backtracking_tm_shim.TMShim
  elif temporalImp == 'tm_cpp':
    return backtracking_tm_shim.TMCPPShim
  elif temporalImp == 'monitored_tm_py':
    return backtracking_tm_shim.MonitoredTMShim
  else:
    raise RuntimeError("Invalid temporalImp '%s'. Legal values are: 'py', "
              "'cpp', 'tm_py', 'monitored_tm_py'" % (temporalImp))



def _buildArgs(f, self=None, kwargs={}):
  

  
  argTuples = getArgumentDescriptions(f)
  argTuples = argTuples[1:]  

  
  
  
  
  
  init = TMRegion.__init__
  ourArgNames = [t[0] for t in getArgumentDescriptions(init)]
  
  
  ourArgNames += [
    'numberOfCols',    
  ]
  for argTuple in argTuples[:]:
    if argTuple[0] in ourArgNames:
      argTuples.remove(argTuple)

  
  if self:
    for argTuple in argTuples:
      argName = argTuple[0]
      if argName in kwargs:
        
        argValue = kwargs.pop(argName)
      else:
        
        
        if len(argTuple) == 2:
          
          raise TypeError("Must provide '%s'" % argName)
        argValue = argTuple[2]
      
      setattr(self, argName, argValue)

  return argTuples


def _getAdditionalSpecs(temporalImp, kwargs={}):
  

  typeNames = {int: 'UInt32', float: 'Real32', str: 'Byte', bool: 'bool', tuple: 'tuple'}

  def getArgType(arg):
    t = typeNames.get(type(arg), 'Byte')
    count = 0 if t == 'Byte' else 1
    if t == 'tuple':
      t = typeNames.get(type(arg[0]), 'Byte')
      count = len(arg)
    if t == 'bool':
      t = 'UInt32'
    return (t, count)

  def getConstraints(arg):
    t = typeNames.get(type(arg), 'Byte')
    if t == 'Byte':
      return 'multiple'
    elif t == 'bool':
      return 'bool'
    else:
      return ''

  
  TemporalClass = _getTPClass(temporalImp)
  tArgTuples = _buildArgs(TemporalClass.__init__)
  temporalSpec = {}
  for argTuple in tArgTuples:
    d = dict(
      description=argTuple[1],
      accessMode='ReadWrite',
      dataType=getArgType(argTuple[2])[0],
      count=getArgType(argTuple[2])[1],
      constraints=getConstraints(argTuple[2]))
    temporalSpec[argTuple[0]] = d

  
  temporalSpec.update(dict(
    columnCount=dict(
      description='Total number of columns.',
      accessMode='Read',
      dataType='UInt32',
      count=1,
      constraints=''),

    cellsPerColumn=dict(
      description='Number of cells per column.',
      accessMode='Read',
      dataType='UInt32',
      count=1,
      constraints=''),

    inputWidth=dict(
      description='Number of inputs to the TM.',
      accessMode='Read',
      dataType='UInt32',
      count=1,
      constraints=''),

    predictedSegmentDecrement=dict(
      description='Predicted segment decrement',
      accessMode='Read',
      dataType='Real',
      count=1,
      constraints=''),

    orColumnOutputs=dict(
      description=
,
      accessMode='Read',
      dataType='Bool',
      count=1,
      constraints='bool'),

    cellsSavePath=dict(
      description=
,
      accessMode='ReadWrite',
      dataType='Byte',
      count=0,
      constraints=''),

    temporalImp=dict(
      description=
,
      accessMode='ReadWrite',
      dataType='Byte',
      count=0,
      constraints='enum: py, cpp'),

  ))

  
  otherSpec = dict(
    learningMode=dict(
      description='True if the node is learning (default True).',
      accessMode='ReadWrite',
      dataType='Bool',
      count=1,
      defaultValue=True,
      constraints='bool'),

    inferenceMode=dict(
      description='True if the node is inferring (default False).',
      accessMode='ReadWrite',
      dataType='Bool',
      count=1,
      defaultValue=False,
      constraints='bool'),

    computePredictedActiveCellIndices=dict(
      description='True if active and predicted active indices should be computed',
      accessMode='Create',
      dataType='Bool',
      count=1,
      defaultValue=False,
      constraints='bool'),

    anomalyMode=dict(
      description='True if an anomaly score is being computed',
      accessMode='Create',
      dataType='Bool',
      count=1,
      defaultValue=False,
      constraints='bool'),

    topDownMode=dict(
      description='True if the node should do top down compute on the next call '
                  'to compute into topDownOut (default False).',
      accessMode='ReadWrite',
      dataType='Bool',
      count=1,
      defaultValue=False,
      constraints='bool'),

    activeOutputCount=dict(
      description='Number of active elements in bottomUpOut output.',
      accessMode='Read',
      dataType='UInt32',
      count=1,
      constraints=''),

    storeDenseOutput=dict(
      description=
,
      accessMode='ReadWrite',
      dataType='UInt32',
      count=1,
      constraints='bool'),

    logPathOutput=dict(
      description='Optional name of output log file. If set, every output vector'
                  ' will be logged to this file as a sparse vector.',
      accessMode='ReadWrite',
      dataType='Byte',
      count=0,
      constraints=''),

  )

  return temporalSpec, otherSpec



class TMRegion(PyRegion):

  


  def __init__(self,

               columnCount,   
               inputWidth,    
               cellsPerColumn, 

               
               
               

               orColumnOutputs=False,
               cellsSavePath='',
               temporalImp=gDefaultTemporalImp,
               anomalyMode=False,
               computePredictedActiveCellIndices=False,

               **kwargs):
    
    TemporalClass = _getTPClass(temporalImp)

    
    
    
    tArgTuples = _buildArgs(TemporalClass.__init__, self, kwargs)

    self._temporalArgNames = [t[0] for t in tArgTuples]

    self.learningMode   = True      
    self.inferenceMode  = False
    self.anomalyMode    = anomalyMode
    self.computePredictedActiveCellIndices = computePredictedActiveCellIndices
    self.topDownMode    = False
    self.columnCount    = columnCount
    self.inputWidth     = inputWidth
    self.outputWidth    = columnCount * cellsPerColumn
    self.cellsPerColumn = cellsPerColumn

    PyRegion.__init__(self, **kwargs)

    
    
    self._loaded = False
    self._initialize()

    
    self.breakPdb = False
    self.breakKomodo = False

    
    self.orColumnOutputs = orColumnOutputs
    self.temporalImp = temporalImp

    
    self.storeDenseOutput = False
    self.logPathOutput = ''
    self.cellsSavePath = cellsSavePath
    self._fpLogTPOutput = None

    
    self._tfdr = None  


  
  
  
  
  


  def _initialize(self):
    


    for attrName in self._getEphemeralMembersBase():
      if attrName != "_loaded":
        if hasattr(self, attrName):
          if self._loaded:
            
            
            
            pass
          else:
            print self.__class__.__name__, "contains base class member '%s'" % \
                attrName
    if not self._loaded:
      for attrName in self._getEphemeralMembersBase():
        if attrName != "_loaded":
          
          
          assert not hasattr(self, attrName)
        else:
          assert hasattr(self, attrName)

    
    self._profileObj = None
    self._iterations = 0

    
    self._initEphemerals()
    self._checkEphemeralMembers()


  def initialize(self):
    

    
    
    autoArgs = dict((name, getattr(self, name))
                    for name in self._temporalArgNames)

    if self._tfdr is None:
      tpClass = _getTPClass(self.temporalImp)

      if self.temporalImp in ['py', 'cpp', 'r',
                              'tm_py', 'tm_cpp',
                              'monitored_tm_py',]:
        self._tfdr = tpClass(
             numberOfCols=self.columnCount,
             cellsPerColumn=self.cellsPerColumn,
             **autoArgs)
      else:
        raise RuntimeError("Invalid temporalImp")


  
  
  
  
  


  
  def compute(self, inputs, outputs):
    


    
    

    
    
    
    
    if False and self.learningMode \
        and self._iterations > 0 and self._iterations <= 10:

      import hotshot
      if self._iterations == 10:
        print "\n  Collecting and sorting internal node profiling stats generated by hotshot..."
        stats = hotshot.stats.load("hotshot.stats")
        stats.strip_dirs()
        stats.sort_stats('time', 'calls')
        stats.print_stats()

      
      
      if self._profileObj is None:
        print "\n  Preparing to capture profile using hotshot..."
        if os.path.exists('hotshot.stats'):
          
          os.remove('hotshot.stats')
        self._profileObj = hotshot.Profile("hotshot.stats", 1, 1)
                                          
      self._profileObj.runcall(self._compute, *[inputs, outputs])
    else:
      self._compute(inputs, outputs)

  def _compute(self, inputs, outputs):
    


    
    
    

    if self._tfdr is None:
      raise RuntimeError("TM has not been initialized")

    
    self._conditionalBreak()

    self._iterations += 1

    
    buInputVector = inputs['bottomUpIn']

    
    resetSignal = False
    if 'resetIn' in inputs:
      assert len(inputs['resetIn']) == 1
      if inputs['resetIn'][0] != 0:
        self._tfdr.reset()
        self._sequencePos = 0  

    if self.computePredictedActiveCellIndices:
      prevPredictedState = self._tfdr.getPredictedState().reshape(-1).astype('float32')

    if self.anomalyMode:
      prevPredictedColumns = self._tfdr.topDownCompute().copy().nonzero()[0]

    
    tpOutput = self._tfdr.compute(buInputVector, self.learningMode, self.inferenceMode)
    self._sequencePos += 1

    
    if self.orColumnOutputs:
      tpOutput= tpOutput.reshape(self.columnCount,
                                     self.cellsPerColumn).max(axis=1)

    
    if self._fpLogTPOutput:
      output = tpOutput.reshape(-1)
      outputNZ = tpOutput.nonzero()[0]
      outStr = " ".join(["%d" % int(token) for token in outputNZ])
      print >>self._fpLogTPOutput, output.size, outStr

    
    outputs['bottomUpOut'][:] = tpOutput.flat

    if self.topDownMode:
      
      outputs['topDownOut'][:] = self._tfdr.topDownCompute().copy()

    
    if self.anomalyMode:
      activeLearnCells = self._tfdr.getLearnActiveStateT()
      size = activeLearnCells.shape[0] * activeLearnCells.shape[1]
      outputs['lrnActiveStateT'][:] = activeLearnCells.reshape(size)

      activeColumns = buInputVector.nonzero()[0]
      outputs['anomalyScore'][:] = anomaly.computeRawAnomalyScore(
        activeColumns, prevPredictedColumns)

    if self.computePredictedActiveCellIndices:
      
      activeState = self._tfdr._getActiveState().reshape(-1).astype('float32')
      activeIndices = numpy.where(activeState != 0)[0]
      predictedIndices= numpy.where(prevPredictedState != 0)[0]
      predictedActiveIndices = numpy.intersect1d(activeIndices, predictedIndices)
      outputs["activeCells"].fill(0)
      outputs["activeCells"][activeIndices] = 1
      outputs["predictedActiveCells"].fill(0)
      outputs["predictedActiveCells"][predictedActiveIndices] = 1


  
  
  
  
  

  
  @classmethod
  def getBaseSpec(cls):
    

    spec = dict(
      description=TMRegion.__doc__,
      singleNodeOnly=True,
      inputs=dict(
        bottomUpIn=dict(
          description=
,
          dataType='Real32',
          count=0,
          required=True,
          regionLevel=False,
          isDefaultInput=True,
          requireSplitterMap=False),

        resetIn=dict(
          description=
,
          dataType='Real32',
          count=1,
          required=False,
          regionLevel=True,
          isDefaultInput=False,
          requireSplitterMap=False),

        sequenceIdIn=dict(
          description="Sequence ID",
          dataType='UInt64',
          count=1,
          required=False,
          regionLevel=True,
          isDefaultInput=False,
          requireSplitterMap=False),

      ),

      outputs=dict(
        bottomUpOut=dict(
          description=
,
          dataType='Real32',
          count=0,
          regionLevel=True,
          isDefaultOutput=True),

        topDownOut=dict(
          description=
,
          dataType='Real32',
          count=0,
          regionLevel=True,
          isDefaultOutput=False),

        activeCells=dict(
          description="The cells that are active",
          dataType='Real32',
          count=0,
          regionLevel=True,
          isDefaultOutput=False),

        predictedActiveCells=dict(
          description="The cells that are active and predicted",
          dataType='Real32',
          count=0,
          regionLevel=True,
          isDefaultOutput=False),

        anomalyScore = dict(
          description=
,
          dataType='Real32',
          count=1,
          regionLevel=True,
          isDefaultOutput=False),

        lrnActiveStateT = dict(
          description=
,
          dataType='Real32',
          count=0,
          regionLevel=True,
          isDefaultOutput=False),

      ),

      parameters=dict(
        breakPdb=dict(
          description='Set to 1 to stop in the pdb debugger on the next compute',
          dataType='UInt32',
          count=1,
          constraints='bool',
          defaultValue=0,
          accessMode='ReadWrite'),

        breakKomodo=dict(
          description='Set to 1 to stop in the Komodo debugger on the next compute',
          dataType='UInt32',
          count=1,
          constraints='bool',
          defaultValue=0,
          accessMode='ReadWrite'),

      ),
      commands = {}
    )

    return spec

  @classmethod
  def getSpec(cls):
    

    spec = cls.getBaseSpec()
    t, o = _getAdditionalSpecs(temporalImp=gDefaultTemporalImp)
    spec['parameters'].update(t)
    spec['parameters'].update(o)

    return spec


  def getAlgorithmInstance(self):
    

    return self._tfdr


  def getParameter(self, parameterName, index=-1):
    

    if parameterName in self._temporalArgNames:
      return getattr(self._tfdr, parameterName)
    else:
      return PyRegion.getParameter(self, parameterName, index)


  def setParameter(self, parameterName, index, parameterValue):
    

    if parameterName in self._temporalArgNames:
      setattr(self._tfdr, parameterName, parameterValue)

    elif parameterName == "logPathOutput":
      self.logPathOutput = parameterValue
      
      if self._fpLogTPOutput is not None:
        self._fpLogTPOutput.close()
        self._fpLogTPOutput = None

      
      if parameterValue:
        self._fpLogTPOutput = open(self.logPathOutput, 'w')

    elif hasattr(self, parameterName):
      setattr(self, parameterName, parameterValue)

    else:
      raise Exception('Unknown parameter: ' + parameterName)


  
  
  
  
  

  def resetSequenceStates(self):
    

    self._tfdr.reset()
    self._sequencePos = 0  
    return

  def finishLearning(self):
    

    if self._tfdr is None:
      raise RuntimeError("Temporal memory has not been initialized")

    if hasattr(self._tfdr, 'finishLearning'):
      self.resetSequenceStates()
      self._tfdr.finishLearning()

  
  
  
  
  


  @staticmethod
  def getSchema():
    

    return TMRegionProto


  def writeToProto(self, proto):
    

    proto.temporalImp = self.temporalImp
    proto.columnCount = self.columnCount
    proto.inputWidth = self.inputWidth
    proto.cellsPerColumn = self.cellsPerColumn
    proto.learningMode = self.learningMode
    proto.inferenceMode = self.inferenceMode
    proto.anomalyMode = self.anomalyMode
    proto.topDownMode = self.topDownMode
    proto.computePredictedActiveCellIndices = (
      self.computePredictedActiveCellIndices)
    proto.orColumnOutputs = self.orColumnOutputs

    if self.temporalImp == "py":
      tmProto = proto.init("backtrackingTM")
    elif self.temporalImp == "cpp":
      tmProto = proto.init("backtrackingTMCpp")
    elif self.temporalImp == "tm_py":
      tmProto = proto.init("temporalMemory")
    elif self.temporalImp == "tm_cpp":
      tmProto = proto.init("temporalMemory")
    else:
      raise TypeError(
          "Unsupported temporalImp for capnp serialization: {}".format(
              self.temporalImp))

    self._tfdr.write(tmProto)


  @classmethod
  def readFromProto(cls, proto):
    

    instance = cls(proto.columnCount, proto.inputWidth, proto.cellsPerColumn)

    instance.temporalImp = proto.temporalImp
    instance.learningMode = proto.learningMode
    instance.inferenceMode = proto.inferenceMode
    instance.anomalyMode = proto.anomalyMode
    instance.topDownMode = proto.topDownMode
    instance.computePredictedActiveCellIndices = (
      proto.computePredictedActiveCellIndices)
    instance.orColumnOutputs = proto.orColumnOutputs

    if instance.temporalImp == "py":
      tmProto = proto.backtrackingTM
    elif instance.temporalImp == "cpp":
      tmProto = proto.backtrackingTMCpp
    elif instance.temporalImp == "tm_py":
      tmProto = proto.temporalMemory
    elif instance.temporalImp == "tm_cpp":
      tmProto = proto.temporalMemory
    else:
      raise TypeError(
          "Unsupported temporalImp for capnp serialization: {}".format(
              instance.temporalImp))

    instance._tfdr = _getTPClass(proto.temporalImp).read(tmProto)

    return instance


  def __getstate__(self):
    

    state = self.__dict__.copy()
    
    for ephemeralMemberName in self._getEphemeralMembersAll():
      state.pop(ephemeralMemberName, None)
    return state

  def serializeExtraData(self, filePath):
    

    if self._tfdr is not None:
      self._tfdr.saveToFile(filePath)

  def deSerializeExtraData(self, filePath):
    

    if self._tfdr is not None:
      self._tfdr.loadFromFile(filePath)


  def __setstate__(self, state):
    


    if not hasattr(self, 'storeDenseOutput'):
      self.storeDenseOutput = False

    if not hasattr(self, 'computePredictedActiveCellIndices'):
      self.computePredictedActiveCellIndices = False

    self.__dict__.update(state)
    self._loaded = True
    
    
    self._initialize()


  def _initEphemerals(self):
    


    self._sequencePos = 0
    self._fpLogTPOutput = None
    self.logPathOutput = None


  def _getEphemeralMembers(self):
    


    return ['_sequencePos', '_fpLogTPOutput', 'logPathOutput',]


  def _getEphemeralMembersBase(self):
    

    return [
        '_loaded',
        '_profileObj',
        '_iterations',
      ]


  def _getEphemeralMembersAll(self):
    

    return self._getEphemeralMembersBase() + self._getEphemeralMembers()


  def _checkEphemeralMembers(self):
    for attrName in self._getEphemeralMembersBase():
      if not hasattr(self, attrName):
        print "Missing base class member:", attrName
    for attrName in self._getEphemeralMembers():
      if not hasattr(self, attrName):
        print "Missing derived class member:", attrName

    for attrName in self._getEphemeralMembersBase():
      assert hasattr(self, attrName)
    for attrName in self._getEphemeralMembers():
      assert hasattr(self, attrName), "Node missing attr '%s'." % attrName

  
  
  
  
  


  def _conditionalBreak(self):
    if self.breakKomodo:
      import dbgp.client; dbgp.client.brk()
    if self.breakPdb:
      import pdb; pdb.set_trace()


  
  
  
  
  


  def getOutputElementCount(self, name):
    

    if name == 'bottomUpOut':
      return self.outputWidth
    elif name == 'topDownOut':
      return self.columnCount
    elif name == 'lrnActiveStateT':
      return self.outputWidth
    elif name == "activeCells":
      return self.outputWidth
    elif name == "predictedActiveCells":
      return self.outputWidth
    else:
      raise Exception("Invalid output name specified")


  
  
  def getParameterArrayCount(self, name, index):
    

    p = self.getParameter(name)
    if (not hasattr(p, '__len__')):
      raise Exception("Attempt to access parameter '%s' as an array but it is not an array" % name)
    return len(p)


  
  
  def getParameterArray(self, name, index, a):
    

    p = self.getParameter(name)
    if (not hasattr(p, '__len__')):
      raise Exception("Attempt to access parameter '%s' as an array but it is not an array" % name)

    if len(p) >  0:
      a[:] = p[:]
