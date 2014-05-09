package algos;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * @author Jens Staahl
 */

public class Barica {

	class Node {
		int x, y, f;
		Node parent;
		int id;

		@Override
		public String toString() {
			return "Node [x=" + x + ", y=" + y + ", f=" + f + "]";
		}

		public Node(int x, int y, int f, int id) {
			super();
			this.x = x;
			this.y = y;
			this.f = f;
			this.id = id;
		}

	}

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);

		int n = io.getInt();
		int k = io.getInt();

		List[] rows = new List[100001];
		Node start = null, end = null;
		for (int i = 0; i < n; i++) {
			int x = io.getInt(), y = io.getInt(), f = io.getInt();
			if (i == 0) {
				start = new Node(x, y, f, i + 1);
				continue;
			} else {
				if (x < start.x || y < start.y) {
					continue;
				}
			}
			if (i == n - 1) {
				end = new Node(x, y, f, i + 1);
			}
			if (rows[y] == null) {
				rows[y] = new ArrayList();
			}
			Node node = new Node(x, y, f, i + 1);
			if (i == n - 1) {
				node = end;
			}
			rows[y].add(node);
		}
		Comparator<Node> nx = new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if (o1.x < o2.x) {
					return -1;
				}
				return 1;
			}
		};
		for (int i = 0; i < rows.length; i++) {
			if (rows[i] == null) {
				continue;
			}
			Collections.sort(rows[i], nx);
		}

		int[] rowdp = new int[100001];
		int[] coldp = new int[100001];
		Arrays.fill(rowdp, -Integer.MAX_VALUE / 2);
		Arrays.fill(coldp, -Integer.MAX_VALUE / 2);
		rowdp[start.y] = start.f;
		coldp[start.x] = start.f;
		Node[] rowparent = new Node[100001];
		Node[] colparent = new Node[100001];
		colparent[start.x] = start;
		rowparent[start.y] = start;
		int ans = 0;
		for (int y = start.y; y <= end.y; y++) {
			List list = rows[y];
			if (list == null) {
				continue;
			}
			for (int i = 0; i < list.size(); i++) {
				Node node = (Node) list.get(i);
				if (-k + rowdp[y] >= 0 || coldp[node.x] - k >= 0) {

					boolean isrow = false;
					if (rowdp[y] > coldp[node.x]) {
						isrow = true;
					}
					if (isrow) {
						node.parent = rowparent[y];
					} else {
						node.parent = colparent[node.x];
					}
					int newval = Math.max(rowdp[y], coldp[node.x]) - k + node.f;
//					System.out.println(node + ": " + newval);
					if(node.x == end.x && node.y == end.y){
						ans = newval;
					}
					if (newval > rowdp[y]) {
						rowdp[y] = newval;
						rowparent[y] = node;
					}
					if (newval > coldp[node.x]) {
						coldp[node.x] = newval;
						colparent[node.x] = node;
					}

				}
			}
		}

		io.write(ans + "\n");
		Stack<String> ss = new Stack();
		while (true) {
			ss.push(end.x + " " + end.y + "\n");
			end = end.parent;
			if (end == null) {
				break;
			}
		}
		io.write(ss.size() + "\n");
		while (ss.size() > 0) {
			io.write(ss.pop());
		}

		io.flush();
	}

	public static void main(String[] args) throws Throwable {
		new Barica().solve();
	}

	public Barica() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}