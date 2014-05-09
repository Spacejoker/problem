package algos;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 */

// Handles standard dijkstra algorithm with time table constraints, including the construction problem
// result is stored in dist and parent. All work done in constructor. 
public class DijkstraTimeTable {
	int[] parent;
	long[] dist;

	public DijkstraTimeTable(TimeTableGraph g, int start) {
		PriorityQueue<DijkstraState> pq = new PriorityQueue<DijkstraState>(10000, new Comparator<DijkstraState>() {
			@Override
			public int compare(DijkstraState o1, DijkstraState o2) {
				if(o1.cost < o2.cost){
					return -1;
				}
				if(o2.cost < o1.cost){
					return 1;
				}
				return 0;
			}
		});

		int N = g.edges.length;
		dist = new long[N];
		Arrays.fill(dist, Long.MAX_VALUE/2);
		dist[start] = 0;
		parent = new int[N];

		for (int i = 0; i < g.edges[start].size(); i++) {
			TimeTableEdge s = (TimeTableEdge) g.edges[start].get(i);
			pq.add(new DijkstraState(start, s.to, s.weight + s.busStart, i));
		}

		while(pq.size() > 0){
			DijkstraState next = pq.poll();
			List<TimeTableEdge> e = g.edges[next.from];

			int newNode = next.to;
			if(next.cost < dist[newNode]){

				parent[newNode] = next.from;
				dist[newNode] = next.cost; 

				for (int i = 0; i < g.edges[newNode].size(); i++) {
					TimeTableEdge ep = (TimeTableEdge) g.edges[newNode].get(i);
					if(ep.interval == 0 && dist[newNode] > ep.busStart){
						continue;
					}

					long leaveTime = ep.busStart;
					if(dist[newNode] > ep.busStart){
						long waitTime = (dist[newNode] - ep.busStart) % ep.interval;
						waitTime = ep.interval - waitTime;
						waitTime %= ep.interval;
						leaveTime = dist[newNode] + waitTime;
					}
					if(ep.weight + leaveTime < dist[ep.to]){
						pq.offer(new DijkstraState(newNode, ep.to, ep.weight + leaveTime , i));
					}
				}
			}
		}
	}

	// Return the distance from source to i
	public long dist(int i) {
		return dist[i];
	}
}

