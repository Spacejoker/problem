my @arr = ();
my @other = ();
while (<STDIN>) {
  chomp($_);
  last if ($_ eq '');
  push(@arr, $_);
  push(@other, $_);
}
$maxiter = 1100;
$kscore = 0;

$h = scalar(@arr);
$w = length(@arr[0]);
for ($iter = 0; $iter < $maxiter; $iter = $iter + 1) {
  @other = @arr;
  for( $y = 0; $y < scalar(@arr); $y = $y + 1 ) {
    for( $x = 0; $x < length(@arr[$y]); $x = $x + 1) {
      $lumber = 0;
      $tree = 0;
      $ground = 0;
#print " new loop\n";

      for ($dy = -1; $dy <= 1; $dy = $dy + 1){
        for ($dx = -1; $dx <= 1; $dx = $dx + 1) {
          $yy = $y + $dy; $xx = $x + $dx; 
#print $yy, ",", $xx, " ----  ", $y, ", ", $x, "\n";
          if($yy != $y || $xx != $x) {
            if($xx >= 0 && $xx < $w && $yy >= 0 && $yy < $h) {
              if(substr(@arr[$yy], $xx, 1) =~ /\./) {
                $ground = $ground + 1;
              }
              if(substr(@arr[$yy], $xx, 1) =~ /#/) {
                $lumber = $lumber + 1;
              }
              if(substr(@arr[$yy], $xx, 1) =~ /\|/) {
                $tree = $tree + 1;
              }
            }
          }
        }
      }

      if(substr(@arr[$y], $x, 1) =~ /\./ && $tree >= 3) {
        substr(@other[$y], $x, 1, "|")
      }
      if(substr(@arr[$y], $x, 1) =~ /#/ && ($lumber == 0 || $tree == 0)) {
        substr(@other[$y], $x, 1, ".")
      }
      if(substr(@arr[$y], $x, 1) =~ /\|/ && $lumber >= 3) {
        substr(@other[$y], $x, 1, "#")
      }
    }
  }
  @arr = @other;
  $treecnt = 0;
  $lumbercnt = 0;

  for( $by = 0; $by < scalar(@arr); $by = $by + 1 ) {
    for( $bx = 0; $bx < length(@arr[$by]); $bx = $bx + 1) {
#print substr(@arr[$by], $bx, 1);
      if(substr(@arr[$by], $bx, 1) =~ /#/) {
        $lumbercnt = $lumbercnt + 1;
      }
      if(substr(@arr[$by], $bx, 1) =~ /\|/) {
        $treecnt = $treecnt + 1;
      }
    }
  }
  $score = $lumbercnt * $treecnt;
  if ($score == $kscore) {
    print "Candidate:", $iter," - ", $kscore, " - ", $score, "\n";
  }
  if ($iter == 1027) {
    print "answer:", $score, "\n";
  }
  if ($iter == 999) {
    $kscore = $lumbercnt * $treecnt;
  }

}

foreach my $loop_variable (@arr) {
#print $loop_variable;
#print "\n"
}

printf "Number of elements in \@arr: %d\n", scalar(@arr);
