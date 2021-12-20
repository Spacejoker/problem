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
  const w = input[0].length;
  const h = input.length;
  const minCost = new Array(h*5).fill(9999999).map(() =>new Array(w*5).fill(9999999));
  minCost[0][0] = 0;
  // x, y
  const stack : any= [[0,0,0]];

  while(stack.length > 0) {
    stack.sort((a,b)=>b[2]-a[2]);
    const pop : any= stack.pop();
    const dir = [[0,1],[0,-1],[1,0],[-1,0]];
    for (const d of dir) {
      const newx = d[0] + pop[0];
      const newy = d[1] + pop[1];
      //console.log('new', d, d[0], d[1], pop, newx, newy);
      if (newx < w*5 && newx >=0 && newy < h*5 && newy >=0) {
        let cellval = Number(input[newy%h].charAt(newx%w))
        const xshift = (newx / w)|0;
        const yshift = (newy / h)|0;
        cellval += xshift + yshift;
        while(cellval > 9) cellval -= 9;
        const newcost = minCost[pop[0]][pop[1]] + cellval ;
        if (newcost < minCost[newx][newy]){
          if (newx == w*5-1 && newy == h*5-1)return newcost;
          minCost[newx][newy] = newcost;
          stack.push([newx,newy, newcost]);
        }
      }
    }
  }
  return minCost[w*5-1][h*5-1];
}
