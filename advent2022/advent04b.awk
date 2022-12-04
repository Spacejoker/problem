# echo "$(($(cat input.txt | wc -l) * $(awk -f advent04.awk < input.txt  | wc -l)))"
{
  split($0,X,",");
  split(X[1], a,"-");
  split(X[2], b,"-");
  if (a[2] < b[1] || a[1] > b[2]) print "|";
}
