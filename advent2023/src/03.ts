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

function solve(input) {
  const found = {};
  let sum =0 ;
  // identify all numbers, and their starting coordinates
  // search the surrounding rectangle for a symbol
  for (let i= 0 ; i < input.length; i++) {
    let numStr = '';
    for (let j =0 ; j < input[i].length ;j++ ) {
      let line = input[i];
      const j0 = j;
      let ans = '';
      while (Number.isInteger(Number(line.charAt(j))) && j < line.length) {
        ans += line.charAt(j);
        j++;
      }
      let foundSymbol = false;
      if (ans.length > 0) {
        for (let xd = -1; xd <= ans.length; xd++) {
          for (let y0 = -1; y0 <= 1; y0++) {
            const xTest = j0+xd, yTest = i+y0;
            if (checkPos(xTest, yTest, input)) {
              foundSymbol = true;
              const key = `${xTest}~${yTest}`;
              if (!found[key]) found[key] = [];
              found[key].push(Number(ans))
            }
          }
        }
      }
    }
  }
  for (const v of Object.values(found)) {
    if ((v as any).length ==2) {
      sum += v[0] * v[1];
    }
  }
  console.log(found);
  return sum;
}
