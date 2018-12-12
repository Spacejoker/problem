import strutils
echo "hi"

var xs: array[1000, int];
var ys: array[1000, int];
var xvs: array[1000, int];
var yvs: array[1000, int];
var cnt:int = 0;

for line in stdin.lines:
  
  var initSplit = line.split(',')
  var x:int = parseInt(initSplit[0].split("position=<")[1].strip())
  var midSplit = initSplit[1].split("> velocity=<");
  var y:int = parseInt(midSplit[0].strip())
  var xv:int = parseInt(midSplit[1].strip())
  var yv:int = parseInt(initSplit[2].split('>')[0].strip())
  xs[cnt] = x
  ys[cnt] = y
  xvs[cnt] = xv
  yvs[cnt] = yv
  cnt = cnt + 1
echo "23"
var ydiff:int = 10000
var xdiff:int = 10000
for unused in 1..50000:
  var maxx = -100000
  var minx = 100000
  var maxy = -100000
  var miny = 100000
  for i in 0..(cnt-1):
    maxx = max(maxx, xs[i]);
    minx = min(minx, xs[i]);  
    maxy = max(maxy, ys[i]);
    miny = min(miny, ys[i]);
    xs[i] = xs[i] + xvs[i]
    ys[i] = ys[i] + yvs[i]


  -- Print any candidate where we have a new low in either x or y.
  if (maxx-minx<xdiff or maxy-miny < ydiff):
    -- Good use of variable name, up until the second star.
    echo unused
    xdiff = maxx-minx
    ydiff = maxy-miny
    if xdiff < 100 and ydiff < 100:
      for y in miny..maxy:
        var l = ""
        for x in minx..maxx:
          var found = '.'
          for c in 0..(cnt-1):
            if xs[c] == x and ys[c] == y:
              found = '#'
          l= l&found
        echo l
    
echo xdiff, ydiff
