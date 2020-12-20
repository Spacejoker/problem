import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class advent15 {

	public static void main(String[] args) throws Exception {
		new advent15(true);
		new advent15(false);
	}

	public advent15(boolean test) throws Exception {
		run(test);
	}

	private void run(boolean test) throws Exception {
		String[] input = readLines(test);
		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms\n", t1 - t0);
	}

	private long solveValue(String[] input, int target) {
		Set<Integer> spoken = new HashSet<>();
		String[] split = input[0].split(",");
		Map<Integer, List<Integer>> spokenTurn = new HashMap<>();
		int lastSpoken = 0;
		for (int i =0 ; ; i++) {
			if (i < split.length) {
				lastSpoken = Integer.valueOf(split[i]);
				List<Integer> nodes = new ArrayList<>();
				nodes.add(i);
				spokenTurn.put(lastSpoken, nodes);
				spoken.add(lastSpoken);
				continue;
			}

			List<Integer> nodes = spokenTurn.get(lastSpoken);
			lastSpoken = 0;
			if (nodes.size() > 1) {
				int prevOccurance = nodes.get(nodes.size() -2);
				lastSpoken = i - prevOccurance - 1;
			}
			if (!spokenTurn.containsKey(lastSpoken)) {
				spokenTurn.put(lastSpoken, new ArrayList<>());
			}
			spokenTurn.get(lastSpoken).add(i);
			spoken.add(lastSpoken);
			if (i == target) {return lastSpoken;}
		}
	}

	private long solveFirst(String[] input) {
		return solveValue(input, 2019);
	}

	private long solveSecond(String[] input) {
		return solveValue(input, 30000000-1);
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
