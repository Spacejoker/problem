package algos;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 */

public class MinCut {

	// some local config
	static boolean test = false;
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
		
		List<Integer> mincutEdges = new EdmondsKarp(V, source, sink, edges).getMinimumCut();

		io.write(mincutEdges.size() + "\n");
		for (Integer ec : mincutEdges) {
			io.write(ec + "\n");
			
		}
		io.flush();
	}

	public static void main(String[] args) throws Throwable {
		new MinCut().solve();
	}

	public MinCut() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;

}