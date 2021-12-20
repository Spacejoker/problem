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
  console.log(solve(input));
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

function solve(input) {
  let maxval =500;
  let cnt =0;
  let x0 =0 , x1 =0 , y0 =0 , y1 =0 ;
  [x0, x1, y0, y1] = input[0].split(',').map(Number);
  let best = 0;
  for (let xvel0 =0; xvel0 < maxval; xvel0++) {
    for (let yvel0 =-maxval; yvel0 < maxval; yvel0++) {
      let x =0, y =0, xvel = xvel0, yvel = yvel0;
      let maxy =0;
      while(true) {
        x+= xvel;
        xvel = Math.max(0, xvel-1);
        y += yvel;
        yvel --;
        maxy = Math.max(maxy, y);
        if (x >= x0 && x <= x1 && y >= y0 && y <= y1) {
          cnt ++;
          best = Math.max(best, maxy);
          break;
        }
        if (x > x1 || y < y0)break;
      }
    }
  }

  return cnt;
}
