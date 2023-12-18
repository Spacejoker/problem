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

  const start = process.hrtime();
  const res = solve(input)
  const end = process.hrtime(start);
  const timeInMilliseconds = end[0] * 1000 + end[1] / 1e6;
  console.log(`Ans: ${res} in ${timeInMilliseconds.toFixed(0)} ms.`);
});

function rowsToGrid(input: string[]) {
  return input.map((row) => {
    //let ret : string[] = [];
    let ret : number[] = [];
    for (let i =0; i < row.length; i++) {
      ret.push(Number(row.charAt(i)));
      //ret.push(row.charAt(i));
    }
    return ret;
  });
}

type Comparator<T> = (a: T, b: T) => number;

class PriorityQueue<T> {
  private heap: T[];
  private compare: Comparator<T>;

  constructor(comparator: Comparator<T>) {
    this.heap = [];
    this.compare = comparator;
  }

  private getLeftChildIndex(parentIndex: number): number {
    return 2 * parentIndex + 1;
  }

  private getRightChildIndex(parentIndex: number): number {
    return 2 * parentIndex + 2;
  }

  private getParentIndex(childIndex: number): number {
    return Math.floor((childIndex - 1) / 2);
  }

  private swap(indexOne: number, indexTwo: number): void {
    [this.heap[indexOne], this.heap[indexTwo]] = [this.heap[indexTwo], this.heap[indexOne]];
  }

  private heapifyUp(): void {
    let index = this.heap.length - 1;
    while (this.getParentIndex(index) >= 0 && this.compare(this.heap[this.getParentIndex(index)], this.heap[index]) > 0) {
      this.swap(this.getParentIndex(index), index);
      index = this.getParentIndex(index);
    }
  }

  private heapifyDown(): void {
    let index = 0;
    let smallerChildIndex: number;

    while (this.getLeftChildIndex(index) < this.heap.length) {
      smallerChildIndex = this.getLeftChildIndex(index);

      if (this.getRightChildIndex(index) < this.heap.length && this.compare(this.heap[this.getRightChildIndex(index)], this.heap[smallerChildIndex]) < 0) {
        smallerChildIndex = this.getRightChildIndex(index);
      }

      if (this.compare(this.heap[index], this.heap[smallerChildIndex]) < 0) {
        break;
      } else {
        this.swap(index, smallerChildIndex);
      }

      index = smallerChildIndex;
    }
  }

  enqueue(item: T): void {
    this.heap.push(item);
    this.heapifyUp();
  }

  dequeue(): T | undefined {
    if (this.heap.length === 0) return undefined;
    if (this.heap.length === 1) return this.heap.pop();

    const item = this.heap[0];
    this.heap[0] = this.heap.pop()!;
    this.heapifyDown();
    return item;
  }

  isEmpty(): boolean {
    return this.heap.length === 0;
  }
}
// x~y~direction
let seen = new Set<string>();
// up left down right
const direction = [[-1, 0], [0, -1], [1, 0], [0, 1]];
let grid : any;
let width, height;
let best = 9999999999999;

function solve(input) {
  let ret = 0;
  grid = rowsToGrid(input);
  width = grid[0].length;
  height = grid.length;
  function getV(x, y, heat) { return heat;}

  const stack = new PriorityQueue<[number, number, number, number, number]>(([ax, ay, ad, ac, ah], [bx, by, bd, bc, bh]) => getV(ax, ay, ah) - getV(bx, by, bh));
  stack.enqueue([0, 0, 2, 1, 0])
  stack.enqueue([0, 0, 3, 1, 0])
  out: while(!stack.isEmpty()) {
    const [x, y, dirIdx, consecutiveSteps, heatLoss] = stack.dequeue()!;
    let cellLoss = grid[y][x];
    const newHeatLoss = heatLoss + cellLoss;
    if (newHeatLoss  + (width - 1- x) + (height - 1 -y)  >= best) continue;

    if (x == width-1 && y == height -1 ) {
      if (consecutiveSteps >= 4 && consecutiveSteps <= 10) {
        if (newHeatLoss < best) console.log('record', newHeatLoss);
        best = Math.min(best, heatLoss + cellLoss);
      }
      continue;
    }

    const key = [x, y, dirIdx, consecutiveSteps].join('~')
    if (seen[key] <= newHeatLoss) continue;
    // Maybe we should also check all entries at [x, y], realistically +18 or something is the maximum we need to care about.
    seen[key] = newHeatLoss;

    for (let i= dirIdx -1; i <= dirIdx + 1; i++) {
      const isStraight = i == dirIdx;
      const idxP = (i+4)%4;

      if (isStraight && consecutiveSteps >= 10) continue;
      if (!isStraight && consecutiveSteps < 4) continue;
      let newx = x + direction[idxP][1];
      let newy = y + direction[idxP][0];
      if (newx < 0 || newx >= grid[0].length || newy < 0 || newy >= grid.length) continue;

      stack.enqueue([newx, newy, idxP, isStraight ? consecutiveSteps+1 : 1, newHeatLoss]);
    }
  }


  return best - grid[0][0];
}

// 1113 and 1100, not 1107, 1112, 1111... 1106 :thinking:
