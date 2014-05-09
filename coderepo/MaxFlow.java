package algos;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Handles input and output to the kattis-problem
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 *
 */
public class MaxFlow {

	// some local config
	static boolean test = true;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);

		int V = io.getInt();
		int E = io.getInt();
		int source = io.getInt(), sink = io.getInt();
		List[] edges = new List[V + 1];

		for (int i = 0; i < edges.length; i++) {
			edges[i] = new ArrayList();
		}

		for (int i = 0; i < E; i++) {
			int from = io.getInt(), to = io.getInt();
			int c = io.getInt();
			FlowEdge forwardEdge = new FlowEdge(from, to);
			forwardEdge.capacity = c;

			edges[forwardEdge.from].add(forwardEdge);
		}

		new EdmondsKarp(V, source, sink, edges);

		// Print edges:
		long totalFlow = 0;
		for (int i = 0; i < edges[source].size(); i++) {
			FlowEdge e = (FlowEdge) edges[source].get(i);
			if (e.flow > 0) {
				totalFlow += e.flow;
			}
		}
		List<String> outdata = new ArrayList<String>(10000);

		for (int i = 0; i < edges.length; i++) {
			for (int j = 0; j < edges[i].size(); j++) {
				FlowEdge e = (FlowEdge) edges[i].get(j);
				long edgeflow = e.flow;
				if (edgeflow > 0) {
					outdata.add(e.from + " " + e.to + " " + edgeflow);
				}
			}
		}
		io.write(V + " " + totalFlow + " " + outdata.size() + "\n");
		for (String string : outdata) {
			io.write(string + "\n");
		}
		io.flush();
	}

	public static void main(String[] args) throws Throwable {
		new MaxFlow().solve();
	}

	public MaxFlow() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;

}