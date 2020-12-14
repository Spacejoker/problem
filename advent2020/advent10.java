import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class advent10 {

	public static void main(String[] args) throws Exception {
		new advent10(true);
		new advent10(false);
	}

	public advent10(boolean test) throws Exception {
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
		int[] n = new int[input.length + 2];
		int max = 0;
		for(int i= 0; i < input.length; i++) {
			n[i] = Integer.valueOf(input[i]);
			max = Math.max(n[i], max);
		}
		Arrays.sort(n);
		int cur = 0, a =0, b = 0;
		
		for (int i= 0;i < n.length; i++) {
			int delta = n[i] - cur;
			cur = n[i];
			if (delta == 1) a++;
			if (delta == 3) b++;
		}
		return a * (b+1);
	}

	int MAX_VAL = 0;

	private long solveSecond(String[] input) {
		int[] n = new int[input.length];
		MAX_VAL = 0;
		for(int i= 0; i < input.length; i++) {
			n[i] = Integer.valueOf(input[i]);
			MAX_VAL = Math.max(n[i], MAX_VAL);
		}
		Arrays.sort(n);
		return go(n, -1, 0);
	}

	long dp[][] = new long[200][200];
	// Recursive dp
	private long go(int[] n, int pos, int value) {
		if (pos >= 0 && dp[pos][value] > 0) {
			return dp[pos][value];
		}
		if (pos == n.length-1) {
			return value == MAX_VAL ? 1 : 0;
		}
		long ways = 0;
		for (int i = pos+1 ; i < n.length; i++) {
			int val = n[i];
			if (val -3 <= value) {
				ways += go(n, i, val);
			}
		}
		if (pos >0) dp[pos][value] = ways;
		return ways;
	}

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
