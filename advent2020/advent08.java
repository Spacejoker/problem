import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class advent08 {

	InputStreamReader inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);
	static final boolean TEST = false;

	public static void main(String[] args) throws Exception {
		new advent08();
	}

	public advent08() throws Exception {
		String[] input = readLines();

		long t0 = System.currentTimeMillis();
		long solutionA = solveFirst(input);
		long solutionB = solveSecond(input);
		long t1 = System.currentTimeMillis();

		System.out.printf("Answer 1: %d\nAnswer 2: %d\n", solutionA, solutionB);
		System.out.printf("Time: %dms", t1 - t0);
	}

	enum Operation {
		NOP, JMP, ACC;
	}

	class Instruction {
		Operation op;
		long value = 0;
		public Instruction(advent08.Operation op, long value) {
			this.op = op;
			this.value = value;
		}
	}

	private advent08.Instruction[] parseProgram(String[] input) {
		Instruction[] ins = new Instruction[input.length];
		for (int i =0 ; i< input.length; i++) {
			String[] tokens = input[i].split(" ");
			Operation op = Operation.NOP;
			switch(tokens[0]) {
				case "jmp":
				op = Operation.JMP;
				break;
				case "acc":
				op = Operation.ACC;
				break;
				case "nop":
				default:
				break;
			}
			ins[i] = new Instruction(op, Integer.valueOf(tokens[1]));
		}
		return ins;
	}

	private long solveFirst(String[] input) {
		int acc =0 ;
		int pc =0 ;
		Instruction[] program = parseProgram(input);

		boolean[]seen = new boolean[input.length];

		while(true) {
			if (seen[pc]) {break;}
			seen[pc] =true;

			Instruction l = program[pc];

			if (l.op == Operation.NOP) {
				pc ++;
				continue;
			}
			else if (l.op == Operation.JMP) {
				pc += l.value;
				continue;
			}
			else if (l.op == Operation.ACC) {
				pc ++;
				acc += l.value;
				continue;
			}

		}
		return acc;
	}

	private long solveSecond(String[] input) {
		for(int i =0 ;i < input.length; i++ ) {
			if (input[i].split(" ")[0].equals("nop")) {
				input[i] = input[i].replace("nop", "jmp");
				int res = tryRun(input);
				if (res != -1) {return res;}
				input[i] = input[i].replace("jmp", "nop");
			}
			if (input[i].split(" ")[0].equals("jmp")) {
				input[i] = input[i].replace("jmp", "nop");
				int res = tryRun(input);
				if (res != -1) {return res;}
				input[i] = input[i].replace("nop", "jmp");
			}

	}
		return -1;
	}

	private int tryRun(String[] input) {
		int pc = 0; int acc = 0;
		boolean[]seen = new boolean[input.length];
		while(true) {
			if (pc == input.length) {return acc;}
			if (pc > input.length) return -1;
			if (seen[pc]) {return -1;}
			seen[pc] =true;
			String[] split = input[pc].split(" ");
			if (split[0].equals("nop")) {
				pc ++;
				continue;
			}
			if (split[0].equals("jmp")) {
				pc += Integer.valueOf(split[1]);
				continue;
			}
			if (split[0].equals("acc")) {
				pc ++;
				acc += Integer.valueOf(split[1]);
				continue;
			}
		}
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
