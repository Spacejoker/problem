def name = System.in.newReader().readLine()
def split = name.split(" ")
for (i=0;i<split.length;i++){
  split[i] = split[i] as int;
}
println go(split, 0);

def go(a, i) {
  def children = a[i++] as int;
  def metadata = a[i++] as int;
  def sum = 0;
  for(def j=0;j<children;j++){
    l = go(a, i);
    sum += l[0]
    i = l[1]
  }
  for(def k=0;k<metadata;k++){
    sum += a[i++] as int;
  }
  return [sum, i];
}
