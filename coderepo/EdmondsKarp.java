package algos;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;



/**
 * Class to handle the flow problem, se constructor for details.
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 *
 */
class EdmondsKarp {

	/**
	 * After the construcor is run all the Edges will be updated with new flow and capacity.
	 * Note that capacity is updated and can be interpreted as remaining capacity.
	 * The algorithm assumes a directed graph.
	 * 
	 * @param V number of vertices in the graph
	 * @param source just an int
	 * @param sink just an int
	 * @param edges an array of Lists, preferrable ArrayLists, each containing a collection of FlowEdges.
	 */

	int V, source, sink;
	List[] edges;
	List<Integer> mincutEdges;

	public EdmondsKarp(int V, int source, int sink, List[] edges) {
		this.V =V;
		this.source = source;
		this.sink = sink;
		this.edges = edges;

		// keep track of back edge
		for (int i = 0; i < edges.length; i++) {
			for (int j = 0; j < edges[i].size(); j++) {
				FlowEdge e = (FlowEdge) edges[i].get(j);
				if (e.backEdge != null) {
					continue;
				}
				for (int k = 0; k < edges[e.to].size(); k++) {
					FlowEdge other = (FlowEdge) edges[e.to].get(k);
					if (i == other.to) {
						e.backEdge = other;
						other.backEdge = e;
					}
				}
			}
		}

		boolean[] visited = new boolean[V + 1];
		int[] parent = new int[V + 1];
		int[] edgeNr = new int[V + 1];
		long[] residual = new long[V + 1];

		while (true) {
			// find a path in the residual graph
			Arrays.fill(parent, -1);
			Arrays.fill(visited, false);
			Arrays.fill(residual, 0);

			LinkedList<Integer> queue = new LinkedList<Integer>();
			// push source node in list
			queue.offer(source);
			visited[source] = true;

			// bfs and try to reach sink
			boolean reachedSink = false;
			while (queue.size() > 0) {
				Integer poll = queue.poll();
				if (poll == sink) {
					reachedSink = true;
					break;
				}
				for (int i = 0; i < edges[poll].size(); i++) {
					FlowEdge edgeToFollow = (FlowEdge) edges[poll].get(i);
					if (edgeToFollow.capacity == 0L) {
						continue;
					}
					if (edgeToFollow.capacity < 0) {
						throw new RuntimeException();
					}
					int to = edgeToFollow.to;

					if (!visited[to] && edgeToFollow.capacity > 0) {
						residual[to] = edgeToFollow.capacity;
						parent[to] = poll;
						visited[to] = true;
						queue.offer(to);
						edgeNr[to] = i; // save which edge that was used!
					}
				}
			}

			if (!reachedSink) {
				break;
			}

			// use parentarray to check for min-value in residual graph
			int cur = sink;
			long minResidual = Long.MAX_VALUE / 2;
			while (cur != source && cur != -1) { // -1 == parent of source = not in graph
				int par = parent[cur];
				minResidual = Math.min(residual[cur], minResidual);
				cur = par;
			}

			// change the flow along the path
			cur = sink;
			while (cur != source) {
				int par = parent[cur];
				FlowEdge modEdge = (FlowEdge) edges[par].get(edgeNr[cur]);

				// cf[u,v]:=c[u,v] - f[u,v]; cf[v,u]:=c[v,u] - f[v,u]
				FlowEdge backEdge = modEdge.backEdge;

				if (backEdge == null) { // create it
					backEdge = new FlowEdge(modEdge.to, modEdge.from);
					backEdge.backEdge = modEdge;
					modEdge.backEdge = backEdge;
					edges[backEdge.from].add(backEdge);
				}

				modEdge.flow += minResidual;
				modEdge.capacity -= minResidual;
				backEdge.capacity += minResidual;
				backEdge.flow -= minResidual;

				cur = par;
			}
		}
	}
	public List<Integer> getMinimumCut(){
		mincutEdges = new ArrayList<Integer>();
		
		boolean[] vis = new boolean[V];
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(source);
		vis[source] = true;
		while(stack.size() >  0){
			Integer pop = stack.pop();
			List newEdges = edges[pop];
			for (int i = 0; i < newEdges.size(); i++) {
				FlowEdge ee = (FlowEdge) newEdges.get(i);
				if(ee.capacity > 0 && !vis[ee.to]){
					vis[ee.to] = true;
					stack.push(ee.to);
				}
			}
		}

		for (int i = 0; i < vis.length; i++) {
			if(vis[i]){
				mincutEdges.add(i);
			}
		}
		return mincutEdges;
	}
}
