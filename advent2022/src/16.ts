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
  console.log('exec', t1 - t0);
  console.log('ans', ans);
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
const cache = {};
//Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
function go(nodes, current, timeLeft, partial, allowed) {
  let distance;
  if (cache[current]) {
    distance = cache[current];
  } else {
    // Find distance to all -> move to one and open.
    const stack = [[current, 0]];
    const seen = new Set();
    seen.add(current);
    distance = {[current]: 0};
    while(stack.length > 0) {
      const [next, t] : any= stack.shift();
      for (const e of nodes[next].edges) {
        if (!seen.has(e)) {
          stack.push([e, t+1]);
          seen.add(e);
          distance[e] = t+1;
        }
      }
    }
    cache[current] = distance;
  }
  // default - do nothing:
  let rate = 0;
  for(const [key, node] of Object.entries(nodes) as any) {
    if (node.open) rate += node.rate;
  }
  let ans = timeLeft * rate + partial;

  for (const key of allowed) {
    const cand = nodes[key];
    if (allowed.has(key) && !cand.open && cand.rate > 0 && distance[key]  + 1 <= timeLeft) {
      let c = (distance[key] +1)* rate + partial;
      cand.open = true;
      const ret = go(nodes, key, timeLeft - distance[key] - 1, c, allowed);
      cand.open = false;
      ans = Math.max(ret, ans);
    }
  }
  return ans;
}

let baseTime = Date.now();
function solve(input: string[]) {
  const nodes = {};
  let cnt = 0;
  let A:any, B:any;
  const C:any = [];
  for (const row of input) {
    const split = row.split(' ');
    const name = split[1];
    const rate = Number(split[4].split('=')[1].split(';')[0]);
    let edges;
    
    if (row.indexOf('valves') > 0) {
      edges = row.split('valves ')[1].split(', ');
    } else {
      edges = [split[split.length-1]];
    }
    nodes[name] = {rate, edges, open: false};
    if (rate > 0) {
      cnt ++;
      C.push(name);
    }
  }
  let ans = 0;
  for (let i =0; i <= 1 << cnt; i++) {
    A = new Set();
    B = new Set();
    for(let j = 0; j < cnt; j++) {
      if ((i & (1 << j)) > 0) {
        A.add(C[j])
      } else {
        B.add(C[j]);
      }
    }
    const aAns = go(nodes, 'AA', 26, 0, A);
    const bAns = go(nodes, 'AA', 26, 0, B);
    ans = Math.max(ans, aAns + bAns);
    if (i % 100 == 0){
      console.log('done', i / 320, '%, current candidate: ', ans, `Spent ${Date.now() - baseTime} ms.`);
    }
  }
  return ans;
}
