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
  console.log(solve2(input));
});

function parseNums(input) {
  return input.split(',').map(Number);
}

//// End boilerplate.
function toNum(input) {
  return function(x, y) {
    return Number(input[y].charAt(x));
  }
}

//function solve(input) {
//  const rules = {};
//  let polymer = '';
//  for (let i= 0; i < input.length; i++) {
//    if (i ==0) polymer = input[i];
//    if (i >1) {
//      const a = input[i].split(' -> ');
//      rules[a[0]] = a[1];
//    }
//  }
//  for (let iter = 0; iter < 10; iter ++) {
//    let add = '';
//    for (let c = 0; c < polymer.length-1; c++) {
//      const val = polymer.charAt(c) + polymer.charAt(c+1);
//      add += rules[val];
//    }
//    let next = '';
//    for (let i =0; i < add.length; i++) {
//      next += polymer.charAt(i) + add.charAt(i);
//    }
//    next += polymer.charAt(polymer.length-1);
//    polymer = next;
//  }
//  const charCnt = new Array(26).fill(0);
//  for (let c = 0; c < polymer.length; c++) {
//    charCnt[parseInt(polymer.charAt(c),36)-10]++;
//  }
//  let min = 999999;
//  let max = 0;
//  for (let i =0 ; i < 26; i++) {
//    if (charCnt[i] ==0)continue;
//    min = Math.min(min, charCnt[i]);
//    max = Math.max(max, charCnt[i]);
//  }
//  
//  return max - min;
//}


function solve2(input) {
  let polymer = '';
  for (let i= 0; i < input.length; i++) {
    if (i ==0) polymer = input[i];
    if (i >1) {
      const a = input[i].split(' -> ');
      rules[a[0]] = a[1];
    }
  }
  const charCnt = new Array(26).fill(0);
  for (let i =0 ; i < polymer.length -1 ; i++) {
    const t = rec(polymer.substring(i, i+2), 39);
    for (let j =0 ; j < 26; j++) {
      charCnt[j] += t[j];
    }
  }
  for (let c = 0; c < polymer.length; c++) {
    charCnt[parseInt(polymer.charAt(c),36)-10]++;
  }
  console.log(JSON.stringify(charCnt));

//  for (let iter = 0; iter < 10; iter ++) {
//    let add = '';
//    for (let c = 0; c < polymer.length-1; c++) {
//      const val = polymer.charAt(c) + polymer.charAt(c+1);
//      add += rules[val];
//    }
//    let next = '';
//    for (let i =0; i < add.length; i++) {
//      next += polymer.charAt(i) + add.charAt(i);
//    }
//    next += polymer.charAt(polymer.length-1);
//    polymer = next;
//  }
//  for (let c = 0; c < polymer.length; c++) {
//    charCnt[parseInt(polymer.charAt(c),36)-10]++;
//  }
  let min = 999999999999999;
  let max = 0;
  for (let i =0 ; i < 26; i++) {
    if (charCnt[i] ==0)continue;
    min = Math.min(min, charCnt[i]);
    max = Math.max(max, charCnt[i]);
  }
  
  return max - min;
}
const rules = {};
const memo :any= {};
function rec(polymer, depth) {
  const key = `${polymer}-${depth}`;
  if (memo[key]) return [...memo[key]];

  const charCnt = new Array(26).fill(0);
  const newChar = rules[polymer];
  charCnt[parseInt(newChar.charAt(0),36)-10] ++;

  if (depth > 0) {
    const a = rec(polymer.charAt(0) + newChar, depth-1);
    const b = rec(newChar + polymer.charAt(1), depth-1);
    for (let i =0 ; i < 26; i++) {
      charCnt[i] += a[i];
      charCnt[i] += b[i];
    }
  }

  memo[key] = charCnt;
  return charCnt;
}
