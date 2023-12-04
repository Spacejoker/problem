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
  console.log('A:', solve(input));
});

// End boilerplate.
const maxNr = {
  'red': 12,
  'green': 13,
  'blue': 14,
};

function checkPossible(s: string) {
  const rounds = s.split(':')[1].split(';');
  const minCnt = {
    'red': 0,
    'green': 0,
    'blue': 0,
  };
  for (const r of rounds) {
    const parts = r.split(' ');
    for (let i = 1 ; i < parts.length; i+=2) {
      const color = parts[i+1].replace(/,/g, '');;
      const cnt = Number(parts[i])
      minCnt[color] = Math.max(minCnt[color], cnt);
    }
  }
  return minCnt['red'] * minCnt['blue'] * minCnt['green'];
}

function solve(input) {
  let sum =0 ;
  for (let i= 0 ; i < input.length; i++) {
    sum += checkPossible(input[i]);
  }
  return sum;
}
