import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;

public class template {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);

	public static void main(String[] args) throws Exception {
		new template();
	}

	public template() throws Exception {
		long[] input = readLines();

		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}

	private long solveSecond(long[] input) {
		return 0;
	}

	private long solveFirst(long[] input) {
		return 0;
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
