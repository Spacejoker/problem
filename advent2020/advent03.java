import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class advent03 {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);
	static final boolean TEST = false;

	public static void main(String[] args) throws Exception {
		new advent03();
	}

	public advent03() throws Exception {
		String[] input = readLines();

		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}

	private long solveFirst(String[] input) {
		int width = input[0].length();
		int x = 0, y =0, cnt =0;

		while(y < input.length-1) {
			x += 3; 
			y += 1;
			x = x % width;
			if (input[y].charAt(x) == '#') {
				cnt += 1;
			}
		}
		return cnt;
	}

	private long solveSecond(String[] input) {
		int width = input[0].length();
		long ans = 1;
		int [][] deltas = new int[][]{{1,1},{3,1},{5,1},{7,1},{1,2}};

		for (int i =0 ; i < deltas.length; i++) {
			int x = 0, y =0;
			long cnt = 0;
			while(y < input.length-1) {
				x += deltas[i][0]; 
				y += deltas[i][1];
				x = x % width;
				if (input[y].charAt(x) == '#') {
					cnt += 1;
				}
			}
			ans *= cnt;
		}
		return ans;
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
