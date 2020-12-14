import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

public class advent07 {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);
	static final boolean TEST = false;

	public static void main(String[] args) throws Exception {
		new advent07();
	}

	public advent07() throws Exception {
		String[] input = readLines();

		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}

	class Node {
		String name;
		List<Edge> edges = new ArrayList<>();
		long count = 1;

		public Node(String name, long count) {
			this.name = name;
			this.count = count;
		}
	}

	class Edge {
		String targetNode;
		int count;

		public Edge(String targetNode, int count) {
			this.targetNode = targetNode;
			this.count = count;
		}
	}

	private Map<String, advent07.Node> parseGraph(String[] input) {
		Map<String, Node> ret = new HashMap<>();
		for (String row : input) {
			String[] words = row.split(" ");
			String name = words[0] + " " + words[1];
			Node node = new Node(name, 1);
			ret.put(name, node);
			if (row.contains("contain no other bags")) {
				continue;
			}
			String[] chunks = row.split("contain")[1].split(",");
			for (String chunk : chunks) {
				String[] s = chunk.trim().split(" ");
				String bagName = new String(s[1] + " " + s[2]).trim();
				node.edges.add(new Edge(bagName, Integer.valueOf(s[0])));
			}
		}
		return ret;
	}

	private long solveFirst(String[] input) {
		Map<String, Node> nodes = parseGraph(input);
		int cnt = 0;
		outer: for (Entry<String, advent07.Node> entry : nodes.entrySet()) {
			Node node = entry.getValue();
			Stack<Node> stack = new Stack<>();
			Set<String> seen = new HashSet<>();
			stack.push(node);
			seen.add(node.name);
			while(stack.size() > 0) {
				Node next = stack.pop();
				if (next.name.equals("shiny gold")) {
					cnt ++;
					continue outer;
				}
				for (Edge expandEdge : next.edges) {
					if (!seen.contains(expandEdge.targetNode)) {
						seen.add(expandEdge.targetNode);
						Node pushNode = nodes.get(expandEdge.targetNode);
						stack.push(pushNode);
					}
				}
			}
		}
		return cnt-1;
	}

	private long solveSecond(String[] input) {
		Map<String, Node> nodes = parseGraph(input);
		Node root = nodes.get("shiny gold");
		int cnt = 0;
			Stack<Node> stack = new Stack<>();
			stack.push(root);
			while(stack.size() > 0) {
				Node next = stack.pop();
				for (Edge expandEdge : next.edges) {
					cnt += expandEdge.count * next.count;
					Node pushNode = new Node(expandEdge.targetNode, next.count * expandEdge.count);
					Node refNode = nodes.get(expandEdge.targetNode);
					pushNode.edges = refNode.edges;
					stack.push(pushNode);
				}
			}
		return cnt;

	}

	String[] readLines() throws Exception {

		String filename = TEST ? "advent2020/test.txt" : "advent2020/input.txt";
		in = new BufferedReader(new FileReader(filename));
		List<String> values = new ArrayList<>();
		String s;
		while ((s = in.readLine()) != null) {
			values.add(s);
		}
		return values.stream().toArray(String[]::new);
	}

}
