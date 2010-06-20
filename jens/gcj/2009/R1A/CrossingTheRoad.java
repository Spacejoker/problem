package gcj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class CrossingTheRoad {

//	final String f = "test.in";
//	final String f = "B-small-practice.in";
	final String f = "B-large-practice.in";
	private BufferedReader in;
	private PrintWriter out;
	private StringTokenizer st;
	
	class CrossInfo{
		int s;
		int w;
		int t;
		private int cycle;
		private int t0;
		public CrossInfo(int s, int w, int t) {
			super();
			this.s = s;
			this.w = w;
			this.t = t;
			this.cycle = s + w;
			this.t0 = t;
			
			while(t0 > 0){
				t0 -= cycle;
			}
		}
		@Override
		public String toString() {
			return "CrossInfo [s=" + s + ", t=" + t + ", w=" + w + "]";
		}
		
		int getTimeToDir(Direction dir, int time){
			
			int flippedTime = t0;
			
			boolean askedVertical = dir.equals(Direction.VERTICAL);
			boolean openVertical = true;
			
			while(true){
				if(askedVertical == openVertical){
					if(flippedTime + (openVertical ? s : w) > time){
						int t = flippedTime  - time;
						
						return t >= 0 ? t : 0;
					}
				}
				
				flippedTime = increment(flippedTime, openVertical);
				openVertical = !openVertical;
			}
		}
		
		private int increment(int x, boolean openVertical) {
			if(openVertical){
				return x + s;
			} else {
				return x + w;
			}
		}
	}
	int rows;
	int columns;
	CrossInfo cross[][];
	void solve() throws IOException {
		
		String result = "3";
		
		rows = nextInt();
		columns = nextInt();
		
		cross = new CrossInfo[rows][columns];
		boolean[][] visitedPos = new boolean[rows*2][columns*2];

		for (int i = 0; i < visitedPos.length; i++) {
			for (int j = 0; j < visitedPos[i].length; j++) {
				visitedPos[i][j] = false;
			}
		}
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cross[i][j] = new CrossInfo(nextInt(), nextInt(), nextInt());
			}
		}
		
		int bestTime = Integer.MAX_VALUE;
		
		bestTime = move(rows*2 -1 , 0, 0, bestTime, visitedPos);
		
		out.println(bestTime); 
		System.out.println(bestTime);
	}
	
	enum Direction{HORIZIONAL, VERTICAL};

	String delimiter = ",";
	
	Map<String, Integer> cache;
	
	private int move(int i, int j, int currentTime,  int bestTime, boolean[][] visitedPos) {
		
		if (i < 0 || j > columns * 2 -1){
			return bestTime;
		}
		
		StringBuilder b = new StringBuilder();
		String rep = b.append(i).append(delimiter).append(j).toString();
		
		if(bestTime < currentTime){		
			return bestTime;
		}
		
		if (cache.containsKey(rep)){ //not optimal path here
			Integer val = cache.get(rep);
			
			if(val < currentTime){
				return bestTime;
			}
		}
		
		if(i == 0 && j == columns*2 -1  ){ //goal
			return currentTime < bestTime ? currentTime : bestTime;
		}
		
		visitedPos[i][j] = true;
		
		//try going in all forward dirs, going back is not sane
		//try up
		if(i > 0 && visitedPos[i-1][j] == false){
			if(i % 2 == 0){ //go the pavement way
				bestTime = move(i -1, j, currentTime + 2, bestTime, visitedPos);	
			} else { //cros street
				int time = timeUntilGreen( Direction.VERTICAL, getGraphPos(i), getGraphPos(j), currentTime);
				bestTime = move(i -1, j, currentTime + time +1, bestTime, visitedPos);	
			}
		}
				
		//try right
		if(j < columns*2 -1 && visitedPos[i][j+1] == false){
			if(j % 2 == 1){
				bestTime = move(i, j+1, currentTime + 2, bestTime, visitedPos);	
			} else {
				int time = timeUntilGreen( Direction.HORIZIONAL, getGraphPos(i), getGraphPos(j), currentTime);
				bestTime = move(i, j+1, currentTime + time + 1, bestTime, visitedPos);	
			}
		}
		
		//try down
		if(i < rows*2 - 1 && visitedPos[i+1][j] == false){
			if(i % 2 == 0){ //go the pavement way
				int time = timeUntilGreen( Direction.VERTICAL, getGraphPos(i), getGraphPos(j), currentTime);
				bestTime = move(i +1, j, currentTime + time +1, bestTime, visitedPos);	
					
			} else { //cros street
				bestTime = move(i +1, j, currentTime + 2, bestTime, visitedPos);	
			}
		}
		
		//try left
		if(j > 0 ){
			if(j % 2 == 1 && visitedPos[i][j-1] == false){
				int time = timeUntilGreen( Direction.HORIZIONAL, getGraphPos(i), getGraphPos(j), currentTime);
				bestTime = move(i, j-1, currentTime + time + 1, bestTime, visitedPos);	
			} else {
				bestTime = move(i, j-1, currentTime + 2, bestTime, visitedPos);	
			}
		}
		
		cache.put(rep, bestTime);
		visitedPos[i][j] = false;
		return bestTime;
		
	}

	private int timeUntilGreen(Direction vertical, int graphPos, int graphPos2, int currentTime) {
		return cross[graphPos][graphPos2].getTimeToDir(vertical, currentTime);
	}

	private int getGraphPos(int j) {
		return (int)(j / 2);
	}

	CrossingTheRoad() throws IOException {
		in = new BufferedReader(new FileReader(f));
		out = new PrintWriter(f + ".out");
		
		eat("");
		
		int tests = nextInt();
		
		long timeStart = System.currentTimeMillis();
		for (int test = 0; test < tests; ++test) {
			cache = new HashMap<String, Integer>();
			System.out.println("Test #" + (test + 1));
			out.print("Case #" + (test + 1) + ": ");
			solve();
			
		}
		
		System.out.println("Time: " + (System.currentTimeMillis() - timeStart));
		
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
		new CrossingTheRoad();
	}


}
