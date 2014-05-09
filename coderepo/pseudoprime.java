package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;

/**
 * @author Jens Staahl
 */

public class pseudoprime {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		while (true) {
			BigInteger p = BigInteger.valueOf(io.getLong());
			BigInteger a = BigInteger.valueOf(io.getLong());
			if (p.equals(BigInteger.ZERO) && a.equals(BigInteger.ZERO)) {
				break;
			}
			BigInteger pow = a.modPow(p, p);
			if (a.equals(pow) && !p.isProbablePrime(20)) {
				out.write("yes\n");
			} else {
				out.write("no\n");
			}
		}
		out.flush();
	}

	public static void main(String[] args) throws Throwable {
		new pseudoprime().solve();
	}

	public pseudoprime() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}