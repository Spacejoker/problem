# Advent solution
input = "192,69,168,160,78,1,166,28,0,83,198,2,254,255,41,12"

def run_seq(nums, s, start_idx, skip_size):
    range_length = len(nums)
    idx = start_idx
    for i, item in enumerate(s):
        for j in range(0, int(item/2)):
            left_idx = (idx + j) % range_length
            right_idx = (idx + item - j - 1) % range_length
            nums[left_idx], nums[right_idx] = nums[right_idx], nums[left_idx]
        idx += item + skip_size
        skip_size += 1
    return nums, idx, skip_size

def solve_a():
    ans_a, _, _ = run_seq(list(range(0, 2**8)), list(map(int, input.split(","))), 0, 0)
    return str(ans_a[0] * ans_a[1])

def to_xor(nums):
    ret = []
    for i in range(0, 16):
        val = nums[i*16]
        for j in range(1, 16):
            val ^= nums[i*16 + j]
        ret.append(val)
    return ret

def c_to_hex(num):
    val = hex(num)[2:] 
    while (len(val) < 2):
        val = '0' + val
    return val

def to_hex(nums):
    return str.join('', list(map(c_to_hex, nums)))

def solve_b():
    ascii_input = list(map(ord, input)) + [17,31,73,47,23]
    nums = list(range(0, 2**8))
    start_idx = 0
    skip_size = 0
    for i in range(0, 2**6):
        nums, start_idx, skip_size = run_seq(nums, ascii_input, start_idx, skip_size)
    xor_list = to_xor(nums)
    hex_values = to_hex(xor_list)
    return hex_values

print("A: " + solve_a())
print("B: " + solve_b())

