package algos;

/**
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 */
public class TimeTableEdge {
	int from, to, weight, busStart, interval;

	public TimeTableEdge(int from, int to,int t0, int interval, int weight) {
		super();
		this.from = from;
		this.to = to;
		this.weight = weight;
		this.busStart = t0;
		this.interval = interval;
	}

	@Override
	public String toString() {
		return "Edge [from=" + from + ", to=" + to + ", weight=" + weight
				+ ", t0=" + busStart + ", interval=" + interval + "]";
	}
}

