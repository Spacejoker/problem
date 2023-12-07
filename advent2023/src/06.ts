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
  const times = input[0].split(':')[1].split(' ').map(Number).filter(e => e != 0)
  const records = input[1].split(':')[1].split(' ').map(Number).filter(e => e != 0)
  let sum =1 ;
  for (let i = 0 ; i < times.length ; i++) {
    let cnt = 0;
    for (let hold = 0; hold < times[i]; hold++) {
      let speed = hold;
      let dist = (times[i] - hold)*speed;
      if (dist > records[i]) cnt ++;
    }
    sum *= cnt;
  }
  return sum;
}
