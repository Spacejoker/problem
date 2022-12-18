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
  const t0 =Date.now() ; 

  const grid = Array(1000).fill(0).map(x => Array(1000).fill('.'))
  let maxy =0 ;

  for (let i = 0; i < input.length; i++) {
    const row = input[i];
    let pts = row.split(' -> ');
    pts = pts.map(p => p.split(',').map(Number));
    for (let p = 0; p < pts.length-1; p++) {
      // a = [x, y]
      const a = pts[p];
      const b = pts[p+1];
      let dx =0, dy =0 ;
      if (a[0] != b[0]) dx = a[0] < b[0] ? 1 : -1;
      if (a[1] != b[1]) dy = a[1] < b[1] ? 1 : -1;
      while(a[0] != b[0] || a[1] != b[1]) {
        maxy = Math.max(a[1], maxy);
        grid[a[1]][a[0]]  = '#';
        a[0] += dx;
        a[1] += dy;
      }
      grid[a[1]][a[0]]  = '#';
      maxy = Math.max(a[1], maxy);
    }
  }

  for (let x =0 ; x < 1000; x++) {
    grid[maxy+2][x] = '#';
  }
  // keep placing
  let cnt =0 ;
  while(true) {
    let p = [500, 0];
    while(true) {
      if (p[1] > 800) {
        return cnt;
      }
      // is next free?
      if (grid[p[1]+1][p[0]] == '.') {
        p[1] ++;
        continue;
      } else if (grid[p[1]+1][p[0]-1] == '.') {
        p[1] ++;
        p[0] --;
        continue;
      } else if (grid[p[1]+1][p[0]+1] == '.') {
        p[1] ++;
        p[0] ++;
        continue;
      } else {
        if (p[0] == 500 && p[1] == 0){
          const t1 = Date.now();
          console.log('time:', t1-t0);
          return cnt+1;
        }
        grid[p[1]][p[0]] = '#';
        cnt ++;
        break;
      }
    }
  }

  return cnt-1;
}

