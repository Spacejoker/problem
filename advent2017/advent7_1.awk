# Advent of code, day 7 part 1
{
  for (i = 4; i <= NF ; i++) {
    sub(/,/, "", $1)
    seen[$1] --
  }
  seen[$1] ++
}

END {
  for (name in seen)
    if (seen[name] > 0)
      print name
}
