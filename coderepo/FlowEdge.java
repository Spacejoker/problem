package algos;
/**
 * Used in flow-problems
 * 
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 */
class FlowEdge {
	int from, to;
	long capacity, flow;
	public FlowEdge backEdge;

	public FlowEdge(int from, int to) {
		super();
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		return "Edge [from=" + from + ", to=" + to + "]";
	}

}
