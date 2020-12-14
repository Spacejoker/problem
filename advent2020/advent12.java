import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class advent12 {

	public static void main(String[] args) throws Exception {
		new advent12(true);
		new advent12(false);
	}

	public advent12(boolean test) throws Exception {
		run(test);
	}

	private void run(boolean test) throws Exception {
		String[] input = readLines(test);
		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		double solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %f\n", solutionA, solutionB);
		System.out.printf("Time: %dms\n", t1 - t0);
	}

	private long solveFirst(String[] input) {
		int x =0, y=0;
		int[][] dir = new int[][]{{0,1},{-1,0},{0,-1},{1,0}};
		int curDir = 0;
		for(String s : input) {
			int value = Integer.valueOf(s.substring(1));
			switch(s.charAt(0)) {
				case 'N':
				y += value;
				break;
				case 'E':
				x += value;
				break;
				case 'S':
				y -= value;
				break;
				case 'W':
				x -= value;
				break;
				case 'F':
				x+= dir[curDir][1]*value;
				y+= dir[curDir][0]*value;
				break;
				case 'R':
				curDir += value/90;
				curDir %= 4;
				break;
				case 'L':
				curDir -= value/90;
				curDir %= 4;
				curDir += 4;
				curDir %= 4;
				break;
			}
		}
		return Math.abs(x) + Math.abs(y);
	}

	private double solveSecond(String[] input) {
		double x =0, y=0;
		double wpx =10, wpy = 1;
		for(String s : input) {
			int value = Integer.valueOf(s.substring(1));
			switch(s.charAt(0)) {
				case 'N':
				wpy += value;
				break;
				case 'E':
				wpx += value;
				break;
				case 'S':
				wpy -= value;
				break;
				case 'W':
				wpx -= value;
				break;
				case 'F':
				x+= value*wpx;
				y+= value*wpy;
				break;
				case 'R':
				value = 360-value;
				case 'L':
				double S = Math.sin(value*Math.PI/180);
				double C = Math.cos(value*Math.PI/180);
				double newx = wpx*C - wpy * S;
				double newy = wpx*S + wpy * C;
				wpx = newx;
				wpy=newy;
				break;
			}
		}
		return Math.abs(x) + Math.abs(y);

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
