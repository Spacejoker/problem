import gcj.ChessBoards;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class NewDigger {

	int inf = Integer.MAX_VALUE - 10000;

	final String f = "test.in";
//	final String f = "B-small-practice.in";

	private BufferedReader in;
	private PrintWriter out;
	private StringTokenizer st;

	NewDigger() throws IOException {
		in = new BufferedReader(new FileReader(f));
		out = new PrintWriter(f + ".out");

		eat("");
		long start = System.currentTimeMillis();

		int tests = nextInt();
		for (int test = 0; test < tests; ++test) {
			System.out.println("Test #" + (test + 1));
			out.print("Case #" + (test + 1) + ": ");
			solve();
		}
		System.out.println("Time: " + (System.currentTimeMillis() - start));
		in.close();
		out.close();
	}

	private void solve() throws IOException {

		int rows = nextInt();
		int columns = nextInt();
		int fallMax = nextInt();

		// y, x, direction: 1 == right, -1 = left, nrOfDigs
		int state[][][][] = new int[rows][columns][columns][columns];
		boolean field[][] = new boolean[rows][columns];
		int falls[][] = new int[rows][columns];

		for (int i = 0; i < rows; i++) {
			String next = next();
			for (int j = 0; j < columns; j++) {
				field[i][j] = next.charAt(j) == '#';
				for (int j2 = 0; j2 < columns; j2++) {
					Arrays.fill(state[i][j][j2], inf);
					
				}
			}
		}
		state[0][0][0][0] = 0; //no digging done on first line anyway
		
		for (int j = 0; j < columns; j++) {
			int fall = 0;
			for (int i = rows-1; i >=0 ; i--) {
				if(fall > 0){
					falls[i][j] = fall;
				}
				if ( !field[i][j] ){
					fall ++;
				} else {
					fall = 0;
				}
			}
		}
		
		int globalBest = inf;

		for (int j = 0; j < columns; j++) {
			for (int i = 0; i < rows - 1; i++) {
				for (int startDig = 0; startDig< columns; startDig++) {
					for (int endDig = startDig; endDig < columns; endDig++) {

						if(state[i][j][startDig][j] > globalBest){
							continue;
						}
						
						int startPos = startDig;
						int endPos = endDig;

						//widen from free space
						while(startPos > 0 && !field[i][startPos-1]){
							startPos--;
						}
												
						while(endPos < columns-1 && !field[i][endPos+1]){
							endPos++;
							
						}
						
						if(i == 1 && j== 3 && startDig == 3 && endDig == 3){
							System.out.println("get it?");
						}
						
						if(endPos < columns -1 && (!field[i][endPos+1] || endDig >= endPos+1) && !field[i+1][endPos+1]){
							int f = falls[i][endPos+1];
							if(f <= fallMax){
								state[i+f][j][endPos+1][endPos+1] = Math.min(state[i+f][j][endPos+1][endPos+1], state[i][j][startDig][endDig]); // "free fall" :)
								if(i + f == rows -1 ){
									globalBest =  Math.min(state[i+f][j][endPos+1][endPos+1], globalBest);
								}
							}
						}
						
						if( startPos > 0 && !field[i][startPos-1] && !field[i+1][startPos-1]){
							int f = falls[i][startPos-1];
							if(f <= fallMax){
								state[i+f][j][startPos-1][startPos-1] = Math.min(state[i+f][j][startPos-1][startPos-1], state[i][j][startDig][endDig]); // "free fall" :)
								if(i + f == rows -1 ){
									globalBest =  Math.min(state[i+f][j][startPos-1][startPos-1], globalBest);
								}
							}
						}
						
						//try to fall in all spaces
						for (int k = startPos; k <= endPos; k++) {
							if(!field[i+1][k]){
								int f = falls[i+1][k]+1;
								if(f <= fallMax){
									state[i+f][j][k][k] = Math.min(state[i+f][j][k][k], state[i][j][startDig][endDig]); // "free fall" :)
									if(i + f == rows -1 ){
										globalBest = setGlobalBest(state, globalBest, j, i, k, f);
									}
								}
							}
							
						}
						
						
						for (int nextStartDig = startPos; nextStartDig < endPos; nextStartDig ++) {
							outer: for (int nextEndDig = nextStartDig; nextEndDig < endPos; nextEndDig++) {
								// try to do the move:
								for (int k = nextStartDig; k <= nextEndDig; k ++) {
									if (!field[i + 1][k]) {
										break outer; // not possible to dig if you fall instead
									}
								}
								
								if(i == 0 && nextStartDig == 2 && nextEndDig == 3){
									System.out.println("stop me");
								}
								
								int tmpHeight = i + 1;
								int newFalls = falls[i][nextStartDig];
								if(newFalls > 0){
									tmpHeight = newFalls;
								}
								
								//fall
								if (newFalls <= fallMax) {
									int newSuggestion = state[i][j][startDig][endDig] + nextEndDig-nextStartDig + 1;
									int oldBest = state[tmpHeight][nextStartDig][nextStartDig][nextEndDig]; 
									
									state[tmpHeight][nextStartDig][nextStartDig][nextEndDig] = Math.min(newSuggestion, oldBest);
									if(tmpHeight == rows -1 ){
										globalBest =  Math.min(state[tmpHeight][nextStartDig][nextStartDig][nextEndDig], globalBest);
									}
								}
								
								
//								tmpHeight = i + 1;
//								fall = 1;
//								
//								//fall
//								while (tmpHeight < rows - 1 && !field[tmpHeight + 1][newStopDig]) {
//									tmpHeight++;
//									fall++;
//								}
//								
//								if (fall <= fallMax) {
//									int newSuggestion = state[i][j][startDig][endDig] + newStopDig-nextStartDig + 1;
//									int oldBest = state[tmpHeight][newStopDig][nextStartDig][newStopDig]; 
//									
//									state[tmpHeight][newStopDig][nextStartDig][newStopDig] = Math.min(newSuggestion, oldBest);
//									if(tmpHeight == rows -1 ){
//										globalBest =  Math.min(state[tmpHeight][newStopDig][nextStartDig][newStopDig], globalBest);
//									}
//								}
							}
						}
					}
				}
			}
		}

		String res = "No";

		if (globalBest < inf) {
			res = "Yes: " + globalBest;
		}

		out.println(res);
		System.out.println(res);
	}

	private int setGlobalBest(int[][][][] state, int globalBest, int j, int i, int k, int f) {
		globalBest =  Math.min(state[i+f][j][k][k], globalBest);
		if(globalBest < 2){
			System.out.println("?");
		}
		return globalBest;
	}

	private void eat(String str) {
		st = new StringTokenizer(str);
	}

	String next() throws IOException {
		while (!st.hasMoreTokens()) {
			String line = in.readLine();
			if (line == null) {
				return null;
			}
			eat(line);
		}
		return st.nextToken();
	}

	int nextInt() throws IOException {
		return Integer.parseInt(next());
	}

	long nextLong() throws IOException {
		return Long.parseLong(next());
	}

	double nextDouble() throws IOException {
		return Double.parseDouble(next());
	}

	public static void main(String[] args) throws IOException {
		new NewDigger();
	}

}
