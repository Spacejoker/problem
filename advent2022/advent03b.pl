# $ perl advent03b.pl < input.txt
use strict;
use warnings;

# my @input;
my $file = 'input.txt';
open my $info, $file or die "Could not open $file: $!";

my $sum = 0;
my @loop = (0..99);

chomp(my @input  = <$info>);

for my $i (@loop) {
  my $match;
  foreach my $c1 (split //, @input[$i*3]) {
    foreach my $c2 (split //, @input[$i*3+1]) {
      foreach my $c3 (split //, @input[$i*3+2]) {
        if ($c1 eq $c2 and $c2 eq $c3) {
          $match = $c1;
        }
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
