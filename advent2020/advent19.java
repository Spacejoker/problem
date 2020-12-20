import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class advent19 {

	public static void main(String[] args) throws Exception {
		new advent19(true);
		new advent19(false);
	}

	public advent19(boolean test) throws Exception {
		run(test);
	}

	private void run(boolean test) throws Exception {
		String[] input = readLines(test);
		long solutionA = solveFirst(input);
		long t0 = System.currentTimeMillis();
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms\n", t1 - t0);
	}



	class Node {
		boolean isTerm = false;
		char termVal;
		List<List<Integer>> branches = new ArrayList<>();

		public Node() {
		}

		public Node(boolean isTerm, char termVal) {
			this.isTerm = isTerm;
			this.termVal = termVal;
		}

		@Override
		public String toString() {
			return "Node [branches=" + branches + ", isTerm=" + isTerm + ", termVal=" + termVal + "]";
		}
		
	}
	private long solveFirst(String[] input) {
		int idx = 0;
		Node[] nodes = new Node[200];
		for(;;idx++){
			if (input[idx].length() == 0) break;
			String s  = input[idx];
			String[] split = s.split(":");
			int nodeIndex = Integer.valueOf(split[0]);
			Node node = new Node();
			boolean isTerm = false;
			if (split[1].contains("a")) {
				isTerm = true;
				node = new Node(true, 'a');
			}
			if (split[1].contains("b")) {
				isTerm = true;
				node = new Node(true, 'b');
			}
			if (isTerm) {
				nodes[nodeIndex] = node;
				continue;
			}
			String[] pipeSplit = split[1].split("\\|");
			for (String rule : pipeSplit) {
				String[] children = rule.trim().split(" ");
				List<Integer> childNodes = new ArrayList<>();
				for(int i = 0 ; i < children.length; i++) {
					childNodes.add(Integer.valueOf(children[i]));
				}
				node.branches.add(childNodes);
			}
			nodes[nodeIndex] = node;
		}
		int ans = 0;
		idx += 1;
		for (; idx<input.length; idx++){
			ans += eval(input[idx], nodes) ? 1 : 0;
		}
		return ans;
	}

	private boolean eval(String candidate, Node[] nodes) {
		List<Integer> rules = new ArrayList<>();
		rules.add(0);

		boolean ret = evalCandidate(candidate, nodes, rules, 0);
		System.out.println(candidate + ", " + ret);
		return ret;
	}

	private boolean evalCandidate(String candidate, advent19.Node[] nodes, List<Integer> rules, int idx) {
		if (rules.size() + idx > candidate.length()) return false;
		while(rules.size() > 0) {
			Node nextNode = nodes[rules.get(0)];
			rules.remove(0);
			if (nextNode.isTerm) {
				if (idx >= candidate.length()) return false;
				if (nextNode.termVal == candidate.charAt(idx)) {
					idx ++;
				} else {
					return false;
				}
			}
			if (nextNode.branches.size() == 1) {
				rules = Stream.concat(nextNode.branches.get(0).stream(), rules.stream()) .collect(Collectors.toList());
			} else if(nextNode.branches.size() > 1) {
				for(List<Integer> branch : nextNode.branches) {
					List<Integer> newRules = Stream.concat(branch.stream(), rules.stream()) .collect(Collectors.toList());
					if ( evalCandidate(candidate, nodes, newRules, idx)) {
						return true;
					}
				}
				return false;
			}
		}

		return idx == candidate.length();
	}

	private long solveSecond(String[] input) {
		return 0;
	}

	String[] readLines(boolean test) throws Exception {
		String filename = test ? "advent2020/test.txt" : "advent2020/input.txt";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		List<String> values = new ArrayList<>();
		String s;
		while ((s = in.readLine()) != null) {
			values.add(s);
		}
		in.close();
		return values.stream().toArray(String[]::new);
	}
}
