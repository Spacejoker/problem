package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * @author Jens Staahl
 */

public class parking {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt"; 
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		int[] cnt = new int[101];
		int[] cost = new int[4];
		for (int i = 0; i < 3; i++) {
			cost[i+1] = io.getInt();

		}
		for (int i = 0; i < 3; i++) {

			int a = io.getInt(), b = io.getInt();
			for (int j = a+1; j <= b; j++) {
				cnt[j]++;
			}
		}
		int ans = 0;
		for (int i = 0; i < cnt.length; i++) {
			ans += cost[cnt[i]]*cnt[i];
		}
		System.out.println(ans);

	}

	public static void main(String[] args) throws Throwable {
		new parking().solve();
	}

	public parking() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}