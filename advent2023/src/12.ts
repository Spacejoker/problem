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

function countRunsOfOnes(array) {
  let runs:any = [];
  let currentRun = 0;

  for (let i = 0; i < array.length; i++) {
    if (array[i] == 2) {
      return runs;
    }
    if (array[i] === 1) {
      currentRun++;
    } else if (currentRun > 0) {
      runs.push(currentRun);
      currentRun = 0;
    }
  }
  if (currentRun > 0) {
    runs.push(currentRun);
  }
  return runs;
}

function check(vals: string, info, needsLength = true) {
  const t = countRunsOfOnes(vals);
  if (needsLength && t.length !== info.length) {
    return false;
  }
  for (let i = 0; i < t.length - (needsLength ? 0 : 1); i++) {
    if (t[i] !== info[i]) {
      return false;
    }
  }
  return true;
}

let hash : any= new Array(100).fill([]); // .map(() => {});//new Array(100).fill([]));
//let hash : any = new Array(100).fill(false).map(() =>new Array(100).fill(undefined));

function countPlace(str, runLengths, idx, positions) {
  if (idx > str.length) { return 0; }
  if (idx == str.length) {
    if (positions.length != runLengths.length) return 0;
    return 1;
  }

  const hashKey = `${idx}~${positions.length}`;
  if (hash[hashKey] !== undefined) return hash[hashKey];

  let ans = 0;

  // Try to place a segment at idx
  if (positions.length < runLengths.length) {
    const runLen = runLengths[positions.length];
    let possible = true;
    // Not enough strip left
    if (runLen + idx > str.length) possible = false;

    // Cannot place here.
    for (let i =0; i < runLen; i++) {
      if (str[idx + i] === 0) possible = false;
    }

    // No space after
    if (idx + runLen < str.length - 1 && str[idx + runLen] == 1) possible = false;

    // No space before:
    if(idx > 0 && str[idx-1] == 1) possible = false;

    if (possible) {
      ans += countPlace(str,
        runLengths, 
        idx+runLen + (positions.length == runLengths.length - 1 ? 0 : 1),
        [...positions, idx]);
    }
  }

  if (str[idx] != 1) {
    // Just step forward
    ans += countPlace(str, 
      runLengths, 
      idx+1,
      positions);
  }
  hash[hashKey] = ans;

  return ans;
}

function ways(row) {
  const v : any = [];
  let [key, info] = row.split(' ');
  key = key+ '?'+key+ '?'+key+ '?'+key+ '?'+key;
  info = info + ',' +info + ',' +info + ',' +info + ',' +info;
  let cnt = 0;
  for (let i =0 ; i< key.length; i++) {
    v.push({'?': 2, '#' : 1, '.': 0}[key.charAt(i)]);
    if (key.charAt(i) == '?') cnt++;
  }

  const c = info.split(',').map(Number);
  return countPlace(v, c, 0, []);
}

function solve(input) {
  let sum = 0;
  let i= 0; 
  for (const row of input) {
    const start = process.hrtime();

    console.log(`Start solve ${i++}`);

    hash = {};
    const w = ways(row)
    const end = process.hrtime(start);
    const timeInMilliseconds = end[0] * 1000 + end[1] / 1e6;

    console.log(`~~~~~~~~~~~~~~~ PROCESSED ROW ${i} ~~~~~~~~~~~~~~ Ans: ${w} Time: ${Math.round(timeInMilliseconds)}`);
    sum += w;
  }
  return sum;
}
