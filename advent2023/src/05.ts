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

function checkSeed(seed, maps, mapidx) {
  if (mapidx == maps.length) return seed;

  let offset = 0;
  for (const [destStart, srcStart, length] of maps[mapidx]) {
    if (seed >= srcStart && seed < srcStart + length) {
      offset = destStart - srcStart;
    }
  }

  return checkSeed(seed+offset, maps, mapidx + 1);
}


function solve(input) {
  let sum =0 ;
  let seeds = input[0].split(':')[1].split(' ').map(Number).filter(e => e != 0);
  const maps : any= [];
  let curMap : any= [];
  for (let i= 3; i < input.length; i++) {
    const line = input[i];
    if (line.length == 0) {maps.push(curMap); curMap = []; i += 1; continue;}
    curMap.push(line.split(' ').map(Number));
  }
  maps.push(curMap);
  let res = Infinity;
  const progress = 0.01;
  for (let s = 0 ; s < seeds.length; s+= 2) {
    for(let i = 0; i < seeds[s+1]; i++) {
      let seedId = seeds[s] + i
      const val = checkSeed(seedId, maps, 0)
      if (val < res) {
        res = val;
      }
      if (checkSeed(seedId + 1000, maps, 0) === val + 1000) i+= 1000;
    }
  }
  //  A:
//  for(const seedId of seeds) {
//    res = Math.min(checkSeed(seedId, maps, 0), res);
//  }
  return res;
}
