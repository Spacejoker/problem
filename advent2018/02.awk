# Usage:
# awk -f 02.awk < input.txt | grep 2 | wc -l
# A
# awk -f 02.awk < input.txt | grep 3 | wc -l
# B
# echo "$(( A * B ))"

{
  split($0, a, "");
  asort(a);
  cnt = 0
  last = ""
  two = 0
  three = 0
  for(i=1;i<=length(a);i++) {
    if(a[i] != last) {
      if (cnt == 2)
        two = 2
      if (cnt == 3)
        three = 3
      cnt = 0
    }
    last = a[i]
    cnt = cnt + 1
  }
  if (two || cnt == 2)
    print("2 ")
  if (three || cnt == 3)
    print("3 ")
  printf("\n");
}
