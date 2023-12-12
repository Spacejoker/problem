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
  let sum = 0;
  const cols : any = new Set(), rows : any = new Set();
  const planets : any= [];
  for (let i=0 ; i < input.length; i++) {
    let hasThing = false;
    for (let j =0 ; j<input[i].length; j++) {
      if (input[i].charAt(j) == '#') {
        hasThing = true;
        planets.push([j, i]);
      }
    }
    if (!hasThing) {
      rows.add(i);
    }
  }
  for (let j =0 ; j<input[0].length; j++) {
    let hasThing = false;
    for (let i=0 ; i < input.length; i++) {
      if (input[i].charAt(j) == '#')
        hasThing = true;
    }
    if (!hasThing) {
      cols.add(j);
    }
  }


  for (let a = 0; a < planets.length; a++) {
    for (let b = a+1; b < planets.length; b++) {
      const ap = planets[a];
      const bp = planets[b];
      let dist =0 ;
      for(let x = Math.min(ap[0], bp[0]); x < Math.max(ap[0], bp[0]); x++) {
        const price = cols.has(x) ? 1000000 : 1;
        dist += price;
      }
      for(let y = Math.min(ap[1], bp[1]); y < Math.max(ap[1], bp[1]); y++) {
        const price = rows.has(y) ? 1000000 : 1;
        dist += price;
      }
      sum += dist;
      console.log(ap, bp, dist);
    }
  }


  console.log(cols, rows);
  return sum;
}
