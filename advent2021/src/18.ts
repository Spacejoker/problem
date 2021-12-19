import * as readline from 'readline';
require('source-map-support').install();

// File reading.
// const grid = new Array(h).fill(false).map(() =>new Array(w).fill(false));

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
  terminal: true
});

export async function readFile() {
  let fullFile : string[] = [];
  for await (const line of rl) {
    fullFile.push(line);
  }
  return fullFile;
}

readFile().then((input) => {
  let max =0;
  for (let i =0 ; i < input.length; i++) {
  for (let j =i+1 ; j < input.length; j++) {
    max = Math.max(max, solve([input[i], input[j]]));
    max = Math.max(max, solve([input[j], input[i]]));

  }
  }
  console.log(max);
});

function parseNums(input) {
  return input.split(',').map(Number);
}

//// End boilerplate.
function toNum(input) {
  return function(x, y) {
    return Number(input[y].charAt(x));
  }
}

interface Add {
  rec: boolean;
  left?: Add;
  right?: Add;
  value?: number;
  depth?: number;
}
function parseSnailfish(s : string) {
  let left, right, value;
  if (s.charAt(0) == '[') {
    let leftStart = 1;
    let leftEnd = 1;
    let depth =0 ;
    while(true) {
      const c = s.charAt(leftEnd);
      if (depth == 0 && c == ',') {
        left = parseSnailfish(s.substring(leftStart, leftEnd));
        break;
      }
      if (c == '[') depth ++;
      if (c == ']') depth --;
      leftEnd ++;
    }
    let rightStart = leftEnd+1;
    let rightEnd = 1;
    while(true) {
      const c = s.charAt(rightEnd);
      if (depth == 0 && c == ']') {
        console.log('parsing', s.substring(rightStart, rightEnd));
        right = parseSnailfish(s.substring(rightStart, rightEnd));
        break;
      }
      if (c == '[') depth ++;
      if (c == ']') depth --;
      rightEnd++;
    }
  } else {
    value = Number(s);
  }
  return {rec: !!left, left, right, value};
}

function solve(input) {
  const nums = input.map(parseSnailfish);
  let val = nums[0];
  for(let i= 1; i < input.length; i++) {
    val = { rec: true, left: val, right: nums[i] };
    // reduce:
    red: while(true) {
      // explode
      const flat = flatten(val);
      for (let j =0 ; j < flat.length ;j++) {
        if (flat[j].rec && flat[j].depth == 4) {
          const explode = flat[j];
          for(let k = j-1; k >= 0; k--) {
            if (!flat[k].rec) {
              flat[k].value += explode.left.value;
              break;
            }
          }
          for(let k = j+3; k < flat.length; k++) {
            if (!flat[k].rec) {
              flat[k].value += explode.right.value;
              break;
            }
          }
          explode.rec = false;
          explode.value = 0;
          continue red;
        }
      }
      for (let j =0 ; j < flat.length ;j++) {
        const split = flat[j];
        if (!split.rec && split.value > 9) {
          split.rec = true;
          split.left = {rec: false, value: ((split.value / 2 )|0)};
          split.right = {rec: false, value: ((split.value / 2 )|0) + split.value % 2};
          continue red;
        }
      }
      // split
      break;
    }
  }

  console.log('parsed:');
  console.log(toStr(val));
  return magnitude(val);
  //return 1;
}

function magnitude(add: Add) {
  if (add.rec) {
    return magnitude(add.left!)*3 + magnitude(add.right!)*2;
  }
  else {
    return add.value!;
  }
}

function flatten(add: Add, depth = 0) {
  add.depth = depth;
  if (add.rec == true) {
    return [add, ...flatten(add.left!, depth+1), ...flatten(add.right!, depth+1)];
  } else {
    return [add];
  }
}

function toStr(val: Add) {
  let ret = '';
  if (val.rec == true) {
    ret += '[';
    ret += toStr(val.left!);
    ret += ',';
    ret += toStr(val.right!);
    ret += ']';
  } else {
    ret += val.value;
  }
  return ret;
}

