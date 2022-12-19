import * as readline from 'readline';
require('source-map-support').install();

// File reading.
// const grid = new Array(h).fill(false).map(() =>new Array(w).fill(false));
// const grid = new Array(h).fill(false).map(() =>new Array(w).fill(false).map(() => new Array(100).fill(false)));

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

let seen;
function solve(input : string[]) {
  let ans = 1;
  let turns = 32;
  for (let i= 0; i < input.length; i++) {
    seen = {};
    maxGeo = {}, maxObs = {}, maxClay = {}, maxOre = {};
    best = 0;
    const s = input[i].split(' ');
    const costs = [[s[6],0,0].map(Number),[s[12],0,0].map(Number), [s[18], s[21], 0].map(Number), [s[27],0, s[30]].map(Number)];
    const maxcost = [0,0,0,0];
    for (let x =0; x < costs.length; x++) {
      for (let j =0 ; j < 3; j++) {
        maxcost[j] = Math.max(maxcost[j],costs[x][j]);
      }
    }
    const g = go(costs, [1,0,0,0, 0], turns, [0,0,0, 0], maxcost)
    console.log('case', i+1, 'score', g);
    ans *= g;//(i+1) * g;
  }
  return ans;
}

let mint = 24;
let best = 0;
let maxGeo, maxObs, maxClay, maxOre;

function go(costs, machines, t, res, maxcosts) {
  let ans = 0;
  if (t ==0 ) {
    const ret = res[3];
    if (ret > best) {
      best = Math.max(best, ret);
      console.log('record', best);
    }
    return ret;
  }
  let buy = false;
  const nextRes = [res[0] + machines[0], res[1] + machines[1], res[2]+machines[2], res[3]+machines[3]];
  for (let i=costs.length -1 ; i >= 0; i--) {
    if (machines[i] >= maxcosts[i] && i < 3) continue;
    const [orecost, claycost, obscost] = costs[i];
    if (res[0] >= orecost && res[1] >= claycost && res[2] >= obscost) {
      machines[i]++;
      nextRes[0] -= orecost;
      nextRes[1] -= claycost;
      nextRes[2] -= obscost;
      go(costs, machines, t-1, nextRes, maxcosts);
      machines[i]--;
      nextRes[0] += orecost;
      nextRes[1] += claycost;
      nextRes[2] += obscost;
      if (i == 3) {
        buy = true;
        break;
      }
    }
  }
  if (res[0] <=4) {
    go(costs, machines,t-1, nextRes, maxcosts);
  }
  return best;
}

// 1961
// 2258
