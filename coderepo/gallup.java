package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * @author Jens Staahl
 */

public class gallup {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		int casenr = 1;
		while (true) {
			int x = solveIt(casenr);
			if (x == 0) {
				return;
			}
			String ans = x + "";
			if(x ==-1){
				ans = "error";
			}
			System.out.println("Case " + casenr + ": " + ans);
			casenr++;
		}
	}

	private int solveIt(int casenr) {
		int n = io.getInt();
		if (n == 0) {
			return 0;
		}
		double[] perc = new double[n];
		double mod = .5;
		for (int i = 0; i < n; i++) {
			String word = io.getWord();
			if (i == 0 && word.contains(".")) {
				String wrd = word.split("\\.")[1];
				for (int j = 0; j < wrd.length(); j++) {
					mod /= 10;
				}
			}
			perc[i] = Double.parseDouble(word) / 100.d;
		}
		double s = 0;
		mod /= 100;
		for (int i = 0; i < perc.length; i++) {
			s+= perc[i];
		}

		if(perc.length <= 2 && Math.abs(s-0.99) < 1e-10){
			return -1;
		}

		out: for (int pp = 1; pp < 10000; pp++) {
			int minleft = pp;
			int maxleft = pp;

			for (int i = 0; i < perc.length; i++) {
				double use = perc[i] * pp;
				double min = Math.floor(use);
				if (min / pp > perc[i] - mod && min > 0) {
					
				} else if ((min + 1) / pp < perc[i] + mod) {
					min++;
				} else {
					continue out;
				}
				double max = min;
				while ((min - 1) / pp >= perc[i] - mod) {
					min--;
				}
				while ((max + 1) / pp <= perc[i] + mod) {
					max++;
				}
				minleft -= max;
				maxleft -= min;
			}
			if (minleft <= maxleft && minleft <= 0 && maxleft >= 0) {
				return pp;
			}
		}
		return -1;
	}

	public static void main(String[] args) throws Throwable {
		new gallup().solve();
	}

	public gallup() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}