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
  console.log(solve2(input));
});

// End boilerplate.
function solve(input) {
  const seen = new Set();
  const w = input[0].length, h = input.length;
  for (let i= 0 ; i < h; i++) {
    for (let j =0; j < w; j++) {
      let D = [[0,1],[0,-1],[1,0],[-1,0]];
      let H = Number(input[i].charAt(j));
      for (const [dx,dy] of D) {
        let works  = true;
        let x =j +dx , y = i + dy;
        while (x >= 0 && x < w && y >= 0 && y < h) {
          let curH = Number(input[y].charAt(x));
          if (curH >= H) works = false;
          x += dx;
          y += dy;
        }
        if (works) {
          seen.add(`${j}~${i}`);
        }
      }
    }
  }
  return seen.size;
}

// End boilerplate.
function solve2(input) {
  const seen = new Set();
  const w = input[0].length, h = input.length;
  let best = 0;
  for (let i= 0 ; i < h; i++) {
    for (let j =0; j < w; j++) {
      let D = [[0,1],[0,-1],[1,0],[-1,0]];
      let H = Number(input[i].charAt(j));
      let scenic = 1;
      for (const [dx,dy] of D) {
        let works  = true;
        let x =j +dx , y = i + dy;
        let dist = 0;
        while (x >= 0 && x < w && y >= 0 && y < h) {
          dist ++;
          let curH = Number(input[y].charAt(x));
          if (curH >= H) break;
          x += dx;
          y += dy;
        }
        scenic *= dist;
      }
      best = Math.max(best, scenic);
    }
  }
  return best;
}
