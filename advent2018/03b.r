myString <- "Hello, World!"
print (myString)

f <- file("stdin")
open(f)

xdim = 5000
ydim = xdim
pelt = seq(0,0,length.out=xdim*ydim)
lines = c()
while(length(line <- readLines(f,n=1)) > 0) {
  lines <- c(lines, line)
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
close(f)


for(line in lines) {
  row <- unlist(strsplit(line, " "))
  coords <- unlist(strsplit(row[3], ","))
  y <- strtoi(coords[1])+1
  x <- strtoi(substr(coords[2],1,nchar(coords[2])-1))+1
  dims <- unlist(strsplit(row[4], "x"))
  h <- strtoi(dims[1])
  w <- strtoi(dims[2])
  found = 0
  for (yy in (y):(y+h-1)) {
    for (xx in (x):(x+w-1)) {
      idx = (xdim*yy) + xx
      found = max(found, pelt[idx])
    }
  }
  if (found == 1) {
    write(row[1], stderr())
  }
}
