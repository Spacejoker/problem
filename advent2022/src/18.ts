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
  const h = 100, w =100;
  const grid = new Array(h).fill(false).map(() =>new Array(w).fill(false).map(() => new Array(100).fill(false)));
  const seek = new Array(h).fill(false).map(() =>new Array(w).fill(false).map(() => new Array(100).fill(false)));

  for (const row of input) {
    const [x,y,z] = row.split(',').map(Number);
    grid[x+1][y+1][z+1] = true;
  }

  const stack : any = [];
  stack.push([0,0,0]);
  while(stack.length > 0) {
    const [x,y,z] = stack.pop();
    const dir = [[0,0,1],[0,0,-1],[0,1,0],[0,-1,0],[1,0,0],[-1,0,0]];
    for (const d of dir) {
      let newx = d[0] + x;
      let newy = d[1] + y;
      let newz = d[2] + z;
      if (newx >= 0 && newx < 100 && newy >= 0 && newy < 100 && newz >= 0 && newz<100 && !grid[newx][newy][newz] && !seek[newx][newy][newz]) {
        stack.push([newx,newy,newz]);
        seek[newx][newy][newz] = true;
      }
    }
  }

  let cnt = 0;
  for (const row of input) {
    const [x_,y_,z_] = row.split(',').map(Number);
    const x = x_ + 1;
    const y = y_ + 1;
    const z = z_ + 1;
    const dir = [[0,0,1],[0,0,-1],[0,1,0],[0,-1,0],[1,0,0],[-1,0,0]];
    for (const d of dir) {
      let newx = d[0] + x;
      let newy = d[1] + y;
      let newz = d[2] + z;
      if (!grid[newx][newy][newz] && seek[newx][newy][newz]) {
        cnt ++;
      }
    }
  }
  return cnt;
}
