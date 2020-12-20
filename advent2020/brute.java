import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class brute {

	public static void main(String[] args) throws Exception {
		new brute(true);
		new brute(false);
	}

	public brute(boolean test) throws Exception {
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
		Map<Long, Long> reg = new HashMap<>();
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
				expanded.clear();
				expand(newMask, 0, 0);
				for(long k : expanded) {
					reg.put(k, opVal);
				}
			}
		}
		long sum = 0;
		for (Entry<Long, Long> entry : reg.entrySet()) {
			sum += entry.getValue();
		}
		return sum;
	}

	List<Long> expanded = new ArrayList<>();

	private void expand(String newMask, int i, long value) {
		if (i == 36) {expanded.add(value);return;};
		long pow = 1L << i;
		char c = newMask.charAt(i);
		if (c == '1' || c == 'X') {
			expand(newMask, i+1, value + pow);
		}
		if (c == '0' || c == 'X') {
			expand(newMask, i+1, value);
		}
	}
	// 2332017537958
	// 2342318523013

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
