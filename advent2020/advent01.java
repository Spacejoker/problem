import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;

public class advent01 {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);

	public static void main(String[] args) throws Exception {
		new advent01();
	}

	public advent01() throws Exception {
		long[] input = readLines();

		long t0 = System.currentTimeMillis();
		Arrays.sort(input);
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}

	private long solveSecond(long[] input) {
		for (int i = 0; i < input.length - 2; i++) {
			for (int j = i + 1; j < input.length - 1; j++) {
				for (int k = j + 1; k < input.length; k++) {
					long a = input[i], b = input[j], c = input[k];
					if (a + b + c == 2020)
						return a * b * c;
				}
			}
		}
		return 0;
	}

	private long solveFirst(long[] input) {
		int p0 = 0, p1 = input.length - 1;
		while (true) {
			long sum = input[p0] + input[p1];
			if (sum < 2020) {
				p0++;
			} else if (sum > 2020) {
				p1--;
			} else {
				return input[p0] * input[p1];
			}
		}
	}

	long[] readLines() throws Exception {
		in = new BufferedReader(new FileReader(new File("advent2020/input.txt")));
		//in = new BufferedReader(new FileReader(new File("advent2020/test.txt")));
		List<Integer> values = new ArrayList<>();
		String s;
		while ((s = in.readLine()) != null) {
			values.add(Integer.valueOf(s));
		}
		return values.stream().mapToLong(i -> i).toArray();
	}

}
