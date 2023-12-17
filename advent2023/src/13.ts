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

function cmp(a, b) {
  return a == b;
}

function findGridSymmetryRow(grid, oldVal) {
  for (let i= 0; i < grid.length -1 ; i++) {
    let same = true;
    for (let j = 0; j <= i; j++) {
      const opp = (i -j) + i + 1;
      if (opp < grid.length) {
        if (!cmp(grid[j], grid[opp])){
          same = false;
          break;
        }
      }
    }
    if (same && oldVal != i+1) return (i+1);
  }
  return 0;

}

function findGridSymmetryValue(grid, oldVal = 0) {
  let ans = findGridSymmetryRow(grid, oldVal/100);
  if (ans > 0) return ans * 100;
  const t : any= [];
  for (let c =0 ; c < grid[0].length; c++) {
    let s = '';
    for (let i =0 ; i < grid.length; i++) {
      s += grid[i].charAt(c);
    }
    t.push(s)
  }
  return findGridSymmetryRow(t, oldVal);
}
function updateCharacter(str: string, index: number, newChar: string): string {
    if (index < 0 || index >= str.length || newChar.length !== 1) {
        return str;
    }
    return str.substring(0, index) + newChar + str.substring(index + 1);
}

function updateGrid(grid, y, x) {
  const c = grid[y].charAt(x);
  grid[y] = grid[y].substring(0, x) + (c == '#' ? '.' : '#') + grid[y].substring(x + 1);
  return grid;
}

function solve(input) {
  let sum = 0;
  let grid : any= [];
  for ( let i= 0; i < input.length; i++) {
    const row = input[i];
    if (row.length == 0) {
      const allVals = new Set<number>();
      const origGrid = [...grid];
      const oldVal  = findGridSymmetryValue(grid);
      allVals.add(oldVal);
      out:for (let y= 0; y < grid.length; y++) {
        for (let x =0 ; x < grid[0].length; x++) {
          let r = 0;
          const c = grid[y].charAt(x);
          grid[y] = grid[y].substring(0, x) + (c == '#' ? '.' : '#') + grid[y].substring(x + 1);
          const tmp = [...grid];
          r = findGridSymmetryValue(grid, oldVal);
          grid[y] = grid[y].substring(0, x) + c + grid[y].substring(x + 1);
          if (r > 0) {
            allVals.add(r);
          }
        }
      }
      allVals.delete(oldVal);
      allVals.forEach((e:number) => {sum += e});
      grid = [];
    } else {
      grid.push(row);
    }
  }
  return sum;
}
