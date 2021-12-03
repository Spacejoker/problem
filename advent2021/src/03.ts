import * as readline from 'readline';

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

//A: 1451208
//B: 1620141160

readFile().then((input) => {
  console.log('Problem 1:', solveA(input), 'Problem 2:', solveB(input));
});

// End boilerplate.
// First star

let solveA=(I,e='',a='',p:any=0,c= new Array(12).fill(0),B=false,i=0)=>{for(;i<12*I.length;i++)if(I[(i/12)|0].charAt(i%12)=='1')c[i%12]++;for (i=0;i<12;i++)(e+=(B=c[i]>I.length/2)?'1':'0',a+=B?'0':'1');return (p=parseInt)(e,2)*p(a,2)}

// Trash code begins
function solveB(input) {
  let len = 12;
  const A =  [...input];
  let ansa = 0;
  for(let pos = 0; pos < len; pos++) {
    let ones = 0;
    for (let i= 0; i < A.length; i++) {
      if (A[i].charAt(pos) == '1') {
        ones ++;
      }
    }
    const target = ones >= A.length/2 ? '1' : '0';
    for (let i= 0; i < A.length; i++) {
      if (A[i].charAt(pos) != target) {
        A.splice(i, 1);
        i--;
      }
    }
    if (A.length == 1) {
      break;
    }

  }

  let mul = 1;
  for (let i = len-1; i>=0; i--) {
    if (A[0].charAt(i) == '1') {
      ansa += mul;
    }
    mul *= 2;
  }

  const B =  [...input];
  let ansb = 0;
  for(let pos = 0; pos < len; pos++) {
    let ones = 0;
    for (let i= 0; i < B.length; i++) {
      if (B[i].charAt(pos) == '1') {
        ones ++;
      }
    }
    let target = '0';
    if (ones < B.length/2) target = '1';
    for (let i= 0; i < B.length; i++) {
      if (B[i].charAt(pos) != target) {
        B.splice(i, 1);
        i--;
      }
    }
    if (B.length == 1) {
      break;
    }
  }
  mul = 1;
  for (let i = len-1; i>=0; i--) {
    if (B[0].charAt(i) == '1') {
      ansb += mul;
    }
    mul *= 2;
  }

  return ansb * ansa;

}

// Problem 1: 775304 Problem 2: 1370737
