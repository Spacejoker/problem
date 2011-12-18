'''
Created on 8 jun 2010

@author: Js
'''
dict = {}

def getScore(donations):
    if(len(donations) == 1):
        return donations[0]
    
    score = 0
    for x in range(0, len(donations)):
        if x % 2 == 0 :
            score += donations[x]
    return score
    
def collectBananas(donations, best):
    
    #if(len(bananas) - 2 < best)
    
    if(str(donations) in dict ):
        return dict[str(donations)]
    
    #sum every second banana
    score = getScore(donations)
    
    if(score > best):
        best = score
    
    for skip in range(0,len(donations)):
        newList = []
        for index in range(0, len(donations)):
            if index != skip:
                newList.append(donations[index])
        score = collectBananas(newList, best)
            
        if(score > best):
            best = score
    dict[str(donations)] = best
    
    return best
        

#bananas = [94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61, 6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397, 52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72]
#bananas = [10, 3, 2, 5, 7, 8]
#bananas = [11,15]
#bananas = [10, 3, 2, 5, 7, 8 ]
#bananas = [94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61, 6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397, 52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72]
bananas = [94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61,  
  6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397,
  52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72]
best = 0

best1 = collectBananas(bananas[:-1], best)
best2 = collectBananas(bananas[1:], best)

if best1 > best2:
    print best1
else:
    print best2
