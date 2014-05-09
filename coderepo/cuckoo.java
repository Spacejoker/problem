package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author Jens Staahl
 */

public class cuckoo {

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
			solveIt();
		}
	}

	private void solveIt() {
		int m = io.getInt();
		int n = io.getInt();
		List[] edges = new List[n];
		for (int i = 0; i < n; i++) {
			edges[i] = new ArrayList();
		}
		int[] degree = new int[n];
		for (int i = 0; i < m; i++) {
			int a = io.getInt();
			int b = io.getInt();
			degree[a]++;
			degree[b]++;
			edges[a].add(b);
			edges[b].add(a);
		}
		Stack<Integer> stack = new Stack<Integer>();
		boolean[] in = new boolean[n];
		Arrays.fill(in, true);
		for (int i = 0; i < degree.length; i++) {
			if (degree[i] == 1) {
				stack.push(i);
				in[i] = false;
			}
		}
		while (stack.size() > 0) {
			Integer pop = stack.pop();
			for (int i = 0; i < edges[pop].size(); i++) {
				Integer next = (Integer) edges[pop].get(i);
				if (degree[next] > 1) {
					degree[next]--;
					if (degree[next] == 1 && in[next]) {
						stack.push(next);
						in[next] = false;
					}
				}
			}
		}
		boolean ok = true;
		for (int i = 0; i < in.length; i++) {
			if (in[i] && degree[i] > 2) {
				ok = false;
			}
		}
		if (ok) {
			System.out.println("successful hashing");
		} else {
			System.out.println("rehash necessary");
		}

	}

	public static void main(String[] args) throws Throwable {
		new cuckoo().solve();
	}

	public cuckoo() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}