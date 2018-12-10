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
  def points = [];
  def m = 0;

  for(def j=0;j<children;j++){
    l = go(a, i);
    points[m++] = l[0] as int;
    i = l[1]
  }

  def metavals = [];
  for(def k=0;k<metadata;k++){
    metavals[k] = a[i++] as int;
  }
  if(children > 0) {
    for(def k=0;k<metadata;k++){
      if(metavals[k]-1 >=0 && metavals[k] -1 < children) {
        sum += points[metavals[k]-1] as int
      }
    }
  } else {
    sum = metavals.sum();
  }
  return [sum, i];
}
