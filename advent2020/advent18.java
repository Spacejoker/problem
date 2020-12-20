import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class advent18 {

	public static void main(String[] args) throws Exception {
		new advent18(true);
		new advent18(false);
	}

	public advent18(boolean test) throws Exception {
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

	private long solveFirst(String[] input) {
		return 0;
//		long ans = 0;
//		for(String s : input) {
//			ans += rec(s);
//
//		}
//		return ans;
	}

	private long rec(String s) {
		int depth = 0;
		long value = 0;
		char lastop = '+';
		for(int i =0 ; i < s.length(); i++) {
			char c = s.charAt(i);
			long val = c - '0';
			boolean foundValue = false;
			if (c == ')') return value;
			if (c == '(') {
			  depth = 1;
				val = rec(s.substring(i+1));
				while(depth > 0) {
					i++;
					if (s.charAt(i) == ')') depth --;
					if (s.charAt(i) == '(') depth ++;
				}
			  foundValue = true;
			}
			if (c >= '0' && c <= '9') {
				foundValue = true;
			}
			if (foundValue && lastop == '+') value += val;
			if (foundValue && lastop == '*') value *= val;
			
			if (c == '+') lastop = '+';
			if (c == '*') lastop = '*';
		}
		return value;
	}

	private long solveSecond(String[] input) {
		long ans = 0;
		for(String s : input) {
			//for(int i= 0; i < 100; i++) 
			s = " " + s + " ";
			for(int i = 0; i < s.length(); i++) {
				if (s.charAt(i) == '+') {
					s = surroundParen(s, i);
					i++;
				}
			}
			ans += rec(s);
		}
		return ans;

	}

	private String surroundParen(String s, int idx) {
		int depth = 0;
		for (int i = idx+2; ;i++) {
			char c = s.charAt(i);
			if (c == '(') depth += 1;
			else if (c == ')'){
				depth -= 1;
			}
			if (depth ==0) {
				s = insertAt(')', s, i+1);
				break;
			}
		}
		depth = 0;
		for (int i = idx-2; ;i--) {
			char c = s.charAt(i);
			if (c == ')') depth += 1;
			else if (c == '(') depth -= 1;
			if (depth ==0) {
				s = insertAt('(', s, i);
				break;
			}
		}
		return s;
	}

	private String insertAt(char c, String s, int i) {
		return s.substring(0, i) + c + s.substring(i);
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
