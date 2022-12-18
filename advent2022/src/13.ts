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

//// End boilerplate.
function solve(input) {

  let idx =1 ;
  let ans = 0;
  let values: any = [];
  for (let i= 0; i < input.length ; i+= 1) {
    if (input[i].length > 0) {
      values.push(JSON.parse(input[i]));
    }
  }
  for (let k = 0; k < values.length*2; k++) {
    for (let i = 0 ; i < values.length-1; i++) {
      let a = values[i];
      let b = values[i+1];
      let c = cmp(a,b);
      if (c > 0) {
        values[i+1] =a;
        values[i] = b;
      }
    }
  }
  for (let i =0 ; i < values.length; i++) {
    if (values[i].length == 1 && values[i][0] == 2) { console.log(2, i+1)}
    if (values[i].length == 1 && values[i][0] == 6) { console.log(6, i+1)}
  }
  return ans;
}

function cmp(a, b) {
  let ret = 0;
  for (let i = 0; i < Math.max(a.length, b.length); i++) {
    if (a.length <= i) return -1;
    if (b.length <= i) return 1;
    let aE = a[i], bE = b[i];
    const aArray = Array.isArray(aE), bArray = Array.isArray(bE);
    if (aArray || bArray) {
      ret = cmp(aArray ? aE : [aE], bArray ? bE : [bE]);
    } else {
      ret = aE - bE;
    }
    if (ret != 0) return ret;
  }
  return 0;
}
