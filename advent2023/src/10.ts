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

function solve(input) {
  let sum = 0;
  let x0 =0, y0 =0 ;
  for (let i= 0; i < input.length; i++) {
    for (let j= 0; j < input[0].length; j++) {
      if (input[i].charAt(j) == 'S') {
        x0 = j;
        y0 = i;
      }
    }
  }

  let visited = new Set();
  const getKey = (x, y) => `${x}~${y}`;
  visited.add(getKey(x0, y0));
  let nextX = x0 + 1;
  let nextY = y0 ;
  let steps = 0;
  visited.add(getKey(nextX, nextY));
  const up = [-1, 0];
  const down = [1, 0];
  const left = [0, -1];
  const right = [0, 1];
  while(nextX != x0 || nextY != y0) {
    // y, x
    let nextPos : any;
    //console.log('next cell', input[nextY].charAt(nextX), nextX, nextY);
    switch(input[nextY].charAt(nextX)) {
      case '|':
        nextPos = [up, down];
        break;
      case '-':
        nextPos = [left, right];
        break;
      case 'L':
        nextPos = [up, right];
        break;
      case '7':
        nextPos = [left, down];
        break;
      case 'J':
        nextPos = [up, left];
        break;
      case 'F':
        nextPos = [down, right];
        break;
    }
    const a = [nextX + nextPos[0][1], nextY + nextPos[0][0]];
    const aKey = getKey(a[0], a[1]);
    const b = [nextX + nextPos[1][1], nextY + nextPos[1][0]];
    const bKey = getKey(b[0], b[1]);
    //console.log(b, bKey, a, aKey);
    if (!visited.has(aKey)) {
      nextX = a[0];
      nextY = a[1];
      visited.add(aKey);
    } else if (!visited.has(bKey)){
      nextX = b[0];
      nextY = b[1];
      visited.add(bKey);
    } else {
      break;
    }
    steps += 1;
  }

  function updateCharacter(str, index, newChar) {
    if (index > str.length - 1) return str; // Check if the index is valid
    return str.substring(0, index) + newChar + str.substring(index + 1);
  }
  
  let area = 97;

  let output = [];
  const grid = new Array(input.length).fill(false).map(() =>new Array(input[0].length).fill('.'));
  
  console.log(visited);
  let inside =0 ;
  for (let starty = 0 ; starty < input.length; starty++) {
    let numWalls = 0;
    let opener = '';
    for (let xt =0 ; xt < input[0].length; xt++) {
      const key = getKey(xt, starty);
      const c = input[starty].charAt(xt);
      //console.log(xt, starty, c, numWalls, c == '|', visited.has(key));
      if(visited.has(key)) {
        if (c == '|') {
          numWalls ++;
        } else if (c == 'F' || c == 'L') {
          opener = c;
        } else if (c == '7') {
          if (opener == 'L') {
            numWalls++;
          }
          opener = '';
        } else if (c == 'J') {
          if (opener == 'F') {
            numWalls++;
          }
          opener == '';
        }
      } else if (numWalls % 2 == 1) {
          inside ++;
      }
    }
  }

  return inside;
}
