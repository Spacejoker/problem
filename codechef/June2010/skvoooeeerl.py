'''
DOESNT SOLVE THE PROBLEM


Created on 9 jun 2010

@author: Js

big one results:
Time: 1179484
Read from cache: 2463733636
possibleToWin rejected: 4077543
'''

test = True

#testInput = ['2',
#'3 9 5',
#'5 1 2',
#'1 2 1',
#'3 2 5',
#'5 1 2',
#'1 1 1']

f = open('sqrldata.txt','r')
testInput = []
for line in f:
    testInput.append(line)
    
f.close()

def getInput():
    if(test):
        return testInput.pop(0)
    else : 
        return raw_input()

nrOfTestCases = int(getInput())


for case in range(0, nrOfTestCases):
    
    #read shit
    l1 = getInput() #m,n, k  =  trees, squirrels, targetChestnutCount
    print l1
    treecount = int(l1.split(" ")[0])
    n = int(l1.split(" ")[1])
    targetNutCount = int(l1.split(" ")[2])
    
    firstDrop = []
    dropInterval = []
    
    l2 = getInput() #time before first drop
    l3 = getInput() #time between drops
    for x in range (0, treecount):
        #print x
        firstDrop.append(int(l2.split(" ")[x]))
        dropInterval.append(int(l3.split(" ")[x]))

    print 'created'
    
    #see best solution possible per second, then take the first that works
    lastSolution = 0
    second = 0
    while(True):
        print second
        treeYields = []
        
        for x in range(0, treecount):
            treeYield = 0
            
            dropSec = second - firstDrop[x]
            
            while(dropSec >= 0):
                treeYield += 1
                dropSec -= dropInterval[x]
            
            treeYields.append(treeYield)
        
        treeYields.reverse()
        
        sum = 0
        
        for x in range(0, n):
            if(len(treeYields) > x):
                sum += treeYields[x]
        
        if(sum >= targetNutCount):
            break;
        
        second += 1    
        
    print second
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    
