import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class advent20 {

	public static void main(String[] args) throws Exception {
		new advent20(true);
		new advent20(false);
	}

	public advent20(boolean test) throws Exception {
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

	class Tile {
		int id;
		String[] c = new String[10];

		public Tile(String[] c, int id) {
			this.c = c;
			this.id = id;
		}

		@Override
		public String toString() {
			return "Tile [id=" + id + ", c=" + Arrays.toString(c) + ", id=" + id + "]";
		}

	}

	private long solveFirst(String[] input) {
		int len = (input.length + 3) / 12;
		Tile[] tiles = new Tile[len];
		Map<Integer, Tile> tileMap = new HashMap<>();
		// 3x3 or 12x12
		for (int i = 0; i < len; i++) {
			int id = Integer.valueOf(input[i * 12].split(" ")[1].split(":")[0]);
			String[] rows = new String[10];
			for (int j = 1; j < 11; j++) {
				int row = i * 12 + j;
				rows[j - 1] = input[row];
			}
			tiles[i] = new Tile(rows, id);
			tileMap.put(id, tiles[i]);
		}

		long ans = 1;
		for (int i = 0; i < len; i++) {
			Tile tile = tiles[i];
			Set<Direction> directionMatch = new HashSet<>();
			for (int j = 0; j < len; j++) {
				if (i == j)
					continue;
				for (Direction d : Direction.values()) {
					if (matchesAny(getString(tile, d), tiles[j])) {
						directionMatch.add(d);
					}
				}
			}
			if (directionMatch.size() == 2) {
				ans *= tile.id;
			}
		}
		return ans;
	}

	private boolean matchesAny(String root, Tile other) {
		String leftCand = getString(other, Direction.LEFT);
		String rightCand = getString(other, Direction.RIGHT);
		String upCand = getString(other, Direction.UP);
		String downCand = getString(other, Direction.DOWN);
		Set<String> options = new HashSet<>();
		options.add(leftCand);
		options.add(new StringBuffer(leftCand).reverse().toString());
		options.add(rightCand);
		options.add(new StringBuffer(rightCand).reverse().toString());
		options.add(upCand);
		options.add(new StringBuffer(upCand).reverse().toString());
		options.add(downCand);
		options.add(new StringBuffer(downCand).reverse().toString());
		return options.contains(root);
	}

	private String getString(Tile tile, Direction side) {
		String ans = "";
		if (side == Direction.RIGHT) {
			for (int i = 0; i < 10; i++) {
				ans += tile.c[i].charAt(9);
			}
		}
		if (side == Direction.LEFT) {
			for (int i = 9; i >= 0; i--) {
				ans += tile.c[i].charAt(0);
			}
		}
		if (side == Direction.UP) {
			for (int i = 0; i < 10; i++) {
				ans += tile.c[0].charAt(i);
			}
		}
		if (side == Direction.DOWN) {
			for (int i = 9; i >= 0; i--) {
				ans += tile.c[9].charAt(i);
			}
		}
		return ans;
	}

	enum Direction {
		UP, RIGHT, DOWN, LEFT;

	}

	Tile[] tiles;

	private long solveSecond(String[] input) {
		Tile[][] grid = new Tile[12][12];
		int len = (input.length + 3) / 12;
		tiles = new Tile[len];
		Map<Integer, Tile> tileMap = new HashMap<>();
		// 3x3 or 12x12
		for (int i = 0; i < len; i++) {
			int id = Integer.valueOf(input[i * 12].split(" ")[1].split(":")[0]);
			String[] rows = new String[10];
			for (int j = 1; j < 11; j++) {
				int row = i * 12 + j;
				rows[j - 1] = input[row];
			}
			tiles[i] = new Tile(rows, id);
			tileMap.put(id, tiles[i]);
		}

		// 2857 is one corner:
		grid[0][0] = tileMap.get(2857);
		if (grid[0][0] == null)
			grid[0][0] = tileMap.get(1951);
		grid[0][0].c = rotate(rotate(mirror(grid[0][0].c)));
		for (int x0 = 1; x0 < Math.sqrt(len); x0++) {
			Tile root = grid[0][x0 - 1];
			for (int c = 0; c < tiles.length; c++) {
				if (tiles[c].id == root.id)
					continue;
				if (matchesNext(root, tiles[c])) {
					grid[0][x0] = tiles[c];
				}
			}
		}

		for (int x0 = 0; x0 < Math.sqrt(len); x0++) {
			for (int y0 = 1; y0 < Math.sqrt(len); y0++) {
				Tile root = grid[y0 - 1][x0];
				for (int c = 0; c < tiles.length; c++) {
					if (tiles[c].id == root.id)
						continue;
					if (matchesDown(root, tiles[c])) {
						grid[y0][x0] = tiles[c];
					}
				}
			}
		}
		int sideLen = (int) (Math.sqrt(len) + 0.0001);
		String[] sea = new String[sideLen * 8];
		for (int i = 0; i < sea.length; i++)
			sea[i] = "";
		for (int i = 0; i < sideLen; i++) {
			for (int j = 0; j < sideLen; j++) {
				for (int x = 0; x < 8; x++) {
					for (int y = 0; y < 8; y++) {
						sea[i * 8 + y] += grid[i][j].c[y + 1].charAt(x + 1);
					}
				}
			}
		}
		List<Integer> ans = new ArrayList<>();
		ans.add(searchSea(sea));
		ans.add(searchSea(rotate(sea)));
		ans.add(searchSea(rotate(rotate(sea))));
		ans.add(searchSea(rotate(rotate(rotate(sea)))));
		ans.add(searchSea(mirror(sea)));
		ans.add(searchSea(rotate(mirror(sea))));
		ans.add(searchSea(rotate(rotate(mirror(sea)))));
		ans.add(searchSea(rotate(rotate(rotate(mirror(sea))))));
		Collections.sort(ans);

		return ans.get(0);
	}

	int searchSea(String[] sea) {
		boolean[][] matched = new boolean[sea.length][sea[0].length()];
		for (int i = 0; i < sea.length; i++) {
			for (int j = 0; j < sea[0].length(); j++) {
				if (sea[i].charAt(j) == '#')
					matched[i][j] = true;
			}
		}

		String[] monster = new String[] { "                  # ", "#    ##    ##    ###", " #  #  #  #  #  #   " };
		int monsterLen = monster[0].length();
		for (int startx = 0; startx <= sea[0].length() - monsterLen; startx++) {
			for (int starty = 0; starty <= sea.length - 3; starty++) {
				boolean fullMatch = true;
				for (int i = 0; i < monster.length; i++) {
					for (int j = 0; j < monster[0].length(); j++) {
						char monsterC = monster[i].charAt(j);
						int seaX = startx + j;
						int seaY = starty + i;
						if (monsterC == '#' && sea[seaY].charAt(seaX) != '#') {
							fullMatch = false;
						}
					}
				}
				if (fullMatch) {
					for (int i = 0; i < monster.length; i++) {
						for (int j = 0; j < monster[0].length(); j++) {
							char monsterC = monster[i].charAt(j);
							int seaX = startx + j;
							int seaY = starty + i;
							if (monsterC == '#') {
								matched[seaY][seaX] = false;
							}
						}
					}
				}
			}
		}

		int ans = 0;
		for (int i = 0; i < sea.length; i++) {
			for (int j = 0; j < sea[0].length(); j++) {
				if (matched[i][j])
					ans++;
			}
		}
		return ans;
	}

	private boolean matchesDown(advent20.Tile root, advent20.Tile tile) {
		String targetStr = "";
		for (int i = 0; i < 10; i++) {
			targetStr += root.c[9].charAt(i);
		}
		String[][] cands = new String[][] { tile.c, rotate(tile.c), rotate(rotate(tile.c)), rotate(rotate(rotate(tile.c))),
				mirror(tile.c), rotate(mirror(tile.c)), rotate(rotate(mirror(tile.c))),
				rotate(rotate(rotate(mirror(tile.c)))) };

		for (String[] cand : cands) {
			String topSide = "";
			for (int i = 0; i < 10; i++) {
				topSide += cand[0].charAt(i);
			}
			if (topSide.equals(targetStr)) {
				tile.c = cand;
				return true;
			}
		}
		return false;

	}

	// If tile can be rotated/mirrored to match root right side
	private boolean matchesNext(advent20.Tile root, advent20.Tile tile) {
		String targetStr = "";
		for (int i = 0; i < 10; i++) {
			targetStr += root.c[i].charAt(9);
		}
		String[][] cands = new String[][] { tile.c, rotate(tile.c), rotate(rotate(tile.c)), rotate(rotate(rotate(tile.c))),
				mirror(tile.c), rotate(mirror(tile.c)), rotate(rotate(mirror(tile.c))),
				rotate(rotate(rotate(mirror(tile.c)))) };

		for (String[] cand : cands) {
			String leftSide = "";
			for (int i = 0; i < 10; i++) {
				leftSide += cand[i].charAt(0);
			}
			if (leftSide.equals(targetStr)) {
				tile.c = cand;
				return true;
			}
		}
		return false;
	}

	private String[] mirror(String[] c) {
		String[] ret = new String[c.length];
		for (int i = 0; i < c.length; i++) {
			ret[i] = new StringBuffer(c[i]).reverse().toString();
		}
		return ret;
	}

	private String[] rotate(String[] c) {
		char[][] a = new char[c.length][c.length];
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c.length; j++) {
				a[i][j] = c[i].charAt(j);
			}
    }
    
		int N = c.length;
		for (int i = 0; i < N / 2; i++) {
			for (int j = i; j < N - i - 1; j++) {
				char temp = a[i][j];
				a[i][j] = a[N - 1 - j][i];
				a[N - 1 - j][i] = a[N - 1 - i][N - 1 - j];
				a[N - 1 - i][N - 1 - j] = a[j][N - 1 - i];
				a[j][N - 1 - i] = temp;
			}
		}
		String[] ret = new String[N];
		for (int i = 0; i < c.length; i++) {
			ret[i] = "";
			for (int j = 0; j < c.length; j++) {
				ret[i] += a[i][j];
			}
		}

		return ret;
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
