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

function processCharacter(curVal, c) {
  curVal += c.charCodeAt(0);
  curVal *= 17;
  curVal %= 256;
  return curVal;
}

function getStringHash(s) {
  let hash = 0;
  for( let i= 0; i < s.length; i++) {
    hash = processCharacter(hash, s.charAt(i));
  }
  return hash;

}
function solve(input) {
  const tokens = input[0].split(',')
  let boxes : any= new Array(256).fill(false).map((a) => []);
  for (const token of tokens) {
    let numberValue, hasDash = false, label;
    let numMatch = token.match(/\d+/);
    if (numMatch) {
      numberValue = parseInt(numMatch[0], 10);
    }
    let dashMatch = token.match(/-/);
    if (dashMatch) {
      hasDash = true;
    }
    let labelMatch = token.match(/\w+/);
    if (labelMatch) {
      label = labelMatch[0];
    }
    const boxHash = getStringHash(label);
    const box = boxes[boxHash];
    if (hasDash) {
      boxes[boxHash] = box.filter(([l, focal]) => l != label);
    } else {
      let foundIdx = -1;
      for (let i= 0; i < box.length; i++) {
        if (box[i][0] == label) {
          foundIdx = i;
          break;
        }
      }
      if (foundIdx != -1) {
        box[foundIdx] = [label, numberValue];
      } else {
        box.push([label, numberValue]);
      }
    }
    console.log(`${token} parses into ${numberValue}, ${hasDash} ${label}`);
  }
  let ret = 0;
  for (let i = 0; i < boxes.length; i++) {
    for (let j =0; j < boxes[i].length; j++) {
//console.log('eh', boxes[i][1]);
      const val = (i+1) * (j+1) * boxes[i][j][1]
      ret += val;
      console.log(i, val, boxes[i]);
    }
  }
  return ret;
}
