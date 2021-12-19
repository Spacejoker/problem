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
//check all perms, all signs. 24 = 8 * 3!
// 12 equal = the 12 same

interface Scan {
  coords : [number, number, number][];
  offset: [number, number, number];
}

function solve(input) {
  const scans :Scan[] = [];
  let coords: [number, number, number][] = [];
  for(let i= 1; i < input.length ;i++) {
    const row = input[i];
    if (row.includes('scanner')) {
      continue;
    }
    if (row.length == 0) {
      scans.push({coords, offset: [0,0,0]});
      coords = [];
      continue;
    }
    coords.push(row.split(',').map(Number));
  }
  let included = new Array(scans.length).fill(false);
  included[0] = true;

  out: for (let from = 0; from < scans.length; from ++) {
    for (let to = 0; to < scans.length; to ++) {
      if (from == to) continue;
      let valid = included[from] && !included[to];
      if (!valid) continue;
      const primary = scans[from].coords;
      const tmp = scans[to].coords;
      const allgen = makeGens(tmp);
      // TODO test all 24 combos
      for (const secondary of allgen) {
        for (let fst =0; fst < primary.length; fst ++) {
          for (let snd = 0; snd < secondary.length; snd ++) {
            let xdelta = secondary[snd][0] - primary[fst][0];
            let ydelta = secondary[snd][1] - primary[fst][1];
            let zdelta = secondary[snd][2] - primary[fst][2];
            let matching = 0;
            for (let check = 0; check < primary.length; check++) {
              for (let checkSnd = 0; checkSnd < secondary.length; checkSnd++) {
                const xdiff = secondary[checkSnd][0] - primary[check][0];
                const ydiff = secondary[checkSnd][1] - primary[check][1];
                const zdiff = secondary[checkSnd][2] - primary[check][2];
                if (xdiff == xdelta && ydiff == ydelta && zdiff == zdelta) {
                  matching ++;
                }
              }
            }
            if (matching >= 12) {
              console.log('added', to);
              scans[to] = {coords: secondary, offset: [xdelta+scans[from].offset[0], ydelta+scans[from].offset[1], zdelta+scans[from].offset[2]]};
              included[to] = true;
              from = 0;
              continue out;
              // TODO: start over
            }
          }
        }
      }
    }
  }

  const strs = new Set();
  for (let i =0; i < scans.length; i++) { // s of scans) {
    const s = scans[i];
    for (const c of s.coords) {
      strs.add(`${c[0]-s.offset[0]}~${c[1]-s.offset[1]}~${c[2]-s.offset[2]}`);
    }
  }

  let maxdist = 0;
  for(let i= 0; i < scans.length; i++) {
  for(let j= 0; j < scans.length; j++) {
    const a = scans[i].offset;
    const b = scans[j].offset;

    const dist = Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1]) + Math.abs(a[2] - b[2]);
    maxdist = Math.max(maxdist, dist);
  }
  }


  // TODO: check unique points.

  console.log(scans[0]);
  console.log(included);
  console.log('distance: ', maxdist);


  return strs.size;
}

function makeGens(cs : [number, number, number][]) :[number, number, number][][] {
  const allSigns = [
    cs.map(([a,b,c]) => [a,b,c]),
    cs.map(([a,b,c]) => [a,b,-c]),
    cs.map(([a,b,c]) => [a,-b,c]),
    cs.map(([a,b,c]) => [a,-b,-c]),
    cs.map(([a,b,c]) => [-a,b,c]),
    cs.map(([a,b,c]) => [-a,b,-c]),
    cs.map(([a,b,c]) => [-a,-b,c]),
    cs.map(([a,b,c]) => [-a,-b,-c])];

  const ret : [number, number, number][][] = [];
  for (const s of allSigns) {
    ret.push(s.map(([a,b,c]) => [a,b,c]));
    ret.push(s.map(([a,b,c]) => [a,c,b]));
    ret.push(s.map(([a,b,c]) => [b,a,c]));
    ret.push(s.map(([a,b,c]) => [b,c,a]));
    ret.push(s.map(([a,b,c]) => [c,a,b]));
    ret.push(s.map(([a,b,c]) => [c,b,a]));
  }
  return ret;
}
