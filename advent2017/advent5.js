const input = '0	5	10	0	11	14	13	4	11	8	8	7	1	4	12	11';

// Part one
function advent6() {
  let nums = input.split('\t').map(Number);
  const input_size = nums.length;
  let seen = {};
  
  for (let i = 0; ; i++) {
    let copy = nums.slice();
    if (seen[copy] >= 0) {
      return {a: i, b: i - seen[copy]};
    }
    seen[copy] = i;
    
    let maxElem = nums.reduce((acc, cur, elem) => {
      return cur > acc.val ? {idx : elem, val : cur} : acc;
      }, {idx: 0, val: 0});
      
    nums[maxElem.idx] = 0;
    for (let j = 0 ; j < maxElem.val ; j++) { 
      nums[(maxElem.idx + j + 1) % nums.length] ++;
    }
  }
}

console.log(advent6());