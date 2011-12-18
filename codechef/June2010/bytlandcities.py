'''
DOESNT SOLVE THE PROBLEM
Created on 9 jun 2010

@author: Js
'''
import time
test = True

testInput = []

f = open('data.txt','r')

for line in f:
    testInput.append(line)
    
f.close()

#print testInput

def getInput():
    if(test):
        return testInput.pop(0)
    else : 
        return raw_input()

nrOfTestCases = int(getInput())

class City():
    def __init__(self, id):
        self.leftChildren = []
        self.rightChildren = []
        self.id = id
        
    def addChild(self, city, side):
        if(side == 'R'):
            self.rightChildren.append(city)
        else :
            self.leftChildren.append(city)
            
    def findCity(self, id):
        if(self.id == id):
            return self
        
        for city in self.leftChildren:
            x = city.findCity(id)
            if(x != None):
                return x
            
        for city in self.rightChildren:
            x = city.findCity(id)
            if(x != None):
                return x
            
        return None;
    
    def getMaxDepth(self, currentDepth):
        
        maxVal = currentDepth
        for city in self.leftChildren:
            val = city.getMaxDepth(currentDepth + 1)
            if(val > maxVal):
                maxVal = val
            
        for city in self.rightChildren:
            val = city.getMaxDepth(currentDepth + 1)
            if(val > maxVal):
                maxVal = val
        return maxVal

    def getPaths(self, currentPath, dir):
        
        paths = []
        newPath = currentPath + dir
        paths.append(newPath)
        
        for city in self.leftChildren:
            p = city.getPaths(newPath, 'L')
            for x in p:
                paths.append(x)
            
        for city in self.rightChildren:
            p = city.getPaths(newPath, 'R')
            for x in p:
                paths.append(x)
        
        return paths
        


def getCommonMaxDepth(mapPaths, nrOfMaps):
    d = 0
    
    for map in mapPaths:
        for line in map:
            #print line
            if len(line) <= d or len(line) == 0:
                continue
            found = 0
            for testmap in mapPaths:
                if found >= nrOfMaps:
                    break;
                if(line in testmap):
                    found += 1
                    continue
            if found >= nrOfMaps:
                d = len(line)
    return d

for case in range(0, nrOfTestCases):
    
    #read shit
    nrOfMaps = int(getInput())
    
    maps = []
    
    start = time.time()

    print 'starting timer'



    
    for x in range(0, nrOfMaps):
        print 'new map'
        #create map
        inputValues = int(getInput())
        
        list = []
        
        root = City(1)
        
        for y in range(0, inputValues-1):
            split = getInput().split(" ")
            
            city = root.findCity(int(split[0]))
            city.addChild(City(int(split[2])), split[1])
            
        maps.append(root)
        
    
    print 'read input at (sec): '  + str(time.time() - start) 
    
    mapPaths = []
    
    for map in maps:
        paths = map.getPaths('','')
        mapPaths.append(paths)
        #print paths
    #    maxDepth.append(map.getMaxDepth(0))
    #print mapPaths
    
    print 'fixed paths at (sec): '  + str(time.time() - start)
    
    output = ''
    
    for x in range(0, nrOfMaps):
        val = getCommonMaxDepth(mapPaths, x+1)
        output += str(val) + ' '
    
    print 'time (sec): '  + str(time.time() - start) 

    
    print output[:-1]
    