fileName = 'test1.in'

f = open(fileName,'r');
fOut = open(fileName + '-solution.txt','w')

nrOfCases = int(f.readline()) #nr test cases is ignored

def isUgly(val):
	if(val%2 != 0 or val%3 != 0 or val%5 != 0 or val%7 != 0):
		return 1
	return 0

for case in range (0, nrOfCases):
	result = ''
	
	#read the line(s)
	line = f.readline()
	print line
	numbers = []
	for char in line:
		numbers.append(int(char))
	
	for index in range(0,numbers + 1):
		#split into all possibilities with the given nr of cuts
		
		print index
		
	print isUgly(236)
	
	print 'Case #' + str(case+1) + ': ' + result
	fOut.write('Case #' + str(case+1) + ': ' + result + '\n')
	
f.close()
fOut.close()