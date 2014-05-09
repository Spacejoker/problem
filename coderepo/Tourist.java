package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * @author Jens Staahl
 */

public class Tourist {

	// some local config
	static boolean test = true;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		int n = io.getInt();
		for (int i = 0; i < n; i++) {
			solveIt();

		}
	}

	String[] board;
	int n, m;
	int[][][] sum;

	private void solveIt() {
		long t0 = System.currentTimeMillis();
		m = io.getInt();
		n = io.getInt();
		sum = new int[n][n][m];
		board = new String[n];
		win = new int[n][m+1];
		for (int i = 0; i < n; i++) {
			board[i] = io.getWord() + " ";
		}
		for (int idx = 0; idx < m; idx++) {
			for (int i = 0; i < n; i++) {
				if (board[i].charAt(idx) == '*') {
					win[i][idx] = 1;
				}
			}
		}

		memo = new int[n + 1][n + 1][m + 1][n + 1];
		int ans = rec(0, 0, 0, 0);
		System.out.println(ans + (win[0][0])-1);
		if(test){
			System.out.println("Time: " + (System.currentTimeMillis() - t0));
		}
	}

	int[][][][] memo;
	int[][] win;

	private int rec(int a, int b, int idx, int upperb) {
		if (idx == m) {
			return 1;
		}
		if (memo[a][b][idx][upperb] > 0) {
			return memo[a][b][idx][upperb];
		}
		if (idx == m - 1 && a == n - 1 && b == n - 1) {
			return win[a][idx];
//			return sum[b][b][idx];
		}
		int max = 0;

		// b goes down:
		if (b < n - 1 && board[b + 1].charAt(idx) != '#') {
			max = Math.max(rec(a, b + 1, idx, upperb) + win[b + 1][idx], max);
		}
		// a goes down:
		if (a < b && board[a + 1].charAt(idx) != '#') {
			int w = a + 1 < upperb ? win[a + 1][idx] : 0;
			max = Math.max(rec(a + 1, b, idx, upperb) + w, max);
		}
		if (board[a].charAt(idx + 1) != '#' && board[b].charAt(idx + 1) != '#') {
			int w = win[b][idx + 1];
			if (a < b) {
				w += win[a][idx + 1];
			}
			max = Math.max(rec(a, b, idx + 1, b) + w, max);
		}

		memo[a][b][idx][upperb] = max;
		return max;
	}

	public static void main(String[] args) throws Throwable {
		new Tourist().solve();
	}

	public Tourist() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}