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

function solve(input) {
  const graph: any = {};
  let sum = 0;
  console.log('hi');
  const steps = input[0];
  const startPos : any = [];
  for (let i= 2; i < input.length; i++) {
    const line = input[i];
    const t = line.match(/\w{3}/g);
    graph[t[0]] = [t[1], t[2]];
    if (t[0].charAt(2) == 'A') startPos.push(t[0]);
  }
  console.log(startPos);
  let lcms: any = [];
  for (let c = 0; c <startPos.length; c++) {
    let current = startPos[c];
    let i;
    for(i= 0; ; i++) {
      current = steps.charAt(i%steps.length) === 'L' ? graph[current][0] : graph[current][1];
      if (current.charAt(2) == 'Z') break;
    }
    lcms.push(i+1);
  }

  return lcmOfList(lcms);
}

function gcd(a: number, b: number): number {
  // Euclidean algorithm to find GCD
  return b === 0 ? a : gcd(b, a % b);
}

function lcm(a: number, b: number): number {
  // Calculate LCM using the GCD
  return Math.abs(a * b) / gcd(a, b);
}

function lcmOfList(numbers: number[]): number {
  if (numbers.length < 2) {
    throw new Error("At least two numbers are required to find LCM.");
  }

  let result = numbers[0];
  for (let i = 1; i < numbers.length; i++) {
    result = lcm(result, numbers[i]);
  }

  return result;
}
