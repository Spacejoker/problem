'''
Created on 9 jun 2010

@author: Js
'''

test = False

testInput = ['3 3',
'1 2 10',
'2 3 10',
'1 3 10',
'4 3',
'1 2 10',
'2 3 10',
'1 4 15']

if(test):
    nrOfCases = 2
else : 
    nrOfCases = int(raw_input())

class customer():
    
    
    def __init__(self):
        self.start = 0.0
        self.end = 0.0
        self.budget = 0.0

    def getMean(self):
        return float(self.budget) / (float(self.end) - float(self.start))

def examineCustomers(customers, bestSetup, best):
    for cust in customers:
        
        #make a copy of bestSetup
        tmpSetup = []
        for item in bestSetup:
            tmpSetup.append(item)
        
        #get mean value
        mean = cust.getMean()
        for x in range(cust.start, cust.end):
            tmpSetup[x] = mean
        
        score = getTotalEarnings(customers, tmpSetup)
        
        if(score >= best):
            best = score
            bestSetup = tmpSetup
    return best, bestSetup
        

def getTotalEarnings(customers, setup):
    win = 0
    for c in customers:
        moneyLeft = c.budget
        for x in range (c.start, c.end):
            moneyLeft -= setup[x]
        if(moneyLeft >= 0):
            win += c.budget - moneyLeft
    
    return win

for case in range(0, nrOfCases):
    raw_input()
    line = ''
    if(test):
        line = testInput.pop(0)
    else:
        line = raw_input()
    
    nrOfNodes = int(line.split(" ")[0])
    nrOfCustomers = int(line.split(" ")[1])
    
    customers = []
    
    for c in range(0, nrOfCustomers):
        cust = customer()
        if(test):
            line = testInput.pop(0)
        else:
            line = raw_input()
        cust.start = int(line.split(" ")[0]) -1 
        cust.end = int(line.split(" ")[1]) - 1
        cust.budget = int(line.split(" ")[2])
        
        customers.append(cust)

       # print cust.budget
    
    best = []
    
    for x in range(0, nrOfNodes -1 ):
        best.append(0)
    
    #optimize for each customer one at a time
    
    bestVal = 0
    bestVal, best = examineCustomers(customers, best, bestVal)
    
    #print bestVal
    #print best#
    line = ''
    for x in best:
        line += str(int(x)) + ' '
        
    print line# [0:-1]
    #optimize on that 90% customers should afford their trip
    
    
    
    
    
    
    
    
    
    
    
    
    
    