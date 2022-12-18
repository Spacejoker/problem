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
  console.log(solve(input));
});

// End boilerplate.

const free = 70000000 - 42586708;
const needed = 30000000;
const minreq = needed - free;

let best = Infinity;
let ans= 0;
function go(node) {
  let tot = 0;
  for (const [key, value] of Object.entries(node.children)) {
    tot += go(value);
  }
  for (const [key, value] of Object.entries(node.files)) {
    tot += value as number;
  }
  if (tot >= minreq && tot < best) {
    best = tot;
  }
  if (tot <= 100000) {
    ans += tot;
  }
  return tot;
}

function solve(input) {
  let node = {children: {}, files:{}};
  let root = node;
  let parents: any[] = [];
  for (let i= 1; i < input.length; i++) {
    const row = input[i];
    const split = row.split(' ');
    if (split[1] == 'cd') {
      const path = split[2];
      if (path == '..') {
        node = parents.pop();
      } else {
        let next = {children: [], files:{}};
        if (node.children[path]) {
          next = node.children[path];
        } else {
          node.children[path] = next;
        }
        parents.push(node);
        node = next;
      }
    } else if (split[1] == 'ls') {
      while(i+1 < input.length && input[i+1].split(' ')[0] != '$') {
        i++;
        const ls = input[i].split(' ');
        if (ls[0] == 'dir') {
          continue;
        } else {
          node.files[ls[1]] = Number(ls[0]);
        }
      }
    }
  }

  let sum = go(root);

  console.log('best', best);
  console.log('root sum', sum);
  return ans;
}

