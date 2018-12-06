coords = [[350, 353], [238, 298], [248, 152], [168, 189], [127, 155], [339, 202], [304, 104], [317, 144], [83, 106], [78, 106], [170, 230], [115, 194], [350, 272], [159, 69], [197, 197], [190, 288], [227, 215], [228, 124], [131, 238], [154, 323], [54, 185], [133, 75], [242, 184], [113, 273], [65, 245], [221, 66], [148, 82], [131, 351], [97, 272], [72, 93], [203, 116], [209, 295], [133, 115], [355, 304], [298, 312], [251, 58], [81, 244], [138, 115], [302, 341], [286, 103], [111, 95], [148, 194], [235, 262], [41, 129], [270, 275], [234, 117], [273, 257], [98, 196], [176, 122], [121, 258]]
dim = 500
count = {}
bad = {}

# Manhattan distance
dist = (c, x, y) -> Math.abs(c[0]-x) + Math.abs(c[1]-y)

# Check if it is on the border (disqualify the coord), or increment its count.
check = (i, target,x, y) ->
 if (dist coords[i], x, y) == target
  if x == 0 || x == dim || y == 0 || y== dim
   bad[i] = true
  else
   count[i] = (count[i]+1) || 1

examine = (x, y) ->
 do (distances = (coords.map (c) -> (dist c, x, y))) ->
  do (sorted = distances.sort((a,b)->a-b)) ->
   if sorted[0] != sorted[1]
    ((check i,sorted[0],x,y) for i in [0 .. coords.length-1])

# Main algo
(((examine x, y) for y in [0 .. dim]) for x in [0 .. dim])

# Cleanup
clear = (k) -> count[k] = 0;
((clear k) for k in Object.keys(bad))

# Find  max
max = 0;
(max = Math.max(max, m) for m in Object.values(count))

console.log(max);
