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

interface Cube {
  x0 : number;
  x1 : number;
  y0 : number;
  y1 : number;
  z0 : number;
  z1 : number;
}

function solve(input) {
  let cubes :Cube[] = [];
  for (const row of input) {
    const params = row.split(',');
    const [[x0, x1], yr, zr] = params.map((p) => p.split('=')[1].split('..').map(Number));
    let on = row.charAt(1) == 'n';
    let newCube :Cube= {x0, x1: x1+1, y0: yr[0], y1: yr[1]+1, z0: zr[0], z1: zr[1]+1};
    cubes = cubes.map(c=>splitCube(c,newCube)).flat();
    if (on) {
      cubes.push(newCube);
    }
  }

  let sum = 0
  for (const c of cubes) {
    sum += (c.x1-c.x0)*(c.y1-c.y0)*(c.z1-c.z0);
  }
  return sum;
}
// a  fully contains b
function contains(a: Cube, b: Cube): boolean {
	return a.x0 <= b.x0 && a.x1 >= b.x1 && a.y0 <= b.y0 && a.y1 >= b.y1 && a.z0 <= b.z0 && a.z1 >= b.z1;
}

function splitCube(a: Cube, b: Cube): Cube[] {
  if (!overlap(a, b)) {
    return [a];
  }
  // 
  if (contains(b, a)) {
    return [];
  }
  let xs=[a.x0, ...[b.x0, b.x1].filter(x=> x > a.x0 && x < a.x1), a.x1];
  let ys=[a.y0, ...[b.y0, b.y1].filter(y=>y > a.y0 && y < a.y1), a.y1];
  let zs=[a.z0, ...[b.z0, b.z1].filter(z=>z > a.z0&&z < a.z1), a.z1];
  let splitCube: Cube[] = [];

  for (let x=0; x < xs.length-1; x++) {
    for (let y =0 ; y < ys.length -1; y++) {
      for (let z = 0; z < zs.length-1; z++) {
        const newCube = { x0: xs[x], y0: ys[y], z0: zs[z], x1: xs[x+1], y1: ys[y+1], z1: zs[z+1]};
        splitCube.push(newCube);
      }
    }
  }
  const ret :any= [];
  for (const c of splitCube) {
    if (!contains(b,c)) {
      ret.push(c);
    }
  }

  return ret;
}

function overlap(a: Cube, b: Cube): boolean {
  return a.x0 <= b.x1 && a.x1 >= b.x0 && a.y0 <= b.y1 && a.y1 >= b.y0 && a.z0 <= b.z1 && a.z1 >= b.z0;
}

//function solve(input) {
//  const dim = 120;
//  const grid = new Array(dim).fill(false).map(() =>new Array(dim).fill(false).map(() => new Array(dim).fill(false)));
//  for (const row of input) {
//    const params = row.split(',');
//    const [xr, yr, zr] = params.map((p) => p.split('=')[1].split('..').map(Number));
//    
//    let val = row.charAt(1) == 'n';
//
//    for (let x = Math.max(0,xr[0]+50); x <= Math.min(101, xr[1]+50); x++) {
//      for (let y = Math.max(0,yr[0]+50); y <= Math.min(101, yr[1]+50); y++) {
//        for (let z = Math.max(0,zr[0]+50); z <= Math.min(101, zr[1]+50); z++) {
//          grid[x][y][z] = val;
//        }
//      }
//    }
//    let cnt =0 ;
//    for (let x =0 ; x < dim; x++) {
//      for (let y =0 ; y < dim; y++) {
//        for (let z =0 ; z < dim; z++) {
//          if (grid[y][x][z]) cnt ++;
//        }
//      }
//    }
//    console.log('on', cnt);
//  }
//  console.log(grid[0][0][1]);
//
//  return 0;
//}
