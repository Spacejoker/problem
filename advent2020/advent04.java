import java.io.*;
import java.math.*;
import java.util.*;
import static java.lang.Math.*;

public class advent04 {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);
	static final boolean TEST = false;

	public static void main(String[] args) throws Exception {
		new advent04();
	}

	public advent04() throws Exception {
		String[] input = readLines();

		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}
	String[] keys = new String[]{"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};
	Set<String> keySet = new HashSet<>();

//, "cid"
	private long solveFirst(String[] input) {
		for(String k : keys){
			keySet.add(k);
		}
		int count = 0;
		Set<String> found = new HashSet<>();
		for(int i = 0; i < input.length; i++) {
			String s = input[i];
			if (s.length() == 0) {
				count += check(found);
				found.clear();
			}
			String[] tokens = s.split(" ");
			for (String token : tokens) {
				String[] parts = token.split(":");
				if (keySet.contains(parts[0])) {
					found.add(parts[0]);
				}
			}
		}
		return count;
	}

	private int check(Set<String> found) {
		return found.size() >= 7 ? 1 : 0;
	}

	private int checkStrict(Map<String, String> found) {
		boolean valid = true;
		if (found.keySet().size() != 7) {return 0;}
		Integer byr = parseValue(found.get("byr"));
		valid &= testByr(byr);
		Integer iyr = parseValue(found.get("iyr"));
		valid &= iyr >= 2010 && iyr <= 2020;
		valid &= checkEyr(parseValue(found.get("eyr")));
		valid &= checkHgt(found.get("hgt"));
		valid &= checkHcl(found.get("hcl"));
		valid &= checkEcl(found.get("ecl"));
		valid &= checkPid(found.get("pid"));

		return valid ? 1 : 0;
	}

	private boolean checkEyr(Integer eyr) {
		boolean valid = eyr >= 2020 && eyr <= 2030;
		System.out.println("eyr " + valid);
		return valid;
	}

	boolean checkHgt(String hgt) {
		boolean valid = true;
		String unit = hgt.substring(hgt.length()-2);
		System.out.println("unit" + unit);
		if (unit.equals("cm")) {
			Integer value = parseValue(hgt.substring(0, hgt.length() - 2));
			System.out.println("val" + value);
			valid &= value >= 150 && value <= 193;
		} else if (unit.equals("in")) {
			Integer value = parseValue(hgt.substring(0, hgt.length() - 2));
			System.out.println("val" + value);
			valid &= value >= 59 && value <= 76;
		} else {
			valid = false;
		}
		System.out.println("hgt " + valid);
		return valid;
	}

	private boolean testByr(Integer byr) {
		boolean valid =  byr >= 1920 && byr <= 2002;
		System.out.println("byr " + valid);
		return valid;
	}

	private boolean checkPid(String s) {
		if (s.length() != 9) return false;
		boolean valid = true;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) < '0' || s.charAt(i) > '9') valid = false;
		}
		System.out.println("pid " + valid);
		return valid;
	}

	private boolean checkEcl(String s) {
		boolean valid = s.equals("amb") || 
				s.equals("blu") || 
				s.equals("brn") || 
				s.equals("gry") || 
				s.equals("grn") || 
				s.equals("hzl") || 
				s.equals("oth");
		System.out.println("ecl " + valid);
		return valid;
	}

	boolean checkHcl(String s) {
		if (s.length() != 7) return false;
		boolean valid = true;
		valid &= s.charAt(0) == '#';
		for (int i = 1; i < s.length(); i++) {
			boolean isNumber = s.charAt(i) >= '0' && s.charAt(i) <= '9';
			boolean isChar = s.charAt(i) >= 'a' && s.charAt(i) <= 'f';
			if (!isNumber && !isChar) valid = false;
		}
		System.out.println("hcl " + valid);
		return valid;
	}

	Integer parseValue(String s) {
		boolean isNumber = s.length() > 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) < '0' || s.charAt(i) >'9') {
				isNumber = false;

			}
		}
		return isNumber ? Integer.valueOf(s) : 0;
	}

	private long solveSecond(String[] input) {
		for(String k : keys){
			keySet.add(k);
		}
		int count = 0;
		Map<String, String> found = new HashMap<>();
		for(int i = 0; i < input.length; i++) {
			String s = input[i];
			if (s.length() == 0) {
				count += checkStrict(found);
				found.clear();
			}
			String[] tokens = s.split(" ");
			for (String token : tokens) {
				String[] parts = token.split(":");
				if (keySet.contains(parts[0])) {
					found.put(parts[0],parts[1]);
				}
			}
		}
		return count;
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
