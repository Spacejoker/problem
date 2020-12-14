import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class advent02 {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);
	static final boolean TEST = false;

	public static void main(String[] args) throws Exception {
		new advent02();
	}

	public advent02() throws Exception {
		String[] input = readLines();

		long t0 = System.currentTimeMillis();
		long solutionA = Arrays.stream(input).filter(this::solveFirst).count();
		long solutionB = Arrays.stream(input).filter(this::solveSecond).count();
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}

	private boolean solveFirst(String line) {
			String[] split = line.split(" ");
			String rule = split[0];
			char matchChar = split[1].charAt(0);
			String password = split[2];

			Pattern numberRegex = Pattern.compile("\\d+");
			Matcher m = numberRegex.matcher(rule);
			m.find();
		  int min = Integer.valueOf(m.group());
			m.find();
			int max = Integer.valueOf(m.group());
			
			long charCount = password.chars().filter(c -> c == matchChar).count();
			return charCount <= max && charCount >= min;
	}

	private boolean solveSecond(String line) {
			String[] split = line.split(" ");
			String rule = split[0];
			char matchChar = split[1].charAt(0);
			String password = split[2];

			Pattern numberRegex = Pattern.compile("\\d+");
			Matcher m = numberRegex.matcher(rule);
			m.find();
			int aIdx = Integer.valueOf(m.group()) - 1;
			char fst = aIdx < password.length() ? password.charAt(aIdx) : '/';
			m.find();
			int bIdx = Integer.valueOf(m.group()) - 1;
			char snd = bIdx < password.length() ? password.charAt(bIdx) : '/';

			return fst != snd && fst == matchChar || snd == matchChar;
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
