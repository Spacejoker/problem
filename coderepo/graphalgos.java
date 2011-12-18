class GraphAlgorithms {
	public static long maximumFlow(Graph graph, int source, int destination){
		long totalFlow = 0;
		int vertexCount = graph.getSize();
		int[] nextEdge = new int[vertexCount];
		while (true) {
			long[] distance = edgeDistances(graph, source).distances;
			if(distance[destination] == Long.MAX_VALUE){
				break;
			}
			Arrays.fill(nextEdge,0);
			totalFlow += doFlow(graph, source, destination, Long.MAX_VALUE, distance, nextEdge);
		}
		return totalFlow;
	}

	private static long doFlow(Graph graph, int source, int destination, long flow, long[] distance, int[] nextEdge) {
		if (source == destination) {
			return flow;
		}
		if (flow == 0 || distance[source] == distance[destination]) {
			return 0;
		}
		List<Edge> incident = graph.getIncident(source);
		int incidentSize = incident.size();
		int totalPushed = 0;
		for (int i = nextEdge[source]; i < incidentSize; i++) {
			Edge edge = incident.get(i);
			int nextDestination = edge.getDestination();
			if (distance[nextDestination] != distance[source] + 1) {
				continue;
			}
			long pushed = doFlow(graph, nextDestination, destination, Math.min(flow, edge.getCapacity()), distance, nextEdge);
			if(pushed != 0){
				edge.pushFlow(pushed);
				flow -= pushed;
				totalPushed += pushed;
				if(flow ==0){
					nextEdge[source] = i;
					return totalPushed;
				}
			}
		}
		nextEdge[source] = incidentSize;
		return totalPushed;
	}

	
	private static DistanceResult edgeDistances(Graph graph, int source) {
		int size = graph.getSize();
		Queue<Integer> queue = new ArrayBlockingQueue<Integer>(size);
		boolean[] notReached = new boolean[size];
		Arrays.fill(notReached, true);
		long[] distance = new long[size];
		Edge[] last = new Edge[size];
		Arrays.fill(distance, Long.MAX_VALUE);
		distance[source] = 0;
		queue.add(source);
		notReached[source] = false;
		int iterationCount = 0;
		while (queue.size() > 0) {
			iterationCount++;
			if (iterationCount / size / size / size != 0) {
				return null;
			}
			int current = queue.poll();
			for (Edge edge : graph.getIncident(current)) {
				if (edge.getCapacity() == 0) {
					continue;
				}
				int next = edge.getDestination();
				long weight = 1;
				if (distance[next] > distance[current] + weight) {
					distance[next] = distance[current] + weight;
					last[next] = edge;
					if (notReached[next]) {
						notReached[next] = false;
						queue.add(next);
					}
				}
			}
		}
		return new DistanceResult(distance, last);
	}
	
	public static class DistanceResult {
		public final long[] distances;
		public final Edge[] last;
		
		public DistanceResult(long[] distances, Edge[] last) {
			this.distances = distances;
			this.last = last;
		}
	}
	

}


//model
class Graph {
	private final int size;
	private final List<Edge>[] edges;

	public Graph(int size) {
		this.size = size;
		edges = new List[size];
		for (int i = 0; i < size; i++) {
			edges[i] = new ArrayList<Edge>();
		}
	}

	public int getSize() {
		return size;
	}

	public List<Edge> getIncident(int vertex) {
		return edges[vertex];
	}

	public void add(Edge edge) {
		edges[edge.getSource()].add(edge);
		edge = edge.getReverseEdge();
		if (edge != null) {
			edges[edge.getSource()].add(edge);
		}
	}
}

interface Edge {
	public int getSource();

	public int getDestination();

	public long getCapacity();

	public void pushFlow(long flow);

	public Edge getReverseEdge();
}

class SimpleEdge implements Edge {

	protected final int source;
	protected final int destination;

	public SimpleEdge(int source, int destination) {
		this.source = source;
		this.destination = destination;
	}

	public int getSource() {
		return source;
	}

	public int getDestination() {
		return destination;
	}

	public long getCapacity() {
		return 0;
	}

	public void pushFlow(long flow) {
		throw new UnsupportedOperationException();
	}

	public Edge getReverseEdge() {
		return null;
	}
}

class FlowEdge extends SimpleEdge {
	private long capacity;
	private long flow = 0;
	private Edge reverseEdge;

	public FlowEdge(int source, int destination, long capacity) {
		super(source, destination);
		this.capacity = capacity;
		reverseEdge = new ReverseEdge();
	}

	public long getCapacity() {
		return capacity;
	}

	public void pushFlow(long flow) {
		if (flow > 0) {
			if (this.capacity < flow) {
				throw new IllegalArgumentException();
			}
		} else {
			if (this.flow < -flow) {
				throw new IllegalArgumentException();
			}
		}
		capacity -= flow;
		this.flow += flow;
	}

	public Edge getReverseEdge() {
		return reverseEdge;
	}

	private class ReverseEdge implements Edge {
		public int getSource() {
			return destination;
		}

		public int getDestination() {
			return source;
		}

		public long getCapacity() {
			return flow;
		}

		public void pushFlow(long flow) {
			FlowEdge.this.pushFlow(-flow);
		}

		public Edge getReverseEdge() {
			return FlowEdge.this;
		}

	}
}