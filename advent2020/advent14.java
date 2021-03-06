import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class advent14 {

	public static void main(String[] args) throws Exception {
		new advent14(true);
		new advent14(false);
	}

	public advent14(boolean test) throws Exception {
		run(test);
	}

	private void run(boolean test) throws Exception {
		String[] input = readLines(test);
		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms\n", t1 - t0);
	}

	private long solveFirst(String[] input) {
		Map<Long, Long> value = new HashMap<>();
		String mask = "";
		for (String s : input) {
			String[] split = s.split("=");
			String op = split[0].trim();
			String operand = split[1].trim();
			if (op.equals("mask")) {
				mask = operand;
			} else {
				long idx = Integer.valueOf(op.split("\\[")[1].split("\\]")[0]);
				long opVal = Integer.valueOf(operand);
				long val = 0;
				for (int i = 0; i < 36; i++) {
					boolean set = false;
					if (mask.charAt(35-i) == '1') {
						set = true;
					} else if (mask.charAt(35-i) == '0') {
					} else {
						set = (opVal & (1L << i)) > 0;
					}
					if (set) {
						val += 1L << i;
					}
				}
				value.put(idx, val);
			}
		}
		long sum = 0;
		for (Entry<Long, Long> entry : value.entrySet()) {
			sum += entry.getValue();
		}
		return sum;
	}

	class Write {
		String mask;
		Long value;

		public Write(String mask, Long value) {
			this.mask = mask;
			this.value = value;
		}
	}

	private long solveSecond(String[] input) {
		String mask = "";
		List<Write> writes = new ArrayList<>();
		for (String s : input) {
			String[] split = s.split("=");
			String op = split[0].trim();
			String operand = split[1].trim();
			if (op.equals("mask")) {
				mask = operand;
			} else {
				long idx = Integer.valueOf(op.split("\\[")[1].split("\\]")[0]);
				long opVal = Integer.valueOf(operand);
				String newMask = "";
				for (int i = 0; i < 36; i++) {
					boolean set = (idx & (1L << i)) > 0;
					boolean isFloating = false;
					if (mask.charAt(35-i) == '1') {
						set = true;
					} else if (mask.charAt(35-i) == '0') {
					} else if (mask.charAt(35-i) == 'X') {
						isFloating = true;
					}
					newMask = (isFloating ? "X" : (set ? "1" : "0")) + newMask;
				}
				// Create the new mask
				writes.add(new Write(newMask, opVal));
			}
		}
		int numWrites = writes.size();
		long sum = 0;
		out: for (int i = numWrites-1; i>= 0; i--) {
			Write lastWrite = writes.get(i);
			String modMask = lastWrite.mask;
			for (int j = numWrites -1 ; j > i; j--) {
        String prevWrite = writes.get(j).mask;
				if (prevWrite.length() ==0) continue;
				// Mod your thing;
				modMask = mod(modMask, prevWrite);
				if (modMask.length() == 0) continue out;
			}
			lastWrite.mask = modMask;
			long pow = 1;
			for (int j =0 ; j < modMask.length(); j++ ){
				if (modMask.charAt(j) == 'X') pow *= 2;
			}
			sum += pow * lastWrite.value;
		} 
		return sum;
	}

	private String mod(String modMask, String mask) {
		int len = modMask.length();
		boolean modified = false;
		char[] newMask = new char[len];
		for (int i = len-1; i >= 0; i--) {
			char c1 = modMask.charAt(i);
			char c2 = mask.charAt(i);
			if (c1 == '0' && c2 == '1')return modMask;
			if (c1 == '1' && c2 == '0')return modMask;
			if (c1 == c2) {
				newMask[i] = c1;
			} else if (c1 == 'X') {
				newMask[i] = c2 == '1' ? '0' : '1';
				modified = true;
			} else if (c2 == 'X') {
				newMask[i] = c1;
			}
		}
		String ret = "";
		if (modified) {
			for (int i= 0; i < newMask.length; i++) {
				ret += ""+newMask[i];
			}
		}
		return ret;
	}

	// 2332017537958
	// 2342318523013
	// 2881082759597

	String[] readLines(boolean test) throws Exception {
		String filename = test ? "advent2020/test.txt" : "advent2020/input.txt";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		List<String> values = new ArrayList<>();
		String s;
		while ((s = in.readLine()) != null) {
			values.add(s);
		}
		in.close();
		return values.stream().toArray(String[]::new);
	}
}
