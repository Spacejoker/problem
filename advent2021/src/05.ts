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

// End boilerplate.

function solve(input) {
  let dim = 1001;
  const grid = new Array(dim).fill(0).map(() =>new Array(dim).fill(0));

  for (let i= 0; i < input.length; i++) {
    const pts = input[i].split(' -> ');
    const [x0, y0] = pts[0].split(',').map(Number);
    const [x1, y1] = pts[1].split(',').map(Number);

    const minx = Math.min(x0, x1), maxx = Math.max(x0, x1);
    const miny = Math.min(y0, y1), maxy = Math.max(y0, y1);
    if (minx != maxx && miny != maxy) {
      let len = maxx- minx + 1; 
      let ymod = y1 > y0? 1: -1;
      let xmod = x1 > x0? 1: -1;
      for(let t =0 ; t< len; t++) {
        grid[y0 + ymod * t ][x0 + xmod * t]++;
      }
    } else {
      for(let x = minx ; x <= maxx; x++) {
        for(let y = miny; y <= maxy; y++) {
          grid[y][x]++;
        }
      }
    }
  }

  let cnt =0, zeroes =0 ;
  for (let y =0;  y < grid.length; y++) {
    let s = '';
    for (let x =0 ; x < grid[0].length; x++) {
      if (grid[y][x] > 1) {
        cnt++;
      }
      s += grid[y][x];
    }
    console.log(s);
  }
  return cnt;
}
