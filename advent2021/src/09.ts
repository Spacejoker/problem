import * as readline from 'readline';
require('source-map-support').install();

// File reading.

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
  console.log(solve(input));
});

function parseNums(input) {
  return input.split(',').map(Number);
}

//// End boilerplate.
//function solve(input) {
//  const h = input.length;
//  const w = input[0].length;
//  let ans = 0;
//  for (let y =0; y < input.length; y++) {
//    for (let x =0; x < input[0].length; x++) {
//      let val = Number(input[y].charAt(x)), smallest = 10;
//      let dir = [[0,1],[0,-1],[1,0],[-1,0]];
//      for (const d of dir) {
//        let newx = x + d[0];
//        let newy = y + d[1];
//        if (newx >=0 && newx < w && newy >=0 && newy < h) {
//let cmp = Number(input[newy].charAt(newx));
//          smallest = Math.min(smallest, cmp);
//        }
//      }
//      if (val < smallest) {
//        console.log(x,y,val, smallest);
//        ans += val+1;
//      }
//    }
//  }
//  return ans;
//}
// End boilerplate.
function toNum(input) {
  return function(x, y) {
    return Number(input[y].charAt(x));
  }
}
function solve(input) {
  const h = input.length;
  const w = input[0].length;
  const num = toNum(input);
  const grid = new Array(h).fill(false).map(() =>new Array(w).fill(false));
  const allSizes : number[] = [];
  for (let y =0; y < input.length; y++) {
    for (let x =0; x < input[0].length; x++) {
      if (!grid[y][x] && num(x, y) != 9) {
        const stack :any[]= [[x,y]];
        grid[y][x] = true;
        let size = 1;

        while(stack.length > 0) {
          let dir = [[0,1],[0,-1],[1,0],[-1,0]];
          let x0, y0;
          [x0, y0] = stack.pop();
          for (const d of dir) {
            let newx = x0 + d[0];
            let newy = y0 + d[1];
            if (newx >=0 && newx < w && newy >=0 && newy < h &&
                !grid[newy][newx] && num(newx, newy) != 9) {
              stack.push([newx, newy]);
              grid[newy][newx] = true;
              size ++;
            }
          }
        }
        allSizes.push(size);
      }
    }
  }
  allSizes.sort((a:number,b:number) => b - a);
  console.log(allSizes);
  const ans = 0;
  return allSizes[0] * allSizes[1] * allSizes[2];
}
