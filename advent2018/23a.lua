s = io.read("*l")
i =0
nodes = {}
maxr = 0
while(1) do
 nodes[i] = {}
 x,y,z,r = string.match(s, "([-]*%d+)[^%d-]+([-]*%d+)[^%d-]+([-]*%d+)[^%d-]+(%d+)");
-- print(x);
-- print(y);
-- print(z);
-- print(r);
 nodes[i]["x"] = tonumber(x);
 nodes[i]["y"] = tonumber(y);
 nodes[i]["z"] = tonumber(z);
 nodes[i]["r"] = tonumber(r);
 maxr = math.max(maxr, nodes[i]["r"])
 i = i + 1
 s = io.read("*l")
 if s == nil then break end
end

inRange = 0
for k, v in pairs(nodes) do
 if (v["r"] == maxr) then
  inRange = 0
  for k2, v2 in pairs(nodes) do
   dist = math.abs(v2["x"] - v["x"]) + math.abs(v2["y"] - v["y"]) + math.abs(v2["z"] - v["z"])
   -- print(k2 .. ", distance: " .. dist)
   if dist <= v["r"] then
    -- print("found" .. k2)
    inRange = inRange + 1
   end
  end
 end
end
-- 215 too high
print("maxr: " .. maxr)
print("inrange: " .. inRange)
