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
  console.log(solveA(input));
  console.log(solveB(input));
});

// End boilerplate.

function solveA(input) {
  const row = input[0];
  for (let i= 13 ; ; i++) {
    const d = new Set();
    for (let j = i-13; j<= i; j++) {
      d.add(row.charAt(j));
    }
    if (d.size == 14) {
      return i+1;
    }
  }
}

function solveB(input) {
  return 1;
}
