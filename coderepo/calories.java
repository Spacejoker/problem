package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * @author Jens Staahl
 */

public class calories {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		while (true) {

			boolean first = true;
			double[] totCalories = new double[3];

			out: while (true) {

				double[] gram = new double[3];
				double[] calories = new double[3];
				double[] percentage = new double[3];

				for (int i = 0; i < 5; i++) {
					String s = io.getWord();
					if (s.equals("-")) {
						if (first) {
							return;
						}
						break out;
					}
					first = false;
					int pos = 0;
					if (i > 0) {
						pos = 1;
						if(i == 4){
							pos = 2;
						}
					}
					int val = Integer.parseInt(s.substring(0, s.length() - 1));

					switch (s.charAt(s.length() - 1)) {
					case 'g':
						gram[pos] += val;
						break;
					case 'C':
						calories[pos] += val;
						break;
					case '%':
						percentage[pos] += val;
						break;
					}
				}
				double totperc = 0;
				for (int i = 0; i < percentage.length; i++) {
					totperc += percentage[i];
				}
				double[] factor = new double[] { 9, 4, 7 };

				for (int i = 0; i < calories.length; i++) {
					calories[i] += gram[i] * factor[i];
				}
				double sum = 0;
				for (int i = 0; i < calories.length; i++) {
					sum += calories[i];
				}
				double x = 100 - totperc;
				
				double tot = sum * 100 / (x);

				for (int i = 0; i < 3; i++) {
					calories[i] += tot * percentage[i]/100.d;
				}
				for (int i = 0; i < 3; i++) {
					totCalories[i] += calories[i];
				}
			}
			double sum = 0;
			for (int i = 0; i < totCalories.length; i++) {
				sum += totCalories[i];
			}

			System.out.println((int)(totCalories[0] / sum * 100 + 0.5d) + "%");

		}
	}

	public static void main(String[] args) throws Throwable {
		new calories().solve();
	}

	public calories() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}