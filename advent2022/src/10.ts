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
function solve(input) {
  let ans = 0, X = 1, pc =-1;
  let s = '';
  let length = {'noop': 1, 'addx': 2};
  for (const line of input) {
    const ops = line.split(' ');
    for (let c = length[ops[0]] ; c > 0; c--) {
      pc ++;
      if ((pc + 20)%40 ==0) {
        ans += X* pc;
      }
      let scanX = pc % 40;
      if (X - 1== scanX || X == scanX || X+1 == scanX) {
        s += '#';
      } else {
        s += '.';
      }
      if (s.length == 40) {
        console.log(s); 
        s = '';
      }
      if (c == 1) {
        switch(ops[0]){ 
          case 'noop':
            break;
          case 'addx':
            X += Number(ops[1]);
            break;
        }
      }
    }
  }
  return ans;
}
