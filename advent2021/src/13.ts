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
  const w = 2000, h = 2000;
  const grid = new Array(h).fill(false).map(() =>new Array(w).fill(false));
  let mode = true;
  const pts :any= [];

  for (const row of input) {
    if (row == '') {mode = false;}
    else if (mode) {
      pts.push(row.split(','));
    } else {
      console.log('fold', row);
      const txt = row.split(' ')[2];
      const isx = txt.charAt(0) == 'x';
      const val = Number(txt.split('=')[1]);
      const idx = isx ? 0 : 1;
      for (const p of pts) {
        if (p[idx] > val) {
          p[idx] = val - Math.abs(val-p[idx]);
        }
      }
    }
  }
  const s = new Set();
  for (const p of pts) {
    s.add(`${p[0]}-${p[1]}`);
  }
  const dim = 100;
    for (let y =0; y < dim; y++) {
    let out = '';
  for (let x =0 ; x < dim; x++) {
      if (s.has(`${x}-${y}`)) out += '#';
      else out += '.';
    }
    console.log(out);
  }
  let size = 0;
  for (const e of s.entries()) {
    size ++;
  }
  //x=655
  return size;
}
