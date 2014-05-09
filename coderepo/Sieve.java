package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.BitSet;

/**
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 */

public class Sieve {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		int limit = io.getInt(), q = io.getInt();
		BitSet sieve = new BitSet(limit + 1);
		
		for (int jump = 2; jump <= limit; jump++) {
			int val = jump;
			if(sieve.get(val)){
				continue;
			}
			val += jump;
			while(val <= limit){
				sieve.set(val);
				val += jump;
			}
		}

		sieve.set(0);
		sieve.set(1);

		int num = limit - sieve.cardinality() + 1;
		io.write(num + "\n");
		
		for (int i = 0; i < q; i++) {
			int n = io.getInt();
			if(sieve.get(n)){
				io.write("0\n");
			}
			else {
				io.write("1\n");
			}
			
		}
		
		io.flush();
	}

	public static void main(String[] args) throws Throwable {
		new Sieve().solve();
	}

	public Sieve() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}