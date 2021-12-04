import * as readline from 'readline';

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
  solveA(input);
});

// End boilerplate.

function solveA(input) {
  const winning = input[0].split(',').map(Number);
  let record = 10000, badRecord =0, ares=0,bres=0;
  out:while(true) {
    for (let i =2; i+= 6;) {
      if (!input[i])break out;
      let board : any[]= [];
      for (let j=0; j < 5; j++) {
        const r = input[j+i];
        const t = r.trim().split(/\s+/);
        board.push(t.map(Number));
      }

      let [steps, s] = score(winning, board);
      if (steps < record) {
        record = steps;
        ares = s;
      }
      if (steps > badRecord) {
        badRecord = steps;
        bres = s;
      }
    }
  }
  console.log('Ans: ', ares, bres);
}
function score(winning, board) : [number, number]{
  const check:any[] = [];
  let steps = 0, points = 0;
  for(let j =0; j < 5; j++) {
    check.push([false, false, false, false, false]);
  }
  for (let i= 0; i < winning.length; i++) {
    for(let y=0; y<5; y++) {
      for (let x =0 ; x < 5; x++) {
        if (winning[i] == board[y][x]) {
          check[y][x] = true;
        }
      }
    }
    for(let y=0; y<5; y++) {
      let allrow = true;
      let allcol = true;
      for (let x =0 ; x < 5; x++) {
        if (!check[y][x]) {
          allrow = false;
        }
        if (!check[x][y]) {
          allcol = false;
        }
      }
      if(allrow || allcol) {
        let sum = 0;
        for (let X =0; X < 5; X ++) {
          for (let Y =0; Y < 5; Y++) {
            if (!check[Y][X])sum += board[Y][X];
          }
        }
        return [i, winning[i] * sum];
      }
    }
  }
  return [100000, 0];
}

function solveB(input) {
  return 1;
}
