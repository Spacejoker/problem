import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class advent11 {

	public static void main(String[] args) throws Exception {
		new advent11(true);
		new advent11(false);
	}

	public advent11(boolean test) throws Exception {
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
		char[][] board = new char[input.length][input[0].length()];
		int w = input[0].length();
		int h = input.length;
		for( int i= 0 ; i< input.length; i++) {
			for (int j =0 ; j < input[0].length(); j++) {
				board[i][j] = input[i].charAt(j);
			}
		}

		while(true) {
			boolean modified = false;
			int totOccupied = 0;
			char[][] nextBoard = new char[input.length][input[0].length()];
			for( int i= 0 ; i< input.length; i++) {
				for (int j =0 ; j < input[0].length(); j++) {
					int numOcc = 0;
					for (int dx = -1; dx <= 1; dx++){
						for (int dy = -1; dy <= 1; dy++){
							if (dx == 0 && dy == 0) continue;
							int newx = j+dx;
							int newy = i+dy;
							if (newx >=0 && newx<w && newy >= 0 && newy <h) {
								char value = board[newy][newx];
								if (value == '#') numOcc ++;
							}
						}
					}
					if (board[i][j] == '#') totOccupied ++;
					if(board[i][j] == 'L' && numOcc == 0) {
						nextBoard[i][j] = '#';
						modified = true;
					} else if (board[i][j] == '#' && numOcc >= 4) {
						nextBoard[i][j] = 'L';
						modified = true;
					} else {
						nextBoard[i][j] = board[i][j];
					}
				}
			}
			board = nextBoard;
			if (!modified) return totOccupied;
		}
	}

	private long solveSecond(String[] input) {
		char[][] board = new char[input.length][input[0].length()];
		int w = input[0].length();
		int h = input.length;
		for( int i= 0 ; i< input.length; i++) {
			for (int j =0 ; j < input[0].length(); j++) {
				board[i][j] = input[i].charAt(j);
			}
		}

		while(true) {
			boolean modified = false;
			int totOccupied = 0;
			char[][] nextBoard = new char[input.length][input[0].length()];
			for( int i= 0 ; i< input.length; i++) {
				for (int j =0 ; j < input[0].length(); j++) {
					int numOcc = 0;
					for (int dx = -1; dx <= 1; dx++){
						for (int dy = -1; dy <= 1; dy++){
							if (dx == 0 && dy == 0) continue;
							int newx = j;
							int newy = i;
							while(true) {
								newx += dx;
								newy += dy;
								if (newx >=0 && newx<w && newy >= 0 && newy <h) {
									char value = board[newy][newx];
									if (value == '#') {
										numOcc ++;
										break;
									} else if (value == 'L') {
										break;
									}
								} else {
									break;
								}
							}
						}
					}
					if (board[i][j] == '#') totOccupied ++;
					if(board[i][j] == 'L' && numOcc == 0) {
						nextBoard[i][j] = '#';
						modified = true;
					} else if (board[i][j] == '#' && numOcc >= 5) {
						nextBoard[i][j] = 'L';
						modified = true;
					} else {
						nextBoard[i][j] = board[i][j];
					}
				}
			}
			board = nextBoard;
			if (!modified) {
				return totOccupied;
			}
		}
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
