void main() {
  var n = 157901;
  var e1pos = 0;
  var e2pos = 1;
  var recipe = [3,7];
  var target = n + 10;
  while(recipe.length < target+5) {
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
  }

  var c = "";
  for(var i =0; i< 10; i++){
    c = c + recipe[n + i].toString();
  }
  print(c);
}
