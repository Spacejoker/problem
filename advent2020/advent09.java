import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class advent09 {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);
	static final boolean TEST = false;

	public static void main(String[] args) throws Exception {
		new advent09();
	}

	public advent09() throws Exception {
		String[] input = readLines();

		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}

	enum Operation {
		NOP, JMP, ACC;
	}

	class Instruction {
		Operation op;
		long value = 0;

		public Instruction(advent09.Operation op, long value) {
			this.op = op;
			this.value = value;
		}
	}

	private long solveFirst(String[] input) {
		long[] x = new long[input.length];
		for (int i =0 ; i < input.length; i++) {
			x[i] = Long.valueOf(input[i]);
		}
		int n = TEST ? 5 : 25;
		long[] hist = new long[n];
		for (int i= 0 ; i < n; i++) {
			hist[i] = x[i];
		}
		for (int i = n;true; i++) {
			long next = x[i];
			int nextIdx = i%n;
			boolean match = false;
			for (int j =0 ; j < i ; j++) {
				for (int k =j+1 ; k < i; k++) {
					long sum = hist[j%n] + hist[k%n];
					///System.out.println("sum" + sum);
					if (sum == next) match = true;
				}
			}
			if (! match) {
				return next;
			} else {
				hist[nextIdx] = next;
			}
		}
	}

	private long solveSecond(String[] input) {
		long target = 26796446;
		long[] x = new long[input.length];
		for (int i =0 ; i < input.length; i++) {
			x[i] = Long.valueOf(input[i]);
		}
		int n = input.length;
		for(int i=0 ; i< n; i++) {
			for(int j =i+1; j<n; j++) {
				long sum = 0;
				for (int k =i ; k <= j; k++) {
					sum += x[k];
				}
				if (sum > target)break;
				if (sum == target) {
					long min = Long.MAX_VALUE;
					long max = 0;
				for (int k =i ; k <= j; k++) {
					min = Math.min(min, x[k]);
					max = Math.max(max, x[k]);
				}
				return min+max;

				}
			}
		}
		return -1;
	}

	String[] readLines() throws Exception {

		String filename = TEST ? "advent2020/test.txt" : "advent2020/input.txt";
		in = new BufferedReader(new FileReader(filename));
		List<String> values = new ArrayList<>();
		String s;
		while ((s = in.readLine()) != null) {
			values.add(s);
		}
		return values.stream().toArray(String[]::new);
	}

}
