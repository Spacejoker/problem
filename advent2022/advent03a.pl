# $ perl advent03a.pl < input.txt

use strict;
use warnings;

my $file = 'input.txt';
open my $info, $file or die "Could not open $file: $!";

my $sum = 0;

while( my $line = <$info>)  {   
  my $p1 = substr $line, 0, length($line)/2;
  my $p2 = substr $line, length($line)/2;
  my $c1;
  my $c2;
  my $match;
  foreach $c1 (split //, $p1) {
    foreach $c2 (split('', $p2)) {
       if ($c1 eq $c2) {
         $match = $c1;
       }
    }
  }
  my $cc = ord($match);
  if ($cc > 97) {
    $cc -= 96;
  } else{
    $cc -= 64;
    $cc +=26;
  }
  $sum += $cc;
}

print "$sum\n";

close $info;
