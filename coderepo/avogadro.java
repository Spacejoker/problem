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

public class avogadro {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	class Node {
		int a, b, c;

		@Override
		public String toString() {
			return "Node [a=" + a + ", b=" + b + ", c=" + c + "]";
		}

		public Node(int a, int b, int c) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}

	}

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		long t0 = -System.currentTimeMillis();
		int n = io.getInt();
		int[] midcnt = new int[n];
		int[] botcnt = new int[n];
		int[] topPos = new int[n];
		List[] midPos = new ArrayList[n];
		List[] botPos = new ArrayList[n];
		int[][] nodes = new int[n][3];

		for (int i = 0; i < n; i++) {
			midPos[i] = new ArrayList();
			botPos[i] = new ArrayList();
		}
		for (int i = 0; i < n; i++) {
			int v = io.getInt() - 1;
			topPos[v] = i;//.add(i);
			nodes[i][0] = v;
		}
		for (int i = 0; i < n; i++) {
			int num = io.getInt() - 1;
			midPos[num].add(i);
			midcnt[num]++;
			nodes[i][1] = num;
		}
		for (int i = 0; i < n; i++) {
			int num = io.getInt() - 1;
			botPos[num].add(i);
			botcnt[num]++;
			nodes[i][2] = num;
		}
		Stack<Integer> todo = new Stack();
		boolean[] in = new boolean[n];
		Arrays.fill(in, true);
		for (int i = 0; i < n; i++) {
			if (botcnt[i] < 1 || midcnt[i] < 1) {
				todo.add(i);
				in[i] = false;
			}
		}
		boolean[] columnDone = new boolean[n];
		while (todo.size() > 0) {
			Integer pop = todo.pop();
			List<Integer> colrm = new ArrayList();
			colrm.add(topPos[pop]);
			for (int i = 0; i < midPos[pop].size(); i++) {
				Integer c = (Integer) midPos[pop].get(i);
				colrm.add(c);
			}
			for (int i = 0; i < botPos[pop].size(); i++) {
				Integer c = (Integer) botPos[pop].get(i);
				colrm.add(c);
			}
			for (int i = 0; i < colrm.size(); i++) {
				Integer rm = colrm.get(i);
				if(columnDone[rm]){
					continue;
				}
				columnDone[rm] = true;
				int[] js = nodes[rm];
				midcnt[js[1]]--;
				if (midcnt[js[1]] < 1 && in[js[1]]) {
					todo.add(js[1]);
					in[js[1]] = false;
				}
				botcnt[js[2]]--;
				if (botcnt[js[2]] < 1 && in[js[2]]) {
					todo.add(js[2]);
					in[js[2]] = false;
				}
				if(in[js[0]]){
					in[js[0]] = false;
					todo.add(js[0]);
				}
			}
		}
		int ans = 0;
		for (int i = 0; i < in.length; i++) {
			if(!in[i]){
				ans ++;
			}
		}
		if(test){
			System.out.println(System.currentTimeMillis() + t0);
		}
		System.out.println(ans);
	}

	public static void main(String[] args) throws Throwable {
		new avogadro().solve();
	}

	public avogadro() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}