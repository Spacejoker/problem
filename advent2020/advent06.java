import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;

public class advent06 {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);
	static final boolean TEST = false;

	public static void main(String[] args) throws Exception {
		new advent06();
	}

	public advent06() throws Exception {
		String[] input = readLines();

		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}

	private long solveFirst(String[] input) {
		List<String> rows = new ArrayList<>();
		int sum = 0;
		for (String s : input) {
			if (s.length() == 0) {
				if (rows.size() > 0) {
					sum +=calcSize(rows.stream().toArray(String[]::new));
				}
				rows.clear();;
			} else {
				rows.add(s);
			}
		}
		if (rows.size() > 0) {
			sum +=calcSize(rows.stream().toArray(String[]::new));
		}
		return sum;
	}

	private int calcSize(String[] array) {
		boolean[] found = new boolean[26];
		for (String row : array) {
			for(char c : row.toCharArray()) {
				found[c-'a'] = true;
			}
		}
		int cnt = 0;
		for(boolean b: found) cnt += b?1:0;
		return cnt;
	}

	private long solveSecond(String[] input) {
		List<String> rows = new ArrayList<>();
		int sum = 0;
		for (String s : input) {
			if (s.length() == 0) {
				if (rows.size() > 0) {
					sum +=calcIntersect(rows.stream().toArray(String[]::new));
				}
				rows.clear();;
			} else {
				rows.add(s);
			}
		}
		if (rows.size() > 0) {
			sum +=calcIntersect(rows.stream().toArray(String[]::new));
		}
		return sum;
	}

	private int calcIntersect(String[] array) {
		boolean[] found = new boolean[26];
		for (int i= 0; i < 26; i++) {
			found[i] = true;
		}
		for (String row : array) {
			boolean[] t = new boolean[26];
			for(char c : row.toCharArray()) {
				t[c-'a'] = true;
			}
			for(int i = 0; i < 26; i++) {
				if (!t[i]) found[i] = false;
			}
		}
		int cnt = 0;
		for(boolean b: found) cnt += b?1:0;
		return cnt;
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
