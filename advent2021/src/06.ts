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
  let timers = input[0].split(',').map(Number);
  let cnt = [0,0,0,0,0,0,0,0,0];
  for (const t of timers) cnt[t] ++;
  for ( let i=0 ; i < 256; i++) {
    let next = [0,0,0,0,0,0,0,0,0];
    for (let j =0; j < 8; j++) {
      next[j] = cnt [j+1];
    }
    next[6] += cnt[0];
    next[8] += cnt[0];
    cnt = next;
  }
  let sum =0;
  for (let i= 0; i < 9; i++) sum += cnt[i];
  return sum;
}
