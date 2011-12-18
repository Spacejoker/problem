package gcj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ChessBoards {
	
//	final String f = "test.in";
//	final String f = "C-small-practice.in";
	final String f = "C-large-practice.in";

	private BufferedReader in;
	private PrintWriter out;
	private StringTokenizer st;
	
	
	int rowCount;
	int columnCount;

	int[][] chessBoard;

	//debug
	static boolean debug = false;
	int squareProblemSize = 2;

	int[][] debugRes;
	int[][] debugResTwo;
	
	void solve() throws IOException {

		int largestSquare = 0;
		
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();

		rowCount = nextInt();
		columnCount = nextInt();

		chessBoard = new int[rowCount][columnCount];
		debugRes = new int[rowCount][columnCount];
		debugResTwo = new int[rowCount][columnCount];

		// Parse the hex
		for (int i = 0; i < rowCount; i++) {
			String next = next();

			for (int charIndex = 0; charIndex < next.length(); charIndex++) {

				int x = Integer.decode("0x" + next.charAt(charIndex));

				for (int exp = 0; exp < 4; exp++) {
					int expValue = (int) Math.pow(2, exp);
					int comparison = (x & expValue);

					if (comparison == expValue) {
						chessBoard[i][4 * charIndex + 3 - exp] = 1;
					} else {
						chessBoard[i][4 * charIndex + 3 - exp] = 0;
					} 	
				} 	
			} 	
		}

		for (int j = 0; j < rowCount; j++) {
			for (int i = 0; i < columnCount; i++) {
				debugResTwo[j][i] = chessBoard[j][i];
			}
		}
		
		//Implement a cache on biggest size, and only clean out if it is affected by the cut square. Maybe?
		//Now compute the largest square
		
		int[][] squareSizes = new int[rowCount][columnCount]; // with the upper left corner at this pos
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				squareSizes[i][j] = -1; //mark all for calculation
			}
		}
		
		while(true){
			
			boolean found = false;
			
			int count = 0;
			outer:
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < columnCount; j++) {
					if(chessBoard[i][j] < 2){ //do we have any uncut pieces?
						found = true;
						if(debug){
							count ++;							
						} else {
							break outer;
						}
					} 
				}
			}
			
			
			if(debug){
				System.out.println("remaining spots: " + count);	
			}
			
			if(!found){ // we are done
				break;
			}
			
			//find square sizes:
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < columnCount; j++) {
					if(squareSizes[i][j] == -1  && chessBoard[i][j] != 2){
						setSize(squareSizes, i, j);	
					}
				}
			}
			
			//get the max board and cut it out
			int bestX = 0;
			int bestY = 0;
			int bestSize = 0;
			for (int i = 0; i < rowCount; i++) {
				for (int j = 0; j < columnCount; j++) {
					if(squareSizes[i][j] > bestSize){
						
						bestSize = squareSizes[i][j];
						bestX = j;
						bestY = i;
						
						if(largestSquare < bestSize){ //for looping in output mode, dirty and just confusing
							largestSquare = bestSize;
						}
					}
				}
			}
			
