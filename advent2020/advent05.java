import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;

public class advent05 {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);
	static final boolean TEST = false;

	public static void main(String[] args) throws Exception {
		new advent05();
	}

	public advent05() throws Exception {
		String[] input = readLines();

		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}

	private long solveFirst(String[] input) {
		long max = 0;
		for (String code : input) {
			long seatId = getSeatId(code);
			max = Math.max(max, seatId);
		}
		return max;
	}

	private long getSeatId(String code) {
		String binary = code.replaceAll("F", "0").replaceAll("B","1").replaceAll("L","0").replaceAll("R","1");
		long row = Long.valueOf(binary.substring(0, 7), 2);
		long column = Long.valueOf(binary.substring(7), 2);
		return row*8 + column;
	}

	private long solveSecond(String[] input) {
		boolean[] found = new boolean[1000];
		for (String code : input) {
			found[(int)getSeatId(code)] = true;
		}
		for (int i = 1; i < found.length; i++) {
			if (found[i-1] && !found[i] && found[i+1]) return i;
		}
		return 0;
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
