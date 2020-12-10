import java.io.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;

import static java.lang.Math.*;

public class advent10 {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);
	static final boolean TEST = false;

	public static void main(String[] args) throws Exception {
		new advent10();
	}

	public advent10() throws Exception {
		String[] input = readLines();

		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}
	private long solveFirst(String[] input) {
		int[] n = new int[input.length + 2];
		int max = 0;
		for(int i= 0; i < input.length; i++) {
			n[i] = Integer.valueOf(input[i]);
			max = Math.max(n[i], max);
		}

		// need to end at 153
		System.out.print(max);
		Arrays.sort(n);
		int cur = 0, a =0, b = 0;

		// there is one dp: [at pos i][with value z]
		//long dp[][] = new long[n.length][200];
		
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
		// need to end at 153
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
