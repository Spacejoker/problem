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

// End boilerplate.

function getDigits(inputString : string) {
  ////const digitRegex = /\d/g;
  //// const digitRegex = /\d|(?=(one|two|three|four|five|six|seven|eight|nine|zero))/gi;
  //  const digitRegex = /\d|(?=(one|two|three|four|five|six|seven|eight|nine|zero))/gi;


  const idx = 'zero|one|two|three|four|five|six|seven|eight|nine'.split('|');
  //const matches = s.match(digitRegex);

  //if (matches) {
  //   return matches.map(match => {
  //     console.log('match', match);
  //    if (/\d/.test(match)) {
  //      return parseInt(match, 10);
  //    } else {
  //      return idx.indexOf(match.toLowerCase());
  //    }
  //  });
  //} else {
  //  return [];
  //}

  const digitAndStringRegex = /\d|one|two|three|four|five|six|seven|eight|nine|zero/gi;
  let matches: RegExpExecArray | null;
  const result: (number)[] = [];

  while ((matches = digitAndStringRegex.exec(inputString)) !== null) {
    const [match] = matches;

    if (/\d/.test(match)) {
      result.push(parseInt(match, 10));
    } else {
      result.push(idx.indexOf(match.toLowerCase()));
    }

    if (match.length === 0) {
      // Avoid infinite loop for empty matches
      break;
    }

    // Move the index to the end of the current match to handle overlapping
    digitAndStringRegex.lastIndex -= match.length - 1;
  }
  return result;
}

function solve(input) {
  let sum =0 ;
  for (const line of input) {
    const digits = getDigits(line)
    sum += 10*digits[0] + digits[digits.length-1];
    console.log(digits);
  }

  return sum;
  // return 0;
}
