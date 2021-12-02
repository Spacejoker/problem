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
  const [x0, y0, y1] = c(input);
  console.log('Problem 1:', x0*y0, 'Problem 2:', x0*y1);
});

// End boilerplate.

let c=(R,x=0,y=0,Y=0,d='',D='')=>(R.map(r=>([d,D]=r.split(' '),d[5]?(x+=+D,Y+=+D*y):y+=+D*(d[3]?1:-1))),[x,y,Y])
