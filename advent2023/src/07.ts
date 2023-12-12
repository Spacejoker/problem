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


function getType(hand) {
  const cnt = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
  for (let i= 0; i < hand.length; i++) {
    cnt[hand[i]] ++;
  }
  let maxval =0;
  let maxidx = 0;
  for (let i= 2; i < cnt.length; i++) {
    if (cnt[i] > maxval) {
      maxidx = i;
      maxval = cnt[i];
    }
  }
  cnt[maxidx] += cnt[1];
  cnt[1] = 0;

  for (let i = 0; i < cnt.length; i++) {
    if (cnt[i] == 5) return 10;
    if (cnt[i] == 4) return 9;
  }
  for (let i = 0; i < cnt.length; i++) {
    for (let j =0 ; j < cnt.length; j++) {
      if (i!=j && cnt[i] == 3 && cnt[j] == 2) return 8;
    }
  }
  for (let i = 0; i < cnt.length; i++) {
    if (cnt[i] == 3) return 7;
  }
  for (let i = 0; i < cnt.length; i++) {
    for (let j =i+1 ; j < cnt.length; j++) {
      if (cnt[i] == 2 && cnt[j] == 2) return 6;
    }
  }
  for (let i = 0; i < cnt.length; i++) {
    if (cnt[i] == 2) return 5;
  }
  return 4;
}

function solve(input) {
  let sum = 0;
  let hands : any= [];
  for (let i= 0; i < input.length; i++) {
    const line = input[i];
    const [raw, bid] = line.split(' ');
    let vals : any = [];
    for (let k =0 ; k < 5 ; k++) {
      vals.push(raw.charAt(k))
    }
    const parsed = vals.map((n) => {
      const num = Number(n);
      if (!Number.isNaN(num)) {
        return num;
      } else return {'T': 10, 'J': 1, 'Q': 12, 'K': 13, 'A':14}[n];
    });
    console.log(parsed);
    hands.push({values: parsed, bid});
    console.log(getType(parsed));
  }
  hands.sort((a, b) => {
    const atype = getType(a.values);
    const btype = getType(b.values);
    if (atype != btype) {
      return btype - atype;
    }
    for(let i= 0; i < a.values.length; i++) {
      if (a.values[i] != b.values[i]) {
        return b.values[i] - a.values[i];
      }
    }
    return 0;
  });
  let winnings = 0;
  for ( let i= 0; i < hands.length; i++) {
    const rank = hands.length - i;
    const hand = hands[i];
    winnings += hand.bid * rank;
  }
  console.log(hands);
  return winnings;
}
