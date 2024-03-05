























import numpy as np
from nupic.bindings.math import Random



class SequenceMachine(object):
  


  def __init__(self,
               patternMachine,
               seed=42):
    

    
    self.patternMachine = patternMachine

    
    self._random = Random(seed)


  def generateFromNumbers(self, numbers):
    

    sequence = []

    for number in numbers:
      if number == None:
        sequence.append(number)
      else:
        pattern = self.patternMachine.get(number)
        sequence.append(pattern)

    return sequence


  def addSpatialNoise(self, sequence, amount):
    

    newSequence = []

    for pattern in sequence:
      if pattern is not None:
        pattern = self.patternMachine.addNoise(pattern, amount)
      newSequence.append(pattern)

    return newSequence


  def prettyPrintSequence(self, sequence, verbosity=1):
    

    text = ""

    for i in xrange(len(sequence)):
      pattern = sequence[i]

      if pattern == None:
        text += "<reset>"
        if i < len(sequence) - 1:
          text += "\n"
      else:
        text += self.patternMachine.prettyPrintPattern(pattern,
                                                       verbosity=verbosity)

    return text


  def generateNumbers(self, numSequences, sequenceLength, sharedRange=None):
    

    numbers = []

    if sharedRange:
      sharedStart, sharedEnd = sharedRange
      sharedLength = sharedEnd - sharedStart
      sharedNumbers = range(numSequences * sequenceLength,
                            numSequences * sequenceLength + sharedLength)

    for i in xrange(numSequences):
      start = i * sequenceLength
      newNumbers = np.array(range(start, start + sequenceLength), np.uint32)
      self._random.shuffle(newNumbers)
      newNumbers = list(newNumbers)

      if sharedRange is not None:
        newNumbers[sharedStart:sharedEnd] = sharedNumbers

      numbers += newNumbers
      numbers.append(None)

    return numbers
