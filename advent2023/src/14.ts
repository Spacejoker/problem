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

function cmp(a, b) {
  return a == b;
}

function findPeriodLength(sequence) {
    const length = sequence.length;
    for (let period = 1; period <= length / 2; period++) {
        let isPeriodic = true;
        for (let i = 0; i < period; i++) {
            for (let j = i + period; j < length; j += period) {
                if (sequence[i] !== sequence[j]) {
                    isPeriodic = false;
                    break;
                }
            }
            if (!isPeriodic) break;
        }
        if (isPeriodic) return period;
    }
    return length; // No repeating pattern found
}

// Parabolic Reflector Dish
function solve(input) {
  let grid = rowsToGrid(input);
  const direction = [[-1, 0], [0, -1], [1, 0], [0, 1]];
  const nums : any= [];
  const initSteps = 1000;
  for (let cyk = 0; cyk < initSteps; cyk ++) {
    for (const [dy, dx] of direction) {
      while(true) {
        let didUpdate = false;
        for (let i = 0 ; i < grid.length; i++) {
          for(let j =0; j < grid[i].length; j++) {
            let newx = j + dx;
            let newy = i + dy;
            if(newx >=0  && newx < grid[i].length && newy >= 0 && newy < grid.length && grid[newy][newx] == '.' && grid[i][j] == 'O') {
              grid[newy][newx] = 'O';
              grid[i][j] = '.';
              didUpdate = true;
            }
          }
        }
        if (!didUpdate) break;
      }
    }
    if (cyk > initSteps - 500) {
      let sum = 0;
      for (let i =0 ; i < grid.length; i++) {
        const pts = grid.length - i;
        for(let j =0; j < grid[i].length; j++) {
          if (grid[i][j] == 'O') sum += pts;
        }
      }
      console.log(sum);
      nums.push(sum);
    }
  }
  const period = findPeriodLength(nums)
  console.log('period', period);
  nums.slice(-period);
  const modSteps = (1000000000 - initSteps) % period;
  //console.log('ans?', nums[modSteps], nums, modSteps);
  for (let cyk = 0; cyk < modSteps; cyk ++) {
    for (const [dy, dx] of direction) {
      while(true) {
        let didUpdate = false;
        for (let i = 0 ; i < grid.length; i++) {
          for(let j =0; j < grid[i].length; j++) {
            let newx = j + dx;
            let newy = i + dy;
            if(newx >=0  && newx < grid[i].length && newy >= 0 && newy < grid.length && grid[newy][newx] == '.' && grid[i][j] == 'O') {
              grid[newy][newx] = 'O';
              grid[i][j] = '.';
              didUpdate = true;
            }
          }
        }
        if (!didUpdate) break;
      }
    }
  }

  let sum = 0;
  for (let i =0 ; i < grid.length; i++) {
    const pts = grid.length - i;
    for(let j =0; j < grid[i].length; j++) {
      if (grid[i][j] == 'O') sum += pts;
    }
  }
  //console.log(grid);
  
  return sum;
}
