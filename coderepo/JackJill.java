package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Jens Staahl
 */

public class JackJill {

	// some local config
	static boolean test = true;
	static String testDataFile = "testdata.txt"; 
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		
		while(true) {
			int n = io.getInt();
			if(n == 0)
				break;
			
			boolean[][] impassible = new boolean[n][n];
			Pos GirlStart = null;
			Pos BoyStart = null;
			Pos GirlEnd = null;
			Pos BoyEnd = null;
			for (int i = 0; i < n; i++) {
				String s = io.getWord();
				for (int j = 0; j < s.length(); j++) {
					if(s.charAt(j) == '*') {
						impassible[i][j] = true;
					} else if(s.charAt(j) == 'H') {
						BoyStart = new Pos(i,j);
					} else if(s.charAt(j) == 'h') {
						GirlStart = new Pos(i,j);
					} else if(s.charAt(j) == 'S') {
						BoyEnd = new Pos(i,j);
					} else if(s.charAt(j) == 's') {
						GirlEnd = new Pos(i,j);
					}
				}
			}
			Pos BoyPos = BoyStart;
			Pos GirlPos = GirlStart;
			double max = n*n;
			double min = 0;
			double best = -1;
			while(min <= max) {
				double mid = (max+min)/2;
				
				Queue<Pos> boyQueue = new ArrayDeque<Pos>();
				Queue<Pos> girlQueue = new ArrayDeque<Pos>();
				
				
			}
			
			io.write("\n");
		}
		
		io.flush();
	}

	public static void main(String[] args) throws Throwable {
		new JackJill().solve();
	}

	public JackJill() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}

class Pos {
	int i;
	int j;
	
	public Pos(int i, int j) {
		this.i = i;
		this.j = j;
	}
}
