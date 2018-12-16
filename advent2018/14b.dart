void main() {
  var n = [1,5,7,9,0,1];
  var e1pos = 0;
  var e2pos = 1;
  var recipe = [3,7];
  while(recipe.length < 30000000) {
    var sum = recipe[e1pos] + recipe[e2pos];
    var digits = [];
    if(sum == 0) digits.add(0);
    while(sum > 0) {
      digits.add(sum%10);
      sum = sum ~/ 10;
    }
    for(var i =digits.length-1 ; i >= 0 ; i--) {
     recipe.add(digits[i]);
    }
    e1pos = (e1pos + recipe[e1pos] + 1) % recipe.length;
    e2pos = (e2pos + recipe[e2pos] + 1) % recipe.length;
    
    var c = "";

  }
  for(var i =0; i < recipe.length - n.length; i++){
   var match = 1;
   for(var j =0 ; j < n.length ; j++) {
    if (n[j] != recipe[i+j]){
      match = 0;
    }
   }
   if (match == 1){
    print("match");
    print(i);
    break;
   }
  }
print("done");
}
