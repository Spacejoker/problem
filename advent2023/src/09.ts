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

function getNext(nums: number[]): number{
  const series = [nums];
  let last = nums;
  while(true) {
    let allZeroes = true;
    let next: any = [];
    for (let i =0; i < last.length -1; i++) {
      next[i] = last[i+1] - last[i];
      if (next[i] != 0) {
        allZeroes = false;
      }
    }
    series.push(next);
    last = next;
    if (allZeroes) break;
  }
  console.log(series);
  let lastValue = 0;
  for (let i= series.length -2 ; i >= 0; i--) {
    const s = series[i];
    
    let nextValue = s[0] - lastValue;
    lastValue = nextValue;
    console.log(lastValue);
  }
  //console.log(lastValue);
  return lastValue;
}

function solve(input) {
  let sum = 0;
  for (let i =0 ; i < input.length; i++ ){
    sum += getNext(input[i].split(' ').map(Number));
  }
  return sum;
}
