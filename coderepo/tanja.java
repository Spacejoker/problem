package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * @author Jens Staahl
 */

public class tanja {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		String word = io.getWord();
		int n = word.length();
		for (int i = (int) Math.sqrt(n); i >= 0; i--) {
			if (n % i == 0) {
				int row = i;
				int col = n / i;
				StringBuffer sb = new StringBuffer();
				String[] ans = new String[word.length()];
				int c = 0;
				for (int j = 0; j < col; j++) {
					for (int k = 0; k < row; k++) {
						ans[j + k * col] = word.charAt(c) + "";
						c++;
						// sb.append(word.charAt(j + k * row) + "");
					}
				}
				for (int j = 0; j < ans.length; j++) {
					sb.append(ans[j]);
				}
				System.out.println(sb.toString());
				return;
			}
		}
	}

	public static void main(String[] args) throws Throwable {
		new tanja().solve();
	}

	public tanja() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}