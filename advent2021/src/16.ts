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

let versionSum =0 ;
function solve([hex]: string[]) {
  let binary = '';
  for (let c = 0; c < hex.length; c++) {
    let value = parseInt(hex.charAt(c), 16).toString(2);
    while(value.length % 4 != 0) value = '0' + value;
    binary += value;
  }
  const result = parsePacket(binary.substring(0));
  return result[1];
}

// Return [parseIndex, value]
function parsePacket(buffer:string) : [number, number] {
  const operands: number[] = [];
  let i = 0;
  const version = parseInt(buffer.substring(i, i+3), 2);
  const type = parseInt(buffer.substring(i+3, i+6), 2);

  // For star 1, TODO remove.
  versionSum += version;

  if (type == 4) {
    let packetLen = 6;
    i += 6;
    let value = 0;
    let lastGroup = false;
    while(!lastGroup) {
      const chunk = buffer.substring(i);
      if (chunk.charAt(0) == '0'){
        lastGroup = true;
      }
      value *= 16;
      value += parseInt(buffer.substring(i+1, i+5), 2);
      i += 5;
      packetLen += 5;
    }
    while(packetLen % 4 != 0) packetLen ++;
    operands.push(value);
  } else {
    const packetHeader = buffer.charAt(i+6);
    const startIdx = i+7;
    if (packetHeader == '0') {
      const valueLength = 15;
      const subPacketLength = parseInt(buffer.substring(startIdx, startIdx + valueLength), 2);
      i += 22;
      const endIdx = i + subPacketLength;
      while(i < endIdx) {
        let parseIdx=0,value=0;
        [parseIdx, value] = parsePacket(buffer.substring(i));
        i+= parseIdx;
        operands.push(value);
      }
    } else {
      // count sub packets
      const valueLength = 11;
      const subPacketCnt = parseInt(buffer.substring(startIdx, startIdx + valueLength), 2);
      i += 18;
      for (let subPacket = 0; subPacket < subPacketCnt; subPacket++) {
        let parseIdx=0,value=0;
        [parseIdx, value] = parsePacket(buffer.substring(i));
        i+= parseIdx;
        operands.push(value);
      }
    }
  }
  console.log('operator', type, 'operands', operands);
  let packetValue =0 ;
  if (type == 0 || type == 4) {
    packetValue = operands.reduce((prev, cur) => prev + cur, 0);
  } else if (type == 1 ) {
    packetValue = operands.reduce((prev, cur) => prev * cur, 1);
  } else if (type == 2) {
    packetValue = operands.reduce((prev, cur) => Math.min(prev, cur), Infinity);
  } else if (type == 3) {
    packetValue = operands.reduce((prev, cur) => Math.max(prev, cur), 0);
  } else if (type == 5) {
    packetValue = operands[0] > operands[1] ? 1: 0;
  } else if (type == 6) {
    packetValue = operands[0] < operands[1] ? 1: 0;
  } else if (type == 7) {
    packetValue = operands[0] == operands[1] ? 1: 0;
  }

  return [i, packetValue];
}
