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

function rowsToGrid(input: string[]) {
  return input.map((row) => {
    let ret : string[] = [];
    for (let i =0; i < row.length; i++) {
      ret.push(row.charAt(i));
    }
    return ret;
  });
}

// x~y~direction
let seen = new Set<string>();
// up left down right
const direction = [[-1, 0], [0, -1], [1, 0], [0, 1]];
let grid : any;

function solve(input) {
  let ret = 0;
  grid = rowsToGrid(input);
  for (let i=0 ; i < grid.length; i++) {
    ret = Math.max(ret, countEnergySquares(0,i,3));
    ret = Math.max(ret, countEnergySquares(grid[i].length-1,i,1));
  }
  for (let i=0 ; i < grid[0].length; i++) {
    ret = Math.max(ret, countEnergySquares(i,0,2));
    ret = Math.max(ret, countEnergySquares(i, grid.length-1,0));
  }
  return ret;

  //
  //for (let i=0 ; i
}

function countEnergySquares(x0, y0, dir){
  seen.clear();
  traceBeam(x0, y0, dir);
  const setPos = new Set();
  const posList = [...seen].map(s => {
    const [x, y, z] = s.split('~');
    return `${x}~${y}`;
  });
  for (let i=0 ; i < posList.length; i++) {
    setPos.add(posList[i]);
  }
  for (let i= 0; i < grid.length; i++) {
    let s = '';
    for (let j =0 ; j < grid[i].length; j++ ) {
      let c = '.'
      if (setPos.has(`${j}~${i}`)) c = '#';
      s += c;
    }
  }
  return setPos.size;
}

function traceBeam(x, y, dir) {
  // oob
  if (x < 0 || y < 0 || x >= grid[0].length || y >= grid.length) return;
  const seenKey = `${x}~${y}~${dir}`;
  // old news
  if (seen.has(seenKey)) return;
  seen.add(seenKey);
  const cell = grid[y][x]
  let defaultMap = {0: [0], 1: [1], 2: [2],  3: [3]};
  let map = {};
  if (cell == '.') {
    map = defaultMap;
  } else if( cell == '/') {
    map = {0: [3], 1: [2], 2:[1], 3:[0]};
  } else if( cell == '\\') {
    map = {0: [1], 1: [0], 2:[3], 3:[2]};
    //map = {0: [2], 2: [0], 1:[3], 3:[1]};
  } else if( cell == '-') {
    map = {0: [1, 3], 1: [1], 2:[1, 3], 3:[3]};
  } else if( cell == '|') {
    map = {0: [0], 1: [0, 2], 2:[2], 3:[0, 2]};
  }

  // something like this
  const newDirIdxList = map[dir];
  for(const dirIdx of newDirIdxList) {
    traceBeam(x + direction[dirIdx][1], y + direction[dirIdx][0], dirIdx);
  }
}
