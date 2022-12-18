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
  const t0 = Date.now();
  const ans = solve(input)
  const t1 = Date.now();
  console.log('exec:', (t1-t0)/1000, 's.');
  console.log('ans:', ans);
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
// too low: 4712648

function solve(input) {
  const reg = /-?\d+/g;
  const sensors : any= [];
  for (const row of input) {
    let coords :any= [];
    let m;
    while ((m = reg.exec(row)) != null) {
      coords.push(m[0]);
    }
    sensors.push(coords.map(Number));
  }
  const M = 4000000;
  for (let y0 = 0; y0 < M; y0++) {
    const segs = getRow(sensors, y0);
    if (segs.length > 1) {
      const x = segs[0][1]+1
      const y = y0;
      return x*M + y0;
    }
  }
  return 0;
}

function getRow(sensors: any, y) {
  let segments: any = [];
  let beacons = new Set();
  for (let [sx, sy, bx, by] of sensors) {
    const md = Math.abs(sx - bx) + Math.abs(sy - by);
    const yd = Math.abs(y - sy);
    const remain = md - yd;
    if (remain >= 0) {
      const x0 = sx - remain;
      const x1 = sx + remain;
      segments.push([x0, x1]);
      if (by == y) {
        beacons.add(`${bx}~${by}`);
      }
    }
  }
  segments.sort((a,b) => a[0] - b[0]);
  let merged : any= [];
  for ( let i= 0; i < segments.length -1;i++) {
    const seg = segments[i], next = segments[i+1];
    if (next[0] <= seg[1]) {
      seg[1] = Math.max(seg[1], next[1]);
      segments = [...segments].splice(0, i+1).concat( [...segments].splice(i+2));
      i--;
    }
  }
  let ans = 0;
  for (const s of segments) {
    ans += s[1] - s[0]+1;
  }
  return segments; //ans - beacons.size;
}
