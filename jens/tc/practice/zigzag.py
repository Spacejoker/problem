'''
Created on 7 jun 2010

@author: Js
'''

import time

dict = {}

def diffMethod(a, b):
    if a > b:
        return 1
    if b > a:
        return -1
    return 0

def getScore(series):
    
    if len(series) == 1:
        return 1
    
    lastDiff = 0
    
    for x in range(0, len(series)-1):
        diff = diffMethod(series[x], series[x+1])
        if lastDiff == 0 or lastDiff != diff:
            if diff == 0:
                return 0
            lastDiff = diff
        elif lastDiff == diff:
            return 0
    
    return len(series)

def findZigZag(series, best):
    
    if(len(series) < best):
        return best
    
    if( str(series) in dict):
        return dict[str(series)]
    
    if(len(series) == 0):
        return 0
    
    #evaluate the series, including target, return max.
    score = getScore(series)
    if(score > best):
        best = score
    
    #try every substring of the series with the same target
    for skipThisOne in range(0, len(series)):
        newList = []
        for index in range(0, len(series)):
            if(index != skipThisOne):
                newList.append(series[index])
        
        score = findZigZag(newList, best)

        if(score > best):
            best = score
    
    dict[str(series)] = best
    
    return best


#input = [ 374, 40, 854, 203, 203, 156, 362, 279, 812, 955,600, 947, 978,52,1,33]
input = [374, 40, 854, 203, 203, 156, 362, 279, 812, 955,
600, 947, 978, 46, 100, 953, 670, 862, 568, 188,
67, 669]
#, 810, 704, 52, 861, 49, 640, 370, 908]
#, 
#477, 245, 413, 109, 659, 401, 483, 308, 609, 120, 
#249, 22, 176, 279, 23, 22, 617, 462, 459, 244]
#input = [ 1, 7, 4, 9, 2, 5]
#input = [1, 2, 3, 4, 5, 6, 7, 8, 9]

output = 0

best = 0


start = time.time()


bestZigZag = findZigZag(input, best)

print 'time (sec): '  + str(time.time() - start) 

#print dict

print bestZigZag