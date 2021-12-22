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

function parseNums(input) {
  return input.split(',').map(Number);
}

//// End boilerplate.
function toNum(input) {
  return function(x, y) {
    return Number(input[y].charAt(x));
  }
}

const dict = {};
function go([s0, s1], turn, [p0, p1]) :[number, number] {
  const hash = `${s0}~${s1}~${turn}~${p0}~${p1}`;
  if (dict[hash]) return dict[hash];
  if (s0 >= 21) {
    return [1,0]
  } 
  if (s1 >= 21) {
    return [0,1];
  }

 const nums = {'3': 1,
      '4': 3,
      '5': 6,
      '6': 7,
      '7': 6,
      '8': 3,
      '9': 1 };

  const ret :[number, number] = [0,0];
  let a=0,b=0, news0 = s0, news1 = s1;
  for (let dice = 3; dice <= 9; dice ++ ){
    const n = nums[`${dice}`];
    let newp0 = p0, newp1 = p1;
    if (turn == 0) {
      newp0 = p0 + dice;
      newp0 %= 10;
      news0 = s0 + newp0 + 1;
    } else {
      newp1 = p1 + dice;
      newp1 %= 10;
      news1 = s1 + newp1 + 1;
    }

    [a,b] = go([news0, news1], (turn+1)%2, [newp0, newp1]);
    ret[0] += n * a;
    ret[1] += n * b;
  }

  //dict[hash] = [a,b];
  // put in hash
  return ret; 
}

function solve(input) {
  const nums = {}

  for (let a= 1; a <= 3; a++) {
    for (let b = 1; b <=3 ; b++) {
      for (let c = 1; c <= 3; c++) {
        const sum = a + b + c;
        if (nums[sum]) nums[sum]++;
        else {nums[sum] = 1;}
      }
    }
  }
  console.log(nums);
  //const ans = go([0,0], 0, [3,7])
  const ans = go([0,0], 0, [2,9])
  console.log(ans);
//  let pos = [2,9];
//  let score = [0,0];
//  let turn = 0;
//  let rolls = 0;
//  let dice = 1;
//  while(true) {
//    let player = turn%2;
//    for(let i= 0 ; i < 3 ; i++ ){
//      pos[player] += dice;
//      pos[player] %= 10;
//      dice ++;
//      rolls ++;
//      if (dice > 100) dice = 1;
//    }
//    score[player] += pos[player]+1;
//    console.log('player', player, 'score', score[player]);
//    if(Math.max(score[0], score[1]) >= 1000) {
//      console.log('apa', score, rolls, dice);
//      return Math.min(score[0],score[1]) * (rolls);
//    }
//
//    turn ++;
//  }

  return 1;
}
//function solve(input) {
//  let pos = [2,9];
//  let score = [0,0];
//  let turn = 0;
//  let rolls = 0;
//  let dice = 1;
//  while(true) {
//    let player = turn%2;
//    for(let i= 0 ; i < 3 ; i++ ){
//      pos[player] += dice;
//      pos[player] %= 10;
//      dice ++;
//      rolls ++;
//      if (dice > 100) dice = 1;
//    }
//    score[player] += pos[player]+1;
//    console.log('player', player, 'score', score[player]);
//    if(Math.max(score[0], score[1]) >= 1000) {
//      console.log('apa', score, rolls, dice);
//      return Math.min(score[0],score[1]) * (rolls);
//    }
//
//    turn ++;
//  }
//
//  return 1;
//}
