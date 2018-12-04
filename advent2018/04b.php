<?php

$stdin = fopen('php://stdin', 'r');
$input = array();

while(($line = trim(fgets(STDIN))) != "")
array_push($input, $line);

sort($input);

$guard = "";
$sleep = 0;
$map=[];
for($i=0;$i<count($input);$i++) {
  $split = explode(" ", $input[$i]);
  if($split[2]=="Guard"){
    $guard = $split[3];
    if (!array_key_exists($guard, $map)) {
      $map[$guard] = [];
    }
  } else if ($split[2] == "falls") {
    $sleep = substr($split[1], 3,2);
  } else {
    $wakes = substr($split[1], 3,2);
    for($j = $sleep+0; $j < $wakes+0; $j++){
      if (!array_key_exists($j, $map[$guard])) {
        $map[$guard][$j] = 0;
      }
      $map[$guard][$j] ++;
    }
  }
}

$bestVal = 0;
$best = "";
foreach (array_keys($map) as $key) {
  foreach (array_keys($map[$key]) as $i) {
    if ($map[$key][$i]>$bestVal) {
      $bestVal = $map[$key][$i];
      $best = $key;
      print "Best\n";
      print $key;
      print "*";
      print $i;
      print "*";
      print $bestVal;
      print "\n";
    }
  }
}
?>
