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
function checkPos(x: number, y: number, input) {
  if (x < 0 || x >= input[0].length || y < 0 || y >= input.length) return false;
  let c = input[y].charAt(x);
  return c == '*';
  // return '.0123456789'.indexOf(c) == -1;
}
function findNumbers(s: string) {
  return s.split(' ').map(Number).filter(n => n > 0);
}

function solve(input) {
  let sum =0 ;
  let cardCnt = input.map(e => 1);
  for (let i= 0 ; i < input.length; i++) {
    const line = input[i];
    const [left, right] = line.split(':')[1].split('|');
    const winners = findNumbers(left);
    const mine = findNumbers(right);
    let matches = 0;
    for (let i = 0; i < mine.length; i++) {
      for (let j =0 ; j < winners.length; j++) {
        if (winners[j] == mine[i]) {
          matches ++;
        }
      }
    }
    for (let k = 0; k < matches && k < input.length; k++) {
      cardCnt[k+i+1] += cardCnt[i];
    }
    sum += cardCnt[i];
  }
  return sum;
}
