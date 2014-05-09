package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author Jens Staahl
 */

public class seti {

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

	private void solveIt() {
		int mod = io.getInt();
		String s = io.getWord();
		int n = s.length();
		int[] x = new int[s.length()];
		for (int i = 0; i < x.length; i++) {
			x[i] = s.charAt(i) - 'a' + 1;
		}
		int[][] matrix = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				matrix[i][j] = BigInteger.valueOf(i).modPow(BigInteger.valueOf(j), BigInteger.valueOf(mod)).intValue();
			}
		}
		
		for (int i = 0; i < matrix.length; i++) {
			System.out.println(Arrays.toString(matrix[i]));
		}
		
	}

	public static void main(String[] args) throws Throwable {
		new seti().solve();
	}

	public seti() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}