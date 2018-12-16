import Foundation
import Darwin

print("Hello world")
var lines = [[Character]]()
while let line = readLine() {
  lines.append(Array(line));
}

var trains:[(Int, Int, Character, Int)] = [];
for y in stride(from: 0, to: lines.count, by: 1) {
 var x = 0;
 for c in lines[y] {
  if c == "<" || c == ">" || c == "^" || c == "v" {
    let train:(Int, Int, Character, Int) = (y, x, c, 0);
    trains.append(train);
  }
  x = x + 1
 }
}

trains = trains.sorted(by: { $0.1 < $1.1 })
trains = trains.sorted(by: { $0.0 != $1.0 ? $0.0 < $1.0 : $0.1 < $1.1 })

for cycles in stride(from: 0, to:100000, by: 1) {
for i in stride(from: 0, to: trains.count, by: 1) {
  let t = trains[i]
  var y = t.0
  var x = t.1
  var d = t.2
  var r = t.3
  let c = lines[y][x]
  if(d == "^") {
    if (c == "/") {
      d = ">"
    } else if (c == "\\") {
      d = "<"
    } else if (c == "+") {
      if(r == 0) {
        d = "<"
      }
      if(r == 2) {
        d = ">"
      }
      r = r + 1
      r %= 3
    }
  } else if(d == "v") {
    if (c == "/") {
      d = "<"
    } else if (c == "\\") {
      d = ">"
    } else if (c == "+") {
      if(r == 0) {
        d = ">"
      }
      if(r == 2) {
        d = "<"
      }
      r = r + 1
      r %= 3
    }
  } else if(d == "<") {
    if (c == "/") {
      d = "v"
    } else if (c == "\\") {
      d = "^"
    } else if (c == "+") {
      if(r == 0) {
        d = "v"
      }
      if(r == 2) {
        d = "^"
      }
      r = r + 1
      r %= 3
    }
  } else if(d == ">") {
    if (c == "/") {
      d = "^"
    } else if (c == "\\") {
      d = "v"
    } else if (c == "+") {
      if(r == 0) {
        d = "^"
      }
      if(r == 2) {
        d = "v"
      }
      r = r + 1
      r %= 3
    }
  }
  if(d=="^"){y = y-1}
  if(d=="v"){y = y+1}
  if(d=="<"){x = x-1}
  if(d==">"){x = x+1}

  trains[i] = (y,x,d, r)
  //print(c)
for i in stride(from: 0, to: trains.count, by: 1){
  for j in stride(from: i+1, to: trains.count, by: 1){
    if (trains[i].0 == trains[j].0 && trains[i].1 == trains[j].1 && trains[i].2 != "e" && trains[j].2 != "e") {
      trains[i].2 = "e"
      trains[j].2 = "e"
      print("answer", cycles)
      print(trains[i], trains[j])
    }
  }
}
}
trains = trains.sorted(by: { $0.0 != $1.0 ? $0.0 < $1.0 : $0.1 < $1.1 })
var cnt = 0
var tt = trains[0]
for t in trains{
  if t.2 != "e"{
    cnt = cnt + 1
    tt = t
  }
}
if (cnt == 1){
  print(tt)
  exit(0)
}
}
for train in trains {
  print(train)
  }
// 123,41
