require('source-map-support').install();
//const input = [[6,16],[12,13],[24,30],[7,10],[14,22],[6,11],[12,21],[23,32],[23,31],[15,18],[17,24]]
//console.log('ans:', overlappingIntervals(input));

console.log('trade gain', tradeGain([1,[63,121,64,109,39,151,184]]));
console.log('waystosum:', findPartitions(20)-1);
let dp = {};
function findPartitions(num = 1) {
   const arr = Array(num + 1).fill(null).map(() => {
      return Array(num + 1).fill(null);
   });
   for (let j = 1; j <= num; j += 1) {
      arr[0][j] = 0;
   }
   for (let i = 0; i <= num; i += 1) {
      arr[i][0] = 1;
   }
   for (let i = 1; i <= num; i += 1) {
      for (let j = 1; j <= num; j += 1) {
         if (i > j) {
            arr[i][j] = arr[i - 1][j];
         }
         else {
            const exclusive = arr[i - 1][j];
            const inclusive = arr[i][j - i];
            arr[i][j] = exclusive + inclusive;
         }
      }
   }
   return arr[num][num];
};

const input = [0,1,0,10,0,9,0,3,2,0,6,0,7,8,10,5]
function canJump(input: number[]) {return 0;}



function primeCalc() {
  let prime = 137087620
  for (let i = 3; i <=prime; i+=2) {
    if (prime % i == 0) {
      console.log('Prime factor: ', i);
      prime /= i;
    }
  }
}

function tradeGain(input) {
  //const input = [5, [77,2,193,193,62,130,149,150,56,177,16,112,186,52,107,26,144,40,42,144,165,146,9,74,155,87,130,99,102,15,151,164,111,170,147,177,141,115,87,177,28,108,134,53,29,136,26,104,46,139]]
  //const input = [1, [5, 10, 0, 100]]; //, 0, 10, 20, 0, 100]];
  const trades :number= input[0] as number;
  const prices :number[]= input[1] as number[];
  // [day][trades_done][have_stock]
  //const dp = new Array(input.length).map((e) => new Array(trades+1).map(p => [0,0]));
  const dp = new Array(prices.length).fill(false).map(() =>new Array(trades+1).fill(false).map(() => new Array(2).fill( -9999999)));

  console.log();
  console.log("~~~~~~~ Trading Q: ~~~~~~~");
  // init
  dp[0][1][1] = -prices[0];
  dp[0][0][0] = 0;

  console.log('pre', dp[0][0][1]);
  console.log('pre', dp[0][1][1]);
  for (let day = 1 ; day < prices.length; day++ ) {
    for (let t = 0; t <= trades; t ++) {
      console.log(day, t);
      // don't have it - keep nothing or sell
      dp[day][t][0] = Math.max(dp[day-1][t][0], dp[day-1][t][1] + prices[day]);
      if (t>0) {
        dp[day][t][1] = Math.max(dp[day-1][t-1][0]-prices[day], dp[day-1][t][1]);
      }
      console.log(dp[day][t][0], dp[day][t][1], dp[day-1][t][0], dp[day-1][t][1] + prices[day]);
    }
  }
  let best = 0;
  for (let t = 0; t <= trades; t++) {
    best = Math.max(best, dp[prices.length-1][t][0]);
  }

  console.log('best gain:', best);
}


function overlappingIntervals(input: number[][]) {
  input.sort((a,b) => a[0]-b[0]);
  console.log('sorted', input);
  for (let i =0 ; i < input.length-1; i++ ){
    console.log(input, i);
    if (input[i][1] >= input[i+1][0]) {
      const newInterval = [input[i][0], Math.max(input[i+1][1], input[i][1])];
      input.splice(i, 2, newInterval);
      i--;
    }
  }
  return input;
}

