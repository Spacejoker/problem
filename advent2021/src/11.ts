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
function toNum(input) {
  return function(x, y) {
    return Number(input[y].charAt(x));
  }
}
function solve(input) {
  const w = input.length;
  const MAX_STEPS = 10000;
  const grid :any[] = [];
  for (const row of input) {
    const output: number[] = [];
    for (let i= 0; i < row.length; i++) {
      output.push(Number(row.charAt(i)));
    }
    grid.push(output);
  }

  let cnt = 0;
  for (let step = 0; step < MAX_STEPS; step ++) {
    let flashes : number[][] = [];
    for(let y= 0; y < w; y++) {
      for (let x = 0; x < w; x++)  {
        grid[y][x] ++;
        if (grid[y][x] > 9) {
          flashes.push([x, y]);
        }
      }
    }
    while(flashes.length > 0) {
      const tmp = flashes;
      flashes = [];
      const dir = [[-1,-1],[-1,0],[-1,1],
        [0,-1], [0,1],
        [1,-1],[1,0],[1,1]];
      for (const t of tmp) {
        for (const d of dir) {
          let newx = t[0] + d[0];
          let newy = t[1] + d[1];
          if (newx >= 0 && newx < w && newy >=0 && newy < w) {
            grid[newy][newx] ++;
            if (grid[newy][newx] == 10) flashes.push([newx, newy]);
          }
        }
      }
    }

    let thisCnt = 0;
    for(let y= 0; y < w; y++) {
      for (let x = 0; x < w; x++)  {
        if (grid[y][x] > 9) {
          grid[y][x] = 0;
          cnt ++;
          thisCnt ++;
        }
      }
    }
    if (thisCnt == 100) return step;
    
    console.log('\n=================');
    console.log('STEP ', step);
    for(let y= 0; y < w; y++) {
      const s = grid[y].join('');
      console.log(s);
    }
  }

  // const grid = new Array(h).fill(false).map(() =>new Array(w).fill(false));
  return cnt;
}

