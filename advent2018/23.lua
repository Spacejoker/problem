math.randomseed(os.time())
s = io.read("*l")
i =0
nodes = {}
maxr = 0
minx =0
maxx =0
miny = 0
maxy = 0
minz = 0
maxz = 0
while(1) do
 x,y,z,r = string.match(s, "([-]*%d+)[^%d-]+([-]*%d+)[^%d-]+([-]*%d+)[^%d-]+(%d+)");
 minx = math.min(tonumber(minx), tonumber(x))
 maxx = math.max(tonumber(maxx), tonumber(x))
 miny = math.min(tonumber(miny), tonumber(y))
 maxy = math.max(tonumber(maxy), tonumber(y))
 minz = math.min(tonumber(minz), tonumber(z))
 maxz = math.max(tonumber(maxz), tonumber(z))
 nodes[i] = {x=tonumber(x),y=tonumber(y),z=tonumber(z),r=tonumber(r)}
 i = i + 1
 s = io.read("*l")
 if s == nil then break end
end

function getDistance(a, b)
  return math.abs(a.x-b.x) + math.abs(a.y-b.y) + math.abs(a.z-b.z)
end

function getCount(testNode)
 local cnt = 0
 for k,v in pairs(nodes) do
  dist = getDistance(testNode, v) -- math.abs(testNode.x - v.x) + math.abs(testNode.y - v.y) + math.abs(testNode.z - v.z)
  if (v.r >= dist) then
    cnt = cnt + 1
  end
 end
 return cnt
end

local bestCnt = 0
local bestDist = 9999999999
player = {x=0,y=0,z=0}

function eval(node)
 return getDistance(node, player), getCount(node)
end

function findLocalMax(pos)
  --print(pos.x .. ", found: " .. getCount(pos))
  local dist = getDistance(pos, player)
  local cnt = getCount(pos)
  if cnt < bestCnt then return end
  :: retry ::
  modified = 0
  for e = 0,16 do
    for sign = -1,1,2 do
      d, c = eval({x=pos.x + sign*2^e, y=pos.y, z=pos.z});
      if (c > cnt) or (c == cnt and d < dist) then
        modified = 1
        dist = d
        cnt = c
      end
      d, c = eval({x=pos.x , y=pos.y+ sign*2^e, z=pos.z});
      if (c > cnt) or (c == cnt and d < dist) then
        modified = 1
        dist = d
        cnt = c
      end
      d, c = eval({x=pos.x, y=pos.y, z=pos.z+ sign*2^e});
      if (c > cnt) or (c == cnt and d < dist) then
        modified = 1
        dist = d
        cnt = c
      end
    end
  end 
  if modified > 0 then
    -- :)
    goto retry
  end
  if (cnt > bestCnt or (bestCnt == cnt and dist < bestDist)) then
    bestCnt = cnt
    bestDist = dist
    print(bestCnt .. ", " .. bestDist)
  end
end

for i =0, 100 do
  local testnode = {x=math.random(minx, maxx), y=math.random(miny, maxy), z=math.random(minz, maxz)};
  findLocalMax(testnode)
end
findLocalMax({x=0, y=0, z=0})
-- too low: 73248199
-- too low: 117179552
-- 846 record
-- 868, 116977346.0
-- 116977346 too low
-- 870, 123577570.0 - incorrect


