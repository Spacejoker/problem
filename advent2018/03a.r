myString <- "Hello, World!"
print (myString)

f <- file("stdin")
open(f)

xdim = 5000
ydim = xdim
pelt = seq(0,0,length.out=xdim*ydim)

while(length(line <- readLines(f,n=1)) > 0) {
  row <- unlist(strsplit(line, " "))
  coords <- unlist(strsplit(row[3], ","))
  y <- strtoi(coords[1])+1
  x <- strtoi(substr(coords[2],1,nchar(coords[2])-1))+1
  dims <- unlist(strsplit(row[4], "x"))
  h <- strtoi(dims[1])
  w <- strtoi(dims[2])
  for (yy in (y):(y+h-1)) {
    for (xx in (x):(x+w-1)) {
      idx = (xdim*yy) + xx
      pelt[idx] <- (pelt[idx] + 1)
    }
  }
}
cnt = 0
for (i in 1:(xdim*ydim)){
  if(pelt[i]>1) {
    cnt = cnt + 1
  }
}
write(cnt, stderr())
close(f)
