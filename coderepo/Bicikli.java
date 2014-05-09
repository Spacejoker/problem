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

public class Bicikli {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		int n = io.getInt(), m = io.getInt();
		List<Integer>[] edges = new List[n];

		num = new int[n];
		lowlink = new int[n];
		comp = new int[n];
		Arrays.fill(comp, -1);
		stack = new Stack<Integer>();
		no_vert = 0;
		no_comp = 0;
		List<Integer>[] backEdge = new List[n];

		for (int i = 0; i < m; i++) {
			int a = io.getInt() - 1, b = io.getInt() - 1;
			if (edges[a] == null) {
				edges[a] = new ArrayList();
			}
			if (backEdge[b] == null) {
				backEdge[b] = new ArrayList();
			}
			edges[a].add(b);
			backEdge[b].add(a);
		}

		Stack<Integer> fromStart = new Stack<Integer>();
		boolean[] reachStart = new boolean[n];
		boolean[] reachEnd = new boolean[n];
		fromStart.push(0);
		reachStart[0] = true;

		while (fromStart.size() > 0) {
			Integer pop = fromStart.pop();
			if (edges[pop] == null) {
				continue;
			}
			for (int i = 0; i < edges[pop].size(); i++) {
				Integer next = edges[pop].get(i);
				if (!reachStart[next]) {
					reachStart[next] = true;
					fromStart.push(next);
				}
			}
		}

		fromStart.push(1);
		reachEnd[1] = true;

		while (fromStart.size() > 0) {
			Integer pop = fromStart.pop();
			if (backEdge[pop] == null) {
				continue;
			}
			for (int i = 0; i < backEdge[pop].size(); i++) {
				Integer next = backEdge[pop].get(i);
				if (!reachEnd[next]) {
					reachEnd[next] = true;
					fromStart.push(next);
				}
			}
		}
		backEdge = null;
		List<Integer>[] edgePrim = new List[n];
		for (int i = 0; i < n; i++) {
			edgePrim[i] = new ArrayList<Integer>();
			if (reachStart[i] && reachEnd[i]) {
				if (edges[i] == null) {
					continue;
				}
				for (int j = 0; j < edges[i].size(); j++) {
					if (reachStart[edges[i].get(j)] && reachEnd[edges[i].get(j)]) {
						edgePrim[i].add(edges[i].get(j));
					}
				}
			}
			edges[i] = null;
		}
		reachStart = null;
		reachEnd = null;
		// this.edges = edgePrim;

		for (int i = 0; i < n; i++) {
			if (comp[i] == -1) {
				tarjan(i, edgePrim);
			}
		}

		int[] compCnt = new int[n + 1];
		for (int i = 0; i < comp.length; i++) {
			if (comp[i] >= 0) {
				compCnt[comp[i]]++;
			}
		}
		comp = null;
		lowlink = null;
		num = null;

		for (int i = 0; i < compCnt.length; i++) {
			if (compCnt[i] > 1) {
				System.out.println("inf");
				return;
			}
		}
		memo = new int[n];
		Arrays.fill(memo, -1);
		long ans = rec(0, edgePrim);
		String s = Long.valueOf(ans).toString();// (;[]) collection.toArray(new ;[collection.size()])
		if (addZeroes) {
			while (s.length() < 9) {
				s = "0" + s;
			}
		}
		System.out.println(s);
	}

	int[] memo;

	private long rec(int i, List<Integer>[] edges) {
		if (memo[i] >= 0) {
			return memo[i];
		}
		if (i == 1) {
			return 1;
		}
		int sum = 0;
		for (int j = 0; j < edges[i].size(); j++) {
			int next = edges[i].get(j);
			sum += rec(next, edges);
			if (sum >= 1e9) {
				addZeroes = true;
				sum %= 1e9;
			}
		}

		memo[i] = sum;
		return sum;
	}

	boolean addZeroes = false;

	int[] num, lowlink, comp;
	Stack<Integer> stack;
	// List<Integer>[] edges;
	int no_vert, no_comp;

	private void tarjan(int v, List[] edges) {
		num[v] = no_vert;
		no_vert++;
		lowlink[v] = num[v];

		comp[v] = Integer.MAX_VALUE / 2;
		stack.push(v);
		for (int i = 0; i < edges[v].size(); i++) {
			int w = (Integer) edges[v].get(i);
			if (comp[w] == -1) {
				tarjan(w, edges);
				lowlink[v] = Math.min(lowlink[w], lowlink[v]);
			} else if (comp[w] == Integer.MAX_VALUE / 2) {
				lowlink[v] = Math.min(num[w], lowlink[v]);
			}
		}
		if (lowlink[v] == num[v]) {
			while (true) {
				int x = stack.pop();
				comp[x] = no_comp;
				if (x == v) {
					no_comp++;
					break;
				}
			}

		}

	}

	public static void main(String[] args) throws Throwable {
		new Bicikli().solve();
	}

	public Bicikli() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}