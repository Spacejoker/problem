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
function solve(input : string[]) {
  const monkey = {};
  const names :any = [];
  for (const row of input) {
    const name = row.split(':')[0];
    const f = row.split(' ');
    if (f.length == 2) {
      monkey[name] = {val: Number(f[1])};
    } else {
      monkey[name] = {a: f[1], b: f[3], op: f[2]};
      names.push(name);
    }
  }
  const L = ev(monkey['root'].a, monkey);
  const R = ev(monkey['root'].b, monkey);
  console.log( L, '=', R);

//  while(true) {
//    for (let i =0; i < names.length; i++) {
//      if (monkey[names[i]].val) {
//        continue;
//      }
//      const {a, b, op} = monkey[names[i]];
//      const aval = monkey[a].val;
//      const bval = monkey[b].val;
//      if (aval && bval) {
//        let res;
//        switch(op) {
//          case '+':
//            res = aval + bval;
//            break;
//          case '*':
//            res = aval * bval;
//            break;
//          case '-':
//            res = aval - bval;
//            break;
//          case '/':
//            res = aval / bval;
//            break;
//        }
//        monkey[names[i]].val = res;
//      }
//    }
//    if (monkey['root'].val) return monkey['root'].val;
//  }
}

function ev(name, monkey) {
  if (name == 'humn') return 'X';
  const m = monkey[name];
  if (m.val) return m.val;
  else {
const l = ev(m.a, monkey)
    const r = ev(m.b, monkey);
let ret = '(' + l + m.op + r + ')'
    console.log(l);
    if ((typeof l == 'number' || !l.includes('X'))  && (typeof r == 'number' || !r.includes('X'))) {
      ret = eval(ret);
    }
    return ret;
  }

  
}
