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
function solve(input) {
  const t0 = Date.now();
  let start = [0,0];
  let target = [0,0];
  const stack:any= []
  for (let i= 0; i < input.length ; i++) {
    for (let j =0 ; j < input[i].length; j++) {
      if (input[i].charAt(j) == 'S') {
        console.log('A');
        start = [i, j];
      }
      if (input[i].charAt(j) == 'E') {
        console.log('B');
        target = [i, j];
      }
    }
  }
  const visited = new Set();
  stack.push([start[0], start[1], 0]);
  while(stack.length > 0) {
    const [y,x,d0] = stack.pop();
    if (y == target[0] && x == target[1]) {
      return d0;
    }
    const d = [[0,1], [0,-1], [1,0], [-1,0]];
    for (const [dy, dx] of d) {
      let newx = x + dx;
      let newy = y + dy;
      const str = `${newy}~${newx}`;
      if (visited.has(str)) {
        continue;
      }
      if (newx < 0 || newx >= input[0].length || newy < 0 || newy >= input.length) {
        continue;
      }
      let cc0 =input[y].charAt(x) ;
      if (cc0 == 'S') cc0 = 'z';
      let cc1 =input[y+dy].charAt(x+dx) ;
      const c0 = cc0.charCodeAt(0);
      const c1 = cc1.charCodeAt(0);
      if (c0 - c1 > 1) {
        continue;
      }
      if (cc1 == 'a') {
        const delta = Date.now() - t0;
        console.log('delta', delta);
        return d0 + 1;
      }
      visited.add(str);
      stack.unshift([y+dy, x+dx, d0+1]);
    }
  }
  return 0;
}

