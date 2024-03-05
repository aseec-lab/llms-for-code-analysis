























import numpy as np
import random
from nupic.bindings.algorithms import SpatialPooler as SP



uintType = "uint32"



class Example(object):
  



  def __init__(self, inputDimensions, columnDimensions):
    

    self.inputDimensions = inputDimensions
    self.columnDimensions = columnDimensions
    self.inputSize = np.array(inputDimensions).prod()
    self.columnNumber = np.array(columnDimensions).prod()
    self.inputArray = np.zeros(self.inputSize, dtype=uintType)
    self.activeArray = np.zeros(self.columnNumber, dtype=uintType)

    random.seed(1)

    self.sp = SP(self.inputDimensions,
                 self.columnDimensions,
                 potentialRadius = self.inputSize,
                 numActiveColumnsPerInhArea = int(0.02*self.columnNumber),
                 globalInhibition = True,
                 seed = 1,
                 synPermActiveInc = 0.01,
                 synPermInactiveDec = 0.008)


  def createInput(self):
    


    print "-" * 70 + "Creating a random input vector" + "-" * 70

    
    self.inputArray[0:] = 0

    for i in range(self.inputSize):
      
      self.inputArray[i] = random.randrange(2)


  def run(self):
    


    print "-" * 80 + "Computing the SDR" + "-" * 80

    
    self.sp.compute(self.inputArray, True, self.activeArray)

    print self.activeArray.nonzero()


  def addNoise(self, noiseLevel):
    


    for _ in range(int(noiseLevel * self.inputSize)):
      
      
      randomPosition = int(random.random() * self.inputSize)

      
      if self.inputArray[randomPosition] == 1:
        self.inputArray[randomPosition] = 0

      else:
        self.inputArray[randomPosition] = 1

      
      

example = Example((32, 32), (64, 64))


print "\n \nFollowing columns represent the SDR"
print "Different set of columns each time since we randomize the input"
print "Lesson - different input vectors give different SDRs\n\n"


for i in range(3):
  example.createInput()
  example.run()


print "\n\nIdentical SDRs because we give identical inputs"
print "Lesson - identical inputs give identical SDRs\n\n"

print "-" * 75 + "Using identical input vectors" + "-" * 75


for i in range(2):
  example.run()


print "\n\nNow we are changing the input vector slightly."
print "We change a small percentage of 1s to 0s and 0s to 1s."
print "The resulting SDRs are similar, but not identical to the original SDR"
print "Lesson - Similar input vectors give similar SDRs\n\n"



print "-" * 75 + "After adding 10% noise to the input vector" + "-" * 75
example.addNoise(0.1)
example.run()



print "-" * 75 + "After adding another 20% noise to the input vector" + "-" * 75
example.addNoise(0.2)
example.run()
