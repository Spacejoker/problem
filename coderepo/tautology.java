package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Stack;

/**
 * @author Jens Staahl
 */

public class tautology {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		while (true) {
			String s = io.getWord();
			if (s.equals("0")) {
				out.flush();
				return;
			}
			if(solveIt(s)){
				System.out.println("tautology");
			} else {
				System.out.println("not");
			}
		}
	}

	private boolean solveIt(String s) {
		for (int i = 0; i < 1 << 5; i++) {
			boolean[] val = new boolean[5];
			for (int j = 0; j < 5; j++) {
				if(( i  & (1 << j)) > 0){
					val[j] = true;
				} else {
					val[j] = false;
				}
			}
			if(!eval(val, s)){
				return false;
			}
		}
		return true;
	}

	private boolean eval(boolean[] val, String s) {
		boolean[] point = new boolean[26];
		point['p' - 'a'] = val[0];
		point['q' - 'a'] = val[1];
		point['r' - 'a'] = val[2];
		point['s' - 'a'] = val[3];
		point['t' - 'a'] = val[4];
		
		Stack<Boolean> stack = new Stack();
		for (int i = s.length()-1; i >= 0 ; i --){
			char c = s.charAt(i);
			if("pqrts".indexOf(c) != -1){
				stack.push(point[c-'a']);
			}
			if( c == 'K'){
				Boolean a = stack.pop();
				Boolean b = stack.pop();
				stack.push(a && b);
			}
			if(c == 'A'){
				Boolean a = stack.pop();
				Boolean b = stack.pop();
				stack.push(a || b);
			}
			if(c == 'N'){
				Boolean a = stack.pop();
				stack.push(!a);
			}
			if(c == 'C'){
				Boolean a = stack.pop();
				Boolean b = stack.pop();
				if(a && !b){
					stack.push(false);
				} else {
					stack.push(true);
				}
			}
			if(c == 'E'){
				Boolean a = stack.pop();
				Boolean b = stack.pop();
				stack.push(a == b);
			}
		}
		return stack.pop();
	}

	public static void main(String[] args) throws Throwable {
		new tautology().solve();
	}

	public tautology() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}