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

const pieces = [
['####'],

['.#.',
'###',
'.#.'],

['..#',
'..#',
'###'],

['#',
'#',
'#',
'#'],

['##',
 '##']];

function check(piece, x, y, cave) {
  for (let i = 0; i < piece.length; i++) {
    for (let j =0 ; j < piece[0].length; j++) {
      let newx = x + j;
      let newy = i + y;
      if (piece[i].charAt(j) == '#' && cave[newy].charAt(newx) == '#') return false;
    }
  }
  return true;
}

function solve(input) {
  const commands = input[0];
  let nextPiece = 0, nextCommand = 0;
  let x =3, y = 0;
  let cave = ['#.......#','#.......#','#.......#','#.......#','#########'];
  // 0 == #, 8 == #: #.......#

  const seen : any = []
  while(nextPiece < 84000) {
    const c = commands[nextCommand % commands.length];
    const p = pieces[nextPiece%5];
    // Move to side
    let newx = x + (c == '<' ? -1 : 1);
    if (check(p, newx, y, cave)) {
      x = newx;
    }
    let newy = y+1;
    if (check(p, x, newy, cave)) {
      y = newy;
    } else {
      for (let i =0; i < p.length; i++) {
        for (let j =0; j < p[0].length; j++) {
          if (p[i].charAt(j) == '#') {
            let z = y+i;
            cave[z] = cave[z].substring(0, x+j) + '#' + cave[z].substring(x+j+1);
          }
        }
      }
      nextPiece ++;
      const p2 = pieces[nextPiece %5];
      let clear;
      for (let i= 0; i < cave.length; i++) {
        if (cave[i] == '#.......#') {
          clear = i+1;
        } else {
          break;
        }
      }
      const target = p2.length + 3;

      while(clear < target) {
        cave.unshift('#.......#');
        clear ++;
      }
      while(clear > target) {
        cave.shift();
        clear --;
      }
      x = 3;
      y = 0;

      if(nextPiece % 5 ==0) {
        let dist = '';
        for (let x =0 ; x < 9; x++) {
          for (let i=0; ; i++) {
            if (cave[i].charAt(x) == '#') {
              dist += `${i}~`;
              break;
            } 
          }
        }
          const cc = nextCommand % commands.length;
        for (let [pattern, len, cycle, cpos] of seen) {
          if (pattern == dist && cpos == cc) {
            console.log('cycle:', nextPiece - cycle, 'building', cave.length - len, 'current', nextPiece);
          }
        }
        seen.push([dist, cave.length, nextPiece, cc]);
      }
      // create new piece;
    }

    // Move down


    nextCommand ++;
    //if (stopped) => update field, create new piece, move field down until new piece fits.
    //;
  }
  console.log(seen);
  while(cave[0] == '#.......#') {
    cave.shift();
  }
  return cave.length -1 + 581395300 * 2738
}
