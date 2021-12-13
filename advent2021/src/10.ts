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

function parseNums(input) {
  return input.split(',').map(Number);
}

//// End boilerplate.
function toNum(input) {
  return function(x, y) {
    return Number(input[y].charAt(x));
  }
}
function solve(input) {
  let scores:number[] = [];
  for (const line of input) {
    if (scoreLine(line) == 0) {
      console.log('adding');
      scores.push(scoreLine2(line));
    }
  }
  scores.sort((a,b) => a-b);
  console.log(scores);
  //const grid = new Array(h).fill(false).map(() =>new Array(w).fill(false));
  return scores[scores.length/2|0];
}

function scoreLine(line) {
  const stack :string[]= [];
  for (let i=0; i < line.length; i++) {
    let c = line.charAt(i);
    if (c == '(' || c == '[' || c == '{' || c == '<') {
      //console.log('pushing', c);
      stack.push(c);
    } else {
      let pop = stack.pop();
      const pairs = ['()','[]','{}','<>'];
      const scores = {')': 3,']': 57, '}':  1197, '>': 25137};//'()','[]','{}','<>'];
      for (let i =0 ; i < 4; i++) {
        if (pop != pairs[i].charAt(0)) continue;
        if (c != pairs[i].charAt(1)){
          console.log('found mismatch', c, pop, scores[c]);
          return scores[c];
        }
      }
    }
  }
  return 0;
}
function scoreLine2(line) {
  const stack :string[]= [];
  for (let i=0; i < line.length; i++) {
    let c = line.charAt(i);
    if (c == '(' || c == '[' || c == '{' || c == '<') {
      stack.push(c);
    } else {
      let pop = stack.pop();
      const pairs = ['()','[]','{}','<>'];
      const scores = {')': 3,']': 57, '}':  1197, '>': 25137};//'()','[]','{}','<>'];
      for (let i =0 ; i < 4; i++) {
        if (pop != pairs[i].charAt(0)) continue;
        if (c != pairs[i].charAt(1)){
          console.log('found mismatch', c, pop, scores[c]);
          return 0;
        }
      }
    }
  }
  let score = 0;
  while(stack.length > 0 ){
    score *= 5;
    const c : string= stack.pop()!;
    const scores = {'(': 1,'[': 2, '{':  3, '<': 4};
    score += scores[c];
  }
  console.log('pts', score);
  return score;
}
