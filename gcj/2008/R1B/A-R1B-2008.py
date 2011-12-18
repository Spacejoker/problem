fileName = 'A-large-practice(2).in'

f = open(fileName,'r');
fOut = open(fileName + '-solution.txt','w')

nrOfCases = int(f.readline()) #nr test cases is ignored


def getCenterPointIsInt(t1, t2, t3):
	x = (t1[0]+t2[0]+t3[0])/3
	y = (t1[1]+t2[1]+t3[1])/3
	#print 'x: ' + str(x)
	#print 'y: ' + str(y)
	return x-int(x) == 0 and y - int(y) == 0

for case in range (0, nrOfCases):
	result = ''
	
	#read the line(s)
	line = f.readline()
	splitLine = line.split(" ")
	n = int(splitLine[0])
	A = int(splitLine[1])
	B = int(splitLine[2])
	C = int(splitLine[3])
	D = int(splitLine[4])
	x0 = int(splitLine[5])
	y0 = int(splitLine[6])
	M = int(splitLine[7])
	
	
	trees = []
	
	X = x0
	Y = y0
	trees.append((float(X), float(Y)))
	for i in range(1, n):#= 1 to n-1
		X = (A * X + B) % M
		Y = (C * Y + D) % M
		trees.append((float(X), float(Y)))

	nrOfTrees = len(trees)
	
	pointsFound = 0
		
	for t1Index in range(0,nrOfTrees):
		for t2Index in range(t1Index+1, nrOfTrees):
			for t3Index in range(t2Index+1, nrOfTrees):
				if(getCenterPointIsInt(trees[t1Index], trees[t2Index], trees[t3Index] )):
					pointsFound += 1

	result = str(pointsFound)
	
	print 'Case #' + str(case+1) + ': ' + result
	fOut.write('Case #' + str(case+1) + ': ' + result + '\n')
f.close()
fOut.close()




























