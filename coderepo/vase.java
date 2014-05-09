package algos;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.BitSet;

/**
 * @author Jens Staahl
 */

public class vase {

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
			int ans = solveIt();
			out.write(ans + "\n");
		}
		out.flush();
	}

	private int solveIt() {
		int n = io.getInt();
		Point[] pts = new Point[n];
		if (n == 0) {
			return 0;
		}
		for (int i = 0; i < n; i++) {
			pts[i] = new Point(io.getInt() - 1, io.getInt() - 1);

		}
		for (int cur = 2; cur <= 10; cur++) {
			if (!can(pts, cur)) {
				return cur - 1;
			}
		}
		return 10;
	}

	private boolean can(Point[] pts, int cur) {
		BitSet[] set = new BitSet[36];
		int cnt = 0;
		for (int i = 0; i < pts.length; i++) {
			BitSet s = set[pts[i].x];
			if (s == null) {
				cnt++;
				set[pts[i].x] = new BitSet();
				s = set[pts[i].x];
			}
			s.set(pts[i].y);
		}
		BitSet[] hats = new BitSet[cnt];
		for (int i = 0; i < set.length; i++) {
			if (set[i] != null) {
				hats[cnt - 1] = set[i];
				cnt--;
			}
		}

		if (cur <= 10) {
			for (int k = 0; k < hats.length - 1; k++) {
				boolean possible = go(hats, k, 1, cur, (BitSet) hats[k].clone());
				if (possible) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean go(BitSet[] hats, int pos, int num, int target, BitSet set) {
		if(num == target){
			return true;
		}
		if(pos == hats.length){
			return false;
		}
		for (int i = pos+1; i < hats.length; i++) {
			BitSet clone = (BitSet) set.clone();
			clone.and(hats[i]);
			if(clone.cardinality() >= target){
				if(go(hats, i, num + 1, target, clone)){
					return true;
				}
			}
		}

		return false;
	}

	public static void main(String[] args) throws Throwable {
		new vase().solve();
	}

	public vase() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}