//			System.out.println(bestSize);
			
			if(bestSize == 2){ //take out any isolated 2x2 squares if we are down to that
				int removedTwos = 0; 
//				System.out.println("found two at: " + bestY +", " +bestX);
				if(debug){
					printChessBoard(squareSizes);
					System.out.println("===");
					printChessBoard(chessBoard);
				}
				
				for (int i = 1; i < rowCount-1; i++) {
					for (int j = 1; j < columnCount-1; j++) {
						if(i == bestY && j == bestX){
							continue;
						}
						if(squareSizes[i][j] == 2 &&  //pallar inte göra ngt smart här
								squareSizes[i][j-1] < 2 && 
								squareSizes[i][j+1] < 2 && 
								squareSizes[i+1][j] < 2 && 
								squareSizes[i-1][j] < 2 && 
								squareSizes[i+1][j-1] < 2&& 
								squareSizes[i+1][j+1] < 2 &&
								squareSizes[i-1][j-1] < 2 &&
								squareSizes[i-1][j+1] < 2){
							
//							System.out.println("found two at: " + i +", " +j);
							
							squareSizes[i][j] = -1;
							squareSizes[i+1][j] = -1;
							squareSizes[i][j+1] = -1;
							squareSizes[i+1][j+1] = -1;
							chessBoard[i][j] = 2;
							chessBoard[i+1][j] = 2;
							chessBoard[i][j+1] = 2;
							chessBoard[i+1][j+1] = 2;
							
							removedTwos++;
							quickTwoRemoved ++;
							
						}
					}
				}
				
				if(debug){
					System.out.println("===");
					printChessBoard(squareSizes);
					System.out.println("===");
					printChessBoard(chessBoard);
				}
				
				addResult(2, result, removedTwos);
				
				if(debug && removedTwos > 0){
					System.out.println("shortcutremoved " + removedTwos + " twos! :)");
				}
			}

			//go through board up and left and mark stuff for recalculation
			for (int i = bestY + bestSize-1; i >= 0; i--) {
				for (int j = bestX + bestSize-1; j >= 0; j--) {
					if(squareSizes[i][j] + i >= bestY || squareSizes[i][j] + j >= bestX  || (j >= bestX && i >= bestY)){
						squareSizes[i][j] = -1;
					}
				}	
			}
			
			for (int i = bestY; i < bestY + bestSize; i++) {
				for (int j = bestX; j < bestX + bestSize; j++) {
					chessBoard[i][j] = 2;
					debugRes[i][j] = bestSize;
					if(bestSize != squareProblemSize){
						debugResTwo[i][j] = 9;
					}
				}
			}
			
			if(debug){
				int supercount = 0;
			
				for (int i = 1; i < rowCount-1; i++) {
					for (int j = 1; j < columnCount-1; j++) {
						if(squareSizes[i][j] == 2){
							supercount ++;
						}
					}
				}
				System.out.println("still got " + supercount + "twos.");
			}
			
			
			if(bestSize == 1){ // get all remaining 1s and sum them up, and mark -1
				int hits = 0;
				for (int i = 0; i < rowCount; i++) {
					for (int j = 0; j < columnCount; j++) {
						if(squareSizes[i][j] == 1){
							hits ++;
							squareSizes[i][j] = -1;
							chessBoard[i][j] = 2;
							quickOneRemoved ++;
						}
					}
				}
				result.put(1, hits);
				
			}
			addResult(bestSize, result, 1);
		}
		
		StringBuilder res = new StringBuilder();
		
		for (int i = largestSquare; i > 0; i--) {
			if(result.containsKey(i)){
				res.append(i + " " + result.get(i)  );
				if(i >1){
					res.append("\n");
				}
			}
		}
		
		if(debug){
			printChessBoard(debugResTwo);
			System.out.println("===");
			printChessBoard(debugRes);
		}
		
		out.println(Integer.valueOf(result.keySet().size()) + "\n" + res.toString());
		System.out.println(res);

	}

	private void addResult(int bestSize, Map<Integer, Integer> result, int diff) {
		if(result.containsKey(bestSize)){
			result.put(bestSize, result.get(bestSize) + diff);
		} else {
			result.put(bestSize, diff);
		}
	}

	private void printChessBoard(int[][] chessBoard2) {
		StringBuilder b = new StringBuilder();
		
		for (int i = 0; i < rowCount; i++) {
			b.delete(0, b.length());
			for (int j = 0; j < columnCount; j++) {
				b.append(chessBoard2[i][j] >= 0 ? chessBoard2[i][j] : "#");
				
			}
			System.out.println(b);
		}		
	}

	private void setSize(int[][] squareSizes, int i, int j) {
		int dim = 1;
		int square = 0;
		if(i > 0 && j > 0){
			if(squareSizes[i-1][j-1] > 1){ //DP!
				dim = squareSizes[i-1][j-1] -1;
				square = dim-1;
			}
		}
		
		for (; dim <= rowCount - i && dim <= columnCount - j; dim++) {
			if(verifySquare(i,j,dim)){
				square ++;
			} else {
				break;
			}
		}
		
		squareSizes[i][j] = square == 0? -1: square;
	}

	private boolean verifySquare(int i, int j, int dim) {
		
		boolean lastBit = chessBoard[i][j] == 1;
		boolean firstBit = lastBit;
		
		int chessVal = 0;
		
		for (int y = i; y < i+dim; y++) {
			for (int x = j; x < j+dim; x++) {
				chessVal = chessBoard[y][x];
				if( chessVal == 2 || (chessVal == 1) == !lastBit){
					return false;
				}
				//change bit:
				lastBit = !lastBit;
			}
			firstBit = ! firstBit;
			lastBit = firstBit;
		}
		return true;
	}
	
	static int quickTwoRemoved = 0;
	static int quickOneRemoved = 0;
	
	ChessBoards() throws IOException {
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
		new ChessBoards();
	}

}
