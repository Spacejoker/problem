package algos;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 */
public class TimeTableGraph {
	List<TimeTableEdge>[] edges;

	public TimeTableGraph(int n) {
		edges = new List[n];
		for (int i = 0; i < n; i++) {
			edges[i] = new ArrayList<TimeTableEdge>();
		}
	}

	public void addEdge(TimeTableEdge edge) {
		edges[edge.from].add(edge);
	}
}
