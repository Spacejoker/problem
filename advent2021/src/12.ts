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

function go(pos, nodes, curPath, visited, double) {
  //console.log('curPath', curPath.length, curPath, visited);
  if (pos == 'end') {
    console.log(curPath);
    return 1;
  }
  let ans = 0;
  const destinations = nodes[pos];
  for (const d of destinations) {
    const singleVisit = d.toLowerCase() == d;
    if (d == 'start')continue;
    if (singleVisit && visited.indexOf(d) >= 0){
      if (!double) {
        //console.log('dd', double, d);
        ans += go(d, nodes, [...curPath, pos], [...visited, d], d);
      }
      continue;
    }
    ans += go(d, nodes, [...curPath, pos], [...visited, d], double);
  }
  return ans;
}

function solve(input) {
  const nodes : any = {};
  for (const row of input) {
    let a = '', b = '';
    [a, b] = row.split('-');
    if (!nodes[a]) nodes[a] = [b];
    else nodes[a].push(b);
    if (!nodes[b]) nodes[b] = [a];
    else nodes[b].push(a);
  }
  return go('start', nodes, [], ['start'], '');
}

