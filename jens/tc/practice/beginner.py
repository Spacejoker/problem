'''
Created on 7 jun 2010

@author: Js
'''

targetSum = 11
min = []

for x in range(0, targetSum + 1):
    min.append(9232) #this val means impossible!

min[0]=0
coins = [2,3,5]

for i in range(0, targetSum + 1): 
    for coin in coins:
        #see if i can use a coin to create a better solution
        if(coin <= i and min[i-coin] + 1 < min[i]):
            min[i] = min[i-coin] + 1

print min[targetSum]