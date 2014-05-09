package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * @author Jens Staahl
 */

public class sylvester {

	// some local config
	static boolean test = false;
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

	private void solveIt() throws IOException {
		long n = io.getLong(), y = io.getLong(), x = io.getLong(), h = io.getLong(), w = io.getLong();

		for (long col = x; col < x + w; col++) {
			for (long row = y; row < y + h; row++) {
				out.write((go(row, col, n, true) ? "1 " : "-1 "));
			}
			out.write("\n");
		}
			out.write("\n");
		out.flush();

	}

	private boolean go(long row, long col, long n, boolean b) {
		if(n == 1){
			return b;
		}
		long half = n/2;
		if(row >= half && col >= half){
			return go(row - half, col - half, half, !b);
		} else {
			long newrow = row >= half ? row - half : row;
			long newcol = col >= half ? col - half : col;
			return go(newrow, newcol, half, b);
		}
	}

	public static void main(String[] args) throws Throwable {
		new sylvester().solve();
	}

	public sylvester() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}