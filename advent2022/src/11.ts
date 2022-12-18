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
const test = false;
const star = 2;

const monkeys = test ? 

  [
  {
    items: [79, 98],
    div: 23,
    to: [2,3],
  },
  {
    items: [54,65,75,74],
    div: 19,
    to: [2,0],
  },
  {
    items: [79, 60,97],
    div: 13,
    to: [1,3],
  },
  {
    items: [74],
    div: 17,
    to: [0,1],
  }]

  : [
  {
    items: [99, 63, 76, 93, 54, 73],
      //Operation: new = old * 11
      div: 2,
      to: [7,1],
  },
  {
    items:[ 91, 60, 97, 54],
    //Operation: new = old + 1
    div: 17,
    to: [3, 2],

  },{
    items:[ 65],
    //Operation: new = old + 7
    div: 7,
    to: [6, 5],

  },{
    items:[ 84, 55],
    //Operation: new = old + 3
    div: 11,
    to: [2, 6],

  },{
    items:[ 86, 63, 79, 54, 83],
    //Operation: new = old * old
    div: 19,
    to: [7, 0],

  },{
    items:[ 96, 67, 56, 95, 64, 69, 96],
    //Operation: new = old + 4
    div: 5,
    to: [4, 0],

  },{
    items:[ 66, 94, 70, 93, 72, 67, 88, 51],
    //Operation: new = old * 5
    div: 13,
    to: [4, 5],
  },

  {
    items:[ 59, 59, 74],
    //Operation: new = old + 8
    div: 3,
    to: [1, 3],
  }];


function solve(input) {
  let cnt = [0,0,0,0,0,0,0,0,0];
  let mod = 1;
  for (const monkey of monkeys){
    mod *= monkey.div;
  }
  for ( let r = 0; r < (star == 2 ? 10000 : 20); r++) {
    for (let m =0 ; m < monkeys.length; m++) {
      const monkey = monkeys[m];
      while(monkey.items.length > 0) {
        cnt[m] ++;
        let next = monkey.items.shift() ?? 0;
        if (!test) {
          switch (m) {
            case 0:
              next *= 11;
              break;
            case 1:
              next += 1;
              break;
            case 2:
              next += 7;
              break;
            case 3:
              next += 3;
              break;
            case 4:
              next *= next;
              break;
            case 5:
              next += 4;
              break;
            case 6:
              next *= 5;
              break;
            case 7:
              next += 8;
              break;
          } 
        } else {
          switch(m) {
            case 0:
              next *= 19;
              break;
            case 1:
              next += 6;
              break;
            case 2:
              next *= next;
              break;
            case 3:
              next += 3;
              break;
          }
        }
        if (star == 2) {
          next %= mod;
        } else {
          next /=3;// monkey.div;
          next = Math.floor(next);
        }

        const toMonkey = next % monkey.div == 0 ? monkey.to[0] : monkey.to[1];

        monkeys[toMonkey].items.push(next);
      }
    }
  }
  cnt.sort((a,b) => a - b).reverse();
  return cnt[0] * cnt[1];
}

