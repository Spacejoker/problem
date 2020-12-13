import java.io.*;
import java.math.*;
import java.util.*;
import java.util.Map.Entry;

import static java.lang.Math.*;

public class advent13 {

	public static void main(String[] args) throws Exception {
		new advent13(true);
		new advent13(false);
	}

	public advent13(boolean test) throws Exception {
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

	private long solveFirst(String[] input) {
		int t0 = Integer.valueOf(input[0]);
		String[] split = input[1].split(",");
		int minMod = Integer.MAX_VALUE;
		int minIdx = 0;
		int multi = 0;
		for(int i =0 ; i< split.length; i++) {
			if (split[i].equals("x")) continue;
			int t = Integer.valueOf(split[i]);
			int wait = t - (t0%t);
			if (wait < minMod) {
				minMod = wait;
				minIdx = wait;
				multi = t;
			}
		}
		return minIdx  * multi;
	}

	class Cycle {
		long start, length;

		public Cycle(long start, long length) {
			this.start = start;
			this.length = length;
		}

		@Override
		public String toString() {
			return "Cycle [length=" + length + ", start=" + start + "]";
		}
	}

	Cycle getCycle(long t, long target, long offset) {
			long t0 = t;
			long next = target;
			long firstHit = 0,secondHit = 0;
			while(true) {
				if (next == t+offset) {
					if (firstHit > 0) {
						secondHit = t;
						return new Cycle(firstHit, secondHit - firstHit);
					} else {
						firstHit = t;
					}
				}

				if (next  < t +offset){
					 next += target;
				} else {
					t += t0;
				}
			}
	}

	Cycle reduceCycles(Cycle cycleA, Cycle cycleB) {
		long a = cycleA.start;
		long b = cycleB.start;
		b+= a/b*cycleB.length;
		long fst =0 ;
		while(true) {
			if (a == b) {
				if (fst > 0) {
					return new Cycle(fst, a - fst);
				} else {
					fst = a;
					a += cycleA.length;
				}
			}
			if (a < b) {
				a += cycleA.length;
			} else {
				b += cycleB.length;
			}
		}
	}

	private long solveSecond(String[] input) {
		int zzz = Integer.valueOf(input[0]);
		String[] split = input[1].split(",");
		int[] n = new int[split.length];
		for (int i= 0; i < split.length; i++) {
			if (split[i].equals("x")) continue;
			n[i] = Integer.valueOf(split[i]);
		}
		

		long t = n[0];
		long firstHit = 0;
		LinkedList<Cycle> cycles = new LinkedList<>();
		out: for (int i = 1 ; i < n.length; i++) {
			if (n[i] == 0)continue;
			cycles.push(getCycle(t,n[i], i));
		}
		while(cycles.size() > 1) {
			Collections.shuffle(cycles);
			System.out.println(cycles.size());
			Cycle a = cycles.pop();
			Cycle b = cycles.pop();
			cycles.push(reduceCycles(a, b));
		}
		return cycles.pop().start;
	}

	String[] readLines(boolean test) throws Exception {
		String filename = test ? "advent2020/test.txt" : "advent2020/input.txt";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		List<String> values = new ArrayList<>();
		String s;
		while ((s = in.readLine()) != null) {
			values.add(s);
		}
		return values.stream().toArray(String[]::new);
	}
}
