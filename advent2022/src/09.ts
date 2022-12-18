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
  const seen = new Set();
  seen.add(`${0}~${0}`);
  const rope = [
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0]];
  let lastRope = [...rope];
  for (const row of input) {
    const [D, L_] = row.split(' ');
    const L = Number(L_);
    const dir = {'R' : [1,0],'L': [-1,0], 'U': [0,1], 'D': [0,-1]}[D];
    for (let i=0 ; i < L; i++) {
      rope[0] = [rope[0][0] + dir[0], rope[0][1] + dir[1]];
      for (let j = 1 ; j < rope.length; j++) {
        const T = rope[j];
        const H = rope[j-1];
        const lastH = lastRope[j-1];
        let xd = Math.abs(T[0] - H[0]);
        let yd = Math.abs(T[1] - H[1]);
        let dist = xd + yd;
        if (dist > 2) {
          rope[j] = [rope[j][0] + (H[0] > T[0] ? 1 : -1), rope[j][1] + (H[1] > T[1] ? 1 : -1)];
        } else if (xd >= 2) {
          rope[j] = [lastH[0], rope[j][1]];;
        } else if (yd >=2) {
          rope[j] = [rope[j][0],lastH[1]];
        }
      }
      seen.add(`${rope[9][0]}~${rope[9][1]}`);
      lastRope = [...rope];
      for (let X = 10; X >= -10 ; X--) {
        let s = '';
        for (let Y = 10; Y >= -10; Y--) {
          let c = '.';
          for (let R = 0; R < 10; R++) {
            if (rope[R][0] == X && rope[R][1] == Y) {
              c = `${R}`;
            }
          }
          s += c;
        }
      }
    }
  }
  return seen.size;;
}
