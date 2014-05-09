package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Stack;

/**
 * @author Jens Staahl
 */

public class Jedna {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		String str = io.getWord();

		String[] split = str.split("=");
		String a = split[0];
		int value = Integer.parseInt(split[1]);

		int[][] dp = new int[a.length() + 1][value + 1];
		int[][] parent = new int[a.length() + 1][value + 1];
		int[][] leg = new int[a.length() + 1][value + 1];
		for (int i = 0; i < dp.length; i++) {
			Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
		}
		dp[0][0] = 0;
		for (int from = 0; from < a.length(); from++) {
			for (int len = 1; len <= 4; len++) {
				int to = from + len;
				if (to > a.length()) {
					break;
				}
				String next = a.substring(from, to);
				int nextval = Integer.parseInt(next);

				for (int i = 0; i <= value; i++) {
					if (dp[from][i] < Integer.MAX_VALUE / 2 && i + nextval <= value) {
						if (dp[to][i + nextval] > dp[from][i] + 1) {
							dp[to][i + nextval] = dp[from][i] + 1;
							parent[to][i + nextval] = from;
							leg[to][i + nextval] = i;
						}
						if (nextval == 0) {
							if (dp[to][i + nextval] > dp[from][i]) {
								dp[to][i + nextval] = dp[from][i];
								parent[to][i + nextval] = from;
								leg[to][i + nextval] = i;
							}

						}
					}
				}
			}
		}
		Stack<String> stack = new Stack<String>();
		int pos = a.length();
		int LOOOL = value;

		while (true) {
			stack.push(a.substring(parent[pos][value], pos));// )parent[pos][value]);
			if (pos == 0) {
				break;
			}
			int ttt = value;
			value = leg[pos][value];
			pos = parent[pos][ttt];
		}
		StringBuilder sb = new StringBuilder();
		while (stack.size() > 0) {
			String pop = stack.pop();
			sb.append(pop);
			if (pop.length() > 0 && Integer.parseInt(pop) == 0) {
				continue;
			}
			if (stack.size() > 0) {
				sb.append("+");
			}
		}
		sb.append("=" + LOOOL);
		System.out.println(sb.toString().substring(1));
	}

	public static void main(String[] args) throws Throwable {
		new Jedna().solve();
	}

	public Jedna() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}