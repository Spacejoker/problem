package algos;
/**
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 */
class DijkstraState {
	int from, to, edgeIdx;
	long cost;

	public DijkstraState(int from, int to, long cost, int edgeIdx) {
		super();
		this.from = from;
		this.to = to;
		this.cost = cost;
		this.edgeIdx = edgeIdx;
	}

	@Override
	public String toString() {
		return "State [from=" + from + ", to=" + to + ", edgeIdx=" + edgeIdx
				+ ", cost=" + cost + "]";
	}
}
