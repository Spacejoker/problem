package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 */
public class DijkstraTT {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		while (true) {

			int n = io.getInt(), m = io.getInt(), q = io.getInt(), s = io.getInt();
			if (n == 0) {
				break;
			}
			TimeTableGraph g = new TimeTableGraph(n);
			for (int i = 0; i < m; i++) {
				g.addEdge(new TimeTableEdge(io.getInt(), io.getInt(), io.getInt(), io.getInt(), io.getInt()));
			}
			DijkstraTimeTable d = new DijkstraTimeTable(g, s);
			for (int i = 0; i < q; i++) {
				long print = d.dist(io.getInt());
				if(print == Long.MAX_VALUE/2){
					io.write("Impossible\n");
				} else {
					io.write( print + "\n");
				}
			}
			io.write("\n");
		}
		io.flush();
	}

	public static void main(String[] args) throws Throwable {
		new DijkstraTT().solve();
	}

	public DijkstraTT() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}