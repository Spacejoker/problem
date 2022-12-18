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

function solve(input) {

  const stacks = [['N','V','C','S'],
['S','N','H','J','M','Z'],
['D','N', 'J','G','T','C','M'],
  ['M','R','W','J','F','D','T'],
['H','F','P'],
['J','H','Z','T','C'],
['Z','L','S','F','Q','R','P', 'D'],
['W','P','F','D','H','L','S', 'C'],
['Z','G','N','F','P','M', 'S', 'D']];

//const stacks = [['N','Z'],['D','C','M'],['P']];

  for (const row of input) {
    console.log('raow', row, stacks);
    if (!row.length) break;
    const [a, n, b, src, c, dest] = row.split(' ');
    const tmp : any= [];
      const sn = Number(src)-1;
      const dn = Number(dest)-1;
    for(let i =0 ; i < Number(n); i++) {
      console.log(n, sn, dn);
      const val = stacks[sn].shift()!;
      tmp.unshift(val);
    }
    for(let i =0 ; i < Number(n); i++) {
      const val = tmp.shift();
      stacks[dn].unshift(val);
    }
  }
    for (let i= 0; i < stacks.length; i++) {
      console.log(stacks[i].shift());
    }
  return 0;
}
