package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Jens Staahl
 */

public class Nikola {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		int n = io.getInt();
		
		PriorityQueue<Node> pq = new PriorityQueue<Node>(10000, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				if (o1.cost < o2.cost)
					return -1;
				if (o2.cost < o1.cost)
					return 1;
				return 0;
			}
		});

		int[] cost = new int[n+1];
		for (int i = 1; i <= n; i++) {
			cost[i] = io.getInt();
		}
		
		pq.offer(new Node(cost[2], 2, 1));
		int[][] visisted = new int[n+1][n+1];
		for (int i = 0; i < visisted.length; i++) {
			Arrays.fill(visisted[i], Integer.MAX_VALUE);
		}
		while(pq.size() > 0) {
			Node p = pq.poll();
//			System.out.println(p);
			if(visisted[p.i][p.steplen] <= p.cost)
				continue;
			if(p.i == n) {
				System.out.println(p.cost);
				return;
			}
			
			visisted[p.i][p.steplen] = p.cost;
			
			if( p.i + p.steplen +1 <= n) {
				int newpos = p.i + p.steplen +1 ;
				int newcost = p.cost + cost[newpos];
				pq.add(new Node(newcost, newpos, p.steplen+1));
			}
			
			if(p.i-p.steplen > 0) {
				int newpos = p.i - p.steplen;
				int newcost = p.cost + cost[newpos];
				pq.add(new Node(newcost, newpos, p.steplen));
			}
		}

//		io.flush();
	}

	public static void main(String[] args) throws Throwable {
		new Nikola().solve();
	}

	public Nikola() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;
}

class Node {
	int cost;
	int i;
	int steplen;

	public Node(int cost, int i, int steplen) {
		this.cost = cost;
		this.i = i;
		this.steplen = steplen;
	}

	@Override
	public String toString() {
		return "Node [cost=" + cost + ", i=" + i + ", steplen=" + steplen + "]";
	}
	
	

}
