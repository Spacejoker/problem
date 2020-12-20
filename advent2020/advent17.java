import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class advent17 {

	public static void main(String[] args) throws Exception {
		new advent17(true);
		new advent17(false);
	}

	public advent17(boolean test) throws Exception {
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
		// z, y, x
		boolean[][][] grid = new boolean[100][100][100];
		int offset = 50;
		int dim = 100;
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length(); j++) {
				boolean active = input[i].charAt(j) == '#';
				if (active) {
					grid[offset][i + offset][j + offset] = true;
				}
			}
		}

		// Step
		for (int step = 0; step < 6; step++) {
			int numActive = 0;
			for (int z = 1; z < dim - 1; z++) {
				for (int y = 1; y < dim - 1; y++) {
					for (int x = 1; x < dim - 1; x++) {
						boolean prevState = grid[z][y][x];
						if (prevState) numActive ++;
					}}}
					System.out.println("Step: " + step + ", num active: " + numActive);
			boolean[][][] nextGrid = new boolean[100][100][100];
			for (int z = 1; z < dim - 1; z++) {
				for (int y = 1; y < dim - 1; y++) {
					for (int x = 1; x < dim - 1; x++) {
						boolean prevState = grid[z][y][x];
						boolean newState = prevState;
						int count = 0;
						for (int dz = -1; dz <= 1; dz++) {
							for (int dy = -1; dy <= 1; dy++) {
								for (int dx = -1; dx <= 1; dx++) {
									if (dx == 0 && dy == 0 && dz == 0)
										continue;
									int newx = dx + x;
									int newy = dy + y;
									int newz = dz + z;
									if (grid[newz][newy][newx]) {
										count++;
									}
								}
							}
						}
						if (prevState){
							 newState = (count == 2 || count == 3);
						}
						
						if ( !prevState && count == 3) {
							newState = true;
						}
						nextGrid[z][y][x] = newState;
					}
				}
			}
			grid = nextGrid;
		}

		int ans = 0;
			for (int z = 1; z < dim - 1; z++) {
				for (int y = 1; y < dim - 1; y++) {
					for (int x = 1; x < dim - 1; x++) {
						boolean prevState = grid[z][y][x];
						if (prevState) ans ++;
					}}}

		return ans;
	}

	private long solveSecond(String[] input) {
		// z, y, x
		int dim = 20;
		boolean[][][][] grid = new boolean[dim][dim][dim][dim];
		int offset = dim/2;
		for (int i = 0; i < input.length; i++) {
			for (int j = 0; j < input[i].length(); j++) {
				boolean active = input[i].charAt(j) == '#';
				if (active) {
					grid[offset][offset][i + offset][j + offset] = true;
				}
			}
		}

		// Step
		for (int step = 0; step < 6; step++) {
			int numActive = 0;
			for (int z = 1; z < dim - 1; z++) {
				for (int y = 1; y < dim - 1; y++) {
					for (int x = 1; x < dim - 1; x++) {
					for (int w = 1; w < dim - 1; w++) {
						boolean prevState = grid[w][z][y][x];
						if (prevState) numActive ++;
					}}}}
					System.out.println("Step: " + step + ", num active: " + numActive);
			boolean[][][][] nextGrid = new boolean[dim][dim][dim][dim];
			for (int w = 1; w < dim - 1; w++) {
			for (int z = 1; z < dim - 1; z++) {
				for (int y = 1; y < dim - 1; y++) {
					for (int x = 1; x < dim - 1; x++) {
						boolean prevState = grid[w][z][y][x];
						boolean newState = prevState;
						int count = 0;
						for (int dw = -1; dw <= 1; dw++) {
						for (int dz = -1; dz <= 1; dz++) {
							for (int dy = -1; dy <= 1; dy++) {
								for (int dx = -1; dx <= 1; dx++) {
									if (dx == 0 && dy == 0 && dz == 0 && dw == 0)
										continue;
									int newx = dx + x;
									int newy = dy + y;
									int newz = dz + z;
									int neww = dw + w;
									if (grid[neww][newz][newy][newx]) {
										count++;
									}
								}
							}
						}
					}
						if (prevState){
							 newState = (count == 2 || count == 3);
						}
						
						if ( !prevState && count == 3) {
							newState = true;
						}
						nextGrid[w][z][y][x] = newState;
					}
				}
			}
		}
			grid = nextGrid;
		}

		int ans = 0;
			for (int w = 1; w < dim - 1; w++)
			for (int z = 1; z < dim - 1; z++) {
				for (int y = 1; y < dim - 1; y++) {
					for (int x = 1; x < dim - 1; x++) {
						boolean prevState = grid[w][z][y][x];
						if (prevState) ans ++;
					}}}

		return ans;

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
