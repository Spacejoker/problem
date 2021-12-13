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
  const nums = input[0].split(',').map(Number);
  let lo =9999999, hi = -10293;
  for(const c of nums) {
    lo = Math.min(c, lo);
    hi = Math.max(c, hi);
  }
  let best = 999999999;
  for(let mid =lo; mid <= hi; mid++) {
    let cost =0 ;
    for (const c of nums) {
      const dist = Math.abs(mid - c);
      cost += dist * (dist + 1)/2;
    }
    best = Math.min(best, cost);
  }
  return best;
}

// 454100
