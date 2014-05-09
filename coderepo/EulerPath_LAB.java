package algos;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Stack;

/**
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 */

class Edge {
	int from, to;

	@Override
	public String toString() {
		return "Edge [from=" + from + ", to=" + to + "]";
	}

	public Edge(int from, int to) {
		super();
		this.from = from;
		this.to = to;
	}

}

class Graph {
	Stack[] edges;

	public Graph(int n) {
		edges = new Stack[n];
		for (int i = 0; i < n; i++) {
			edges[i] = new Stack();
		}
	}

	public void addEdge(Edge edge) {
		edges[edge.from].push(edge);
	}
}

class EulerPath {

}

public class EulerPath_LAB {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);

		while (true) {

			int n = io.getInt(), m = io.getInt();
			if (n == 0) {
				break;
			}
			int[] indegree = new int[n];
			int[] outdegree = new int[n];
			Graph g = new Graph(n);
			for (int i = 0; i < m; i++) {
				int a = io.getInt(), b = io.getInt();
				g.addEdge(new Edge(a, b));

				indegree[b]++;
				outdegree[a]++;
			}
			int start = -1;
			int end = -1;
			boolean fail = false;

			for (int i = 0; i < outdegree.length; i++) {
				if (outdegree[i] == indegree[i]) {// 2 == 1){
					// ok
				} else if (outdegree[i] - 1 == indegree[i]) {
					if (start != -1) {
						fail = true;
						break;
					}
					start = i;
				} else if (indegree[i] - 1 == outdegree[i]) {
					if (end != -1) {
						fail = true;
						break;
					}
					end = i;
				} else {
					fail = true;
					break;
				}
			}
			if (fail) {
				io.write("Impossible\n");
				continue;
			}
			boolean isTour = false;
			if (start == end) {
				start = 0;
				end = 0;
				isTour = true;
			} else {
				// g.addEdge(new Edge(end, start));j
				// m++;
			}

			int cur = start;
			Stack<Integer> stack = new Stack<Integer>();
			Stack<Integer> tour = new Stack<Integer>();
			while (g.edges[cur].size() > 0 || stack.size() > 0) {
				if (g.edges[cur].size() > 0) {
					Edge next = (Edge) g.edges[cur].pop();
					stack.push(cur);
					cur = next.to;
				} else {
					tour.push(cur);
					cur = stack.pop();
				}
			}
			if (tour.size() < m) {
				io.write("Impossible");
			} else {
				io.write(start + " ");
				while (tour.size() > 0) {
					io.write(tour.pop() + " ");
				}
			}
			io.write("\n");
		}
		io.flush();
	}

	public static void main(String[] args) throws Throwable {
		new EulerPath_LAB().solve();
	}

	public EulerPath_LAB() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream
																				// =
																				// System.out;

}