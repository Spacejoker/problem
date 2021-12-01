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

readFile().then((r) => {
  const input = r.map(Number);
  console.log('A:', c(input));
  console.log('B:', c(s(input)));
});

// End boilerplate.

let c=(r,l=NaN,n=0)=>(r.forEach(N=>(l<N&&n++,l=N)),n),s=n=>n.map((r,i)=>r+n[i+1]+n[i+2])
