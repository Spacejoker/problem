# awk -f advent04a.awk < input.txt  | wc -l
{
  split($0,X,",");
  split(X[1], a,"-");
  split(X[2], b,"-");
  if (a[1] <= b[1] && a[2] >= b[2]) print "|";
  else if (b[1] <= a[1] && b[2] >= a[2]) print "|";
}
