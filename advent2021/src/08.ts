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

// End boilerplate.

function shuffle(a) {
    var j, x, i;
    for (i = a.length - 1; i > 0; i--) {
        j = Math.floor(Math.random() * (i + 1));
        x = a[i];
        a[i] = a[j];
        a[j] = x;
    }
    return a;
}

function alphabet(pts) {
  const abc = 'abcdefg';
  let ret = '';
  for (const c of pts) {
    ret += abc.charAt(c);
  }
  return (s) => {
    let full ='';
    for(let i = 0 ; i < s.length ; i++) {
      const pos = s.charCodeAt(i) - 97;
      full += ret.charAt(pos);
    }
    return powStr(full);
  };
}

const permutator = (inputArr) => {
  let result :any= [];

  const permute = (arr, m = []) => {
    if (arr.length === 0) {
      result.push(m)
    } else {
      for (let i = 0; i < arr.length; i++) {
        let curr = arr.slice();
        let next = curr.splice(i, 1);
        permute(curr.slice(), m.concat(next))
     }
   }
 }

 permute(inputArr)

 return result;
}

function powStr(s) {
  let ans = 0;
  for (let i =0 ; i < s.length; i++) {
      ans += Math.pow(2, s.charCodeAt(i)-97+1);
  }
  return ans;
}

function solve(input) {
  // const nums = parseNums(input[0]);
    let total = 0;
  for (const row of input) {
    const split = row.split(' | ');
    const codes = split[0].split(' ');
    const output = split[1].split(' ');

    //  a
    //b   c
    //  d 
    //e   f
    //  g

    const map = [0,1,2,3,4,5,6];
    const allPerms = permutator(map);
    const codeVals = codes.map(powStr);


    for (const p of allPerms) {
      const c = alphabet(p);
      const nums = [c('abcefg'),
          c('cf'),
          c('acdeg'),
          c('acdfg'),
          c('bcdf'),
          c('abdfg'),
          c('abdefg'),
          c('acf'),
          c('abcdefg'),
          c('abcdfg')];

      /*
 aaaa    ....    aaaa    aaaa    ....
b    c  .    c  .    c  .    c  b    c
b    c  .    c  .    c  .    c  b    c
 ....    ....    dddd    dddd    dddd
e    f  .    f  e    .  .    f  .    f
e    f  .    f  e    .  .    f  .    f
 gggg    ....    gggg    gggg    ....

  5:      6:      7:      8:      9:
 aaaa    aaaa    aaaa    aaaa    aaaa
b    .  b    .  .    c  b    c  b    c
b    .  b    .  .    c  b    c  b    c
 dddd    dddd    ....    dddd    dddd
.    f  e    f  .    f  e    f  .    f
.    f  e    f  .    f  e    f  .    f
 gggg    gggg    ....    gggg    gggg
 */

      let correct = false;
      let success = true;
      let matches = 0;
      for (const c of codeVals) {
        let found = false;
        for (const n of nums) {
          if (n == c){
            found = true;
            matches ++;
          }
        }
        if (!found){
          success = false;
        }
      }

      if (success) {
        //console.log('yo', nums, codes, codes.map(powStr));
        let ans = 0;
        for (let i =0; i < output.length; i++ ) {
          ans *= 10;
          const val = powStr(output[i]);
          for (let j =0; j < 10; j++) {
            if (val == nums[j]) {
              ans += j;
              break;
            }
          }
        }
        console.log(ans);
        total += ans;
        if (ans == 4870) console.log(nums, codes, codes.map(powStr), );
        break;
      }
    }

  }
  console.log('powstr', powStr('e'), powStr('cd'));
  
  return total;
}

// wrong: 1121709
// wrong!!: 1014748
