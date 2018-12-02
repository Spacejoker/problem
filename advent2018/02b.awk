# To run, input in input.txt:
# $ awk -f 02b.awk <(paste -d' ' -s input.txt)
{ 
  for(i = 1; i <= NF; i++) { 
    for(j = i+1; j <= NF; j++) { 
      mis = 0
      common = ""
      for (k = 0; k < length($i); k++){
        if (substr($i,k,1) != substr($j,k,1)) {
          mis = mis + 1
        } else {
          common = common substr($i,k,1)
        }
      }
      if (mis == 1) {
        print(common);
      }
    }
  }
}
