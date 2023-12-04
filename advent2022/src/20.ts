import * as readline from 'readline';
require('source-map-support').install();

// File reading.
// const grid = new Array(h).fill(false).map(() =>new Array(w).fill(false));
// const grid = new Array(h).fill(false).map(() =>new Array(w).fill(false).map(() => new Array(100).fill(false)));

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
function solve(input : string[]) {
  let nums = input.map((e, idx) => ({idx, val: Number(e)*811589153}));
  for (let cnt =0 ; cnt < 10; cnt ++){
    console.log('count', cnt);
    for (let i= 0; i < nums.length; i++) {
      // find idx:
      let curIdx;
      for (let k =0 ; k < nums.length; k++) {
        if (nums[k].idx == i) {
          curIdx = k;
          break;
        }
      }
      let num = nums[curIdx];
      const rem = nums.slice(0, curIdx).concat(nums.slice(curIdx+1));
      let newpos = curIdx + num.val;
      newpos %= rem.length;
      while(newpos < 0) newpos += rem.length;
      newpos %= rem.length;
      rem.splice(newpos, 0, num);
      nums = rem;
    }
  }
  let start;
  for (let i= 0; i < nums.length; i++) {
    if (nums[i].val ==0) {
      start = i;
    }
  }
  let ans = 0;
  for (let i =0; i <= 3000; i++) {
    if (i % 1000 == 0) {
      ans += nums[(start+i)%nums.length].val;
    }
  }

  return ans;
}
