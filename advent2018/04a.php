<?php

$stdin = fopen('php://stdin', 'r');
$input = array();

while(($line = trim(fgets(STDIN))) != "")
array_push($input, $line);

sort($input);

# Sweep one, find the sleepiest guard.
$guard = "";
$sleep = 0;
$map=[];
for($i=0;$i<count($input);$i++) {
  $split = explode(" ", $input[$i]);
  if($split[2]=="Guard"){
    $guard = $split[3];
    if (!array_key_exists($guard, $map)) {
      $map[$guard] = 0;
    }
  } else if ($split[2] == "falls") {
    $sleep = substr($split[1], 3,2);
  } else {
    $wakes = substr($split[1], 3,2); 
    $map[$guard] = $map[$guard] + ($wakes - $sleep);
  }
}
$bestVal = 0;
$best = "";
foreach (array_keys($map) as $key) {
  if ($map[$key]>$bestVal) {

    $bestVal = $map[$key];
    $best = $key;
  }
}

print $best;
print "\n";
print $map[$best];

$times = [];

# Sweep two, for this guard, check all minutes.
$on = 0;
for($i=0;$i<count($input);$i++) {
  $split = explode(" ", $input[$i]);
  if($split[2]=="Guard"){
    if ($split[3] == $best) {
      $on = 1;
    } else {
      $on = 0;
    } 

  } else if ($split[2] == "falls") {
    $sleep = substr($split[1], 3,2);
  } else {
    $wakes = substr($split[1], 3,2); 
    if($on == 1) {

      for($j = $sleep; $j < $wakes; $j++){
        if (!array_key_exists($j, $times)) {
          $times[$j] = 0;
        }
        $times[$j] ++;
      }
    }
  }
}
print "ok?\n";
foreach (array_keys($times) as $key) {
  print $key;
  print ": ";
  print $times[$key];
  print "\n";
}

?>
