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
  let padding = 4;
  const decoder = input[0];
  let grid = input.slice(2);
  let next = new Array(grid.length+padding).fill('');
  for (let iter =0; iter < 50; iter++) {
    for (let y =-(padding/2) ; y < grid.length+padding/2; y++) {
      for (let x =-(padding/2) ; x < grid[0].length+padding/2; x++) {
        let str = '';
        for (let dy =-1; dy <= 1; dy++) {
          for (let dx =-1; dx <= 1; dx++) {
            const newy = y + dy;
            const newx = x + dx;
            let c = iter % 2 == 0 ? '.' : '#';
            if (newx >= 0 && newx < grid[0].length && newy >= 0 && newy < grid.length) {
              c = grid[newy].charAt(newx);
            }
            str += c == '#' ? '1' : '0';
          }
        }
        const idx = parseInt(str, 2);
        next[y+padding/2] += decoder.charAt(idx);
      }
    }
    grid = next;
    next = new Array(grid.length+4).fill('');
  }

  let cnt = 0;
  for(let i =0; i < grid.length; i++) {
    for (let j =0 ; j < grid[i].length; j++) {
      if (grid[i].charAt(j) == '#') cnt ++;
    }
    console.log(grid[i]);
  }

  console.log('cnt', cnt);
  return cnt;
}
