import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class advent16 {

	public static void main(String[] args) throws Exception {
		new advent16(true);
		new advent16(false);
	}

	public advent16(boolean test) throws Exception {
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

	class Field {
		String name;
		int[] rangeA;
		int[] rangeB;

		public Field(String name, int[] rangeA, int[] rangeB) {
			this.name = name;
			this.rangeA = rangeA;
			this.rangeB = rangeB;
		}

		@Override
		public String toString() {
			return "Field [name=" + name + ", rangeA=" + Arrays.toString(rangeA) + ", rangeB=" + Arrays.toString(rangeB)
					+ "]";
		}
	}

	private long solveFirst(String[] input) {
		List<Field> rules = new ArrayList<>();
		int i =0;
		for(; ;i++) {
			String row = input[i];
			if (row.length() == 0) {
				i += 5;
				break;
			}
			String[] rowSplit = row.split(" ");
			String name = rowSplit[0];
			int[] rangeA = makeRange(rowSplit[1]);
			int[] rangeB = makeRange(rowSplit[3]);
			rules.add(new Field(name, rangeA, rangeB));
		}
		int ans = 0;
		for(;i < input.length; i++) {
			String[] vals = input[i].split(",");
			for(int k =0 ; k < vals.length; k++) {
				int val = Integer.valueOf(vals[k]);
				if (!isValid(val, rules)) {
					ans += val;
				}
			}
		}
		return ans;
	}

	private boolean isValid(int val, List<advent16.Field> rules) {
		for (Field f:  rules) {
			if (val >= f.rangeA[0] && val <= f.rangeA[1])return true;
			if (val >= f.rangeB[0] && val <= f.rangeB[1])return true;
		}
		return false;
	}

	private int[] makeRange(String string) {
		String[] split = string.split("-");
		int[] ret = new int[2];
		ret[0] = Integer.valueOf(split[0]);
		ret[1] = Integer.valueOf(split[1]);
		return ret;
	}

	private long solveSecond(String[] input) {
		List<Field> rules = new ArrayList<>();
		int I =0;
		for(; ;I++) {
			String row = input[I];
			if (row.length() == 0) {
				I += 2;
				break;
			}
			String[] rowSplit = row.split(" ");
			String name = rowSplit[0];
			int[] rangeA = makeRange(rowSplit[1]);
			int[] rangeB = makeRange(rowSplit[3]);
			rules.add(new Field(name, rangeA, rangeB));
		}
		String[] myTicket = input[I].split(",");
		int[] ticketVal = new int[myTicket.length];
		for (int i= 0; i < ticketVal.length; i++) {
			ticketVal[i] = Integer.valueOf(myTicket[i]);
		}
		I += 3;
		List<int[]> tickets = new ArrayList<>();
		for(;I < input.length; I++) {
			String[] vals = input[I].split(",");
			int[] ticket = new int[vals.length];
			boolean isValid = true;
			for(int k =0 ; k < vals.length; k++) {
				int val = Integer.valueOf(vals[k]);
				ticket[k] = val;
				if (!isValid(val, rules)) {
					isValid = false;
				}
			}
			if (isValid) tickets.add(ticket);
		}

		// if ticket 1, field 2, matches rule 3
		// Check each field, does field 2 match rule 3?

		boolean[][] bits = new boolean[rules.size()][rules.size()];
		for (int i =0 ; i < rules.size(); i++) {
			for (int k =0 ; k < rules.size(); k++) {
				bits[i][k] = true;
			}
		}

		for (int[] ticket : tickets) {
			for (int i = 0; i < ticket.length ; i++) {
				for (int k = 0; k < rules.size(); k++) { 
					Field rule = rules.get(k);
					if (!matchesRule(ticket[i], rule)){
						// field i always matches rule k
						bits[i][k] = false;
					}
				}
			}
		}

		long ans = 1;
		Map<Integer,Integer> used = new HashMap<>();
		boolean[] solved = new boolean[20];
		int cntFound = 0;
		out: while(cntFound < rules.size()) {
		for (int i =0 ; i < rules.size(); i++) {
			int lastRule = -1;
			int findCount = 0;
			for (int k =0 ; k < rules.size(); k++) {
				if(bits[i][k] && !solved[k]) {
					findCount ++;
					lastRule = k;
				}
			}
			if (findCount == 1) {
				used.put(i ,lastRule);
				cntFound ++;
				solved[lastRule] = true;
				System.out.println("Match! " + i + ", " + lastRule);
				if (lastRule < 6) ans *= ticketVal[i];
				continue out;
			}
		}
	}

		return ans;
	}

	private boolean matchesRule(int i, advent16.Field rule) {
			if (i >= rule.rangeA[0] && i <= rule.rangeA[1])return true;
			if (i >= rule.rangeB[0] && i <= rule.rangeB[1])return true;
		return false;
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
