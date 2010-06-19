package gcj;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class DigginTwo {

	Map<State, Res> cache; 
	
//	final String f = "B-small-practice.in";
	final String f = "B-large-practice.in";
//	final String f = "testin.in";

	StringBuilder b = new StringBuilder();

	static long possibleToWin = 0;
	static long possibleToBeatBest = 0;
	static long readFromCache = 0;
	
	static long timeSpentInPossibleToWinMethod = 0;
	
	private BufferedReader in;
	private PrintWriter out;
	private StringTokenizer st;

	String[] rows = null;
	int rowCount;
	int columnCount;
	int maxFallDist;
	
	void solve() throws IOException {
		rowCount = nextInt();
		columnCount = nextInt();
		maxFallDist = nextInt();
		
		rows = new String[rowCount];
		
		for (int i = 0; i < rowCount; i++) {
			rows[i] = next();
		}
	
		int best = Integer.MAX_VALUE;
		
		int firstRightLimit = -1;
		
		for (int i = 0; i < columnCount; i++) {
			if(rows[0].charAt(i) == '.' && rows[1].charAt(i) == '#'){
				firstRightLimit ++;
			} else if(rows[0].charAt(i) == '.'){
				firstRightLimit ++;
				break;
			}else {
				break;
			}
		}
		
		best = move(0, 0, 0, firstRightLimit, 0, best,0);
		
		String result = best == Integer.MAX_VALUE ? "No" : "Yes " + best;
		out.println(result);
		System.out.println(result);
	}
	/**
	 * right/left limit is the newly dug area
	 */
	
	
	
	private int move(int i, int j, int leftLimit, int rightLimit, int digs, int best, int falls) {
		if(digs > best){
			return best;
		}
		State rep = new State(i, j, leftLimit, rightLimit);
//		String rep = new StringBuilder()
//		.append(i).append("#")
//		.append(j).append("#")
//		.append(leftLimit).append("#")
//		.append(rightLimit).append("#")
//		.append(digs).append("#")
//		.append(best).append("#")
//		.toString();
		
		if(cache.containsKey(rep)){
			
			Res res = cache.get(rep);
			if(res.digs <= digs && res.best <= best){
//				System.out.println("cache used");
				readFromCache ++;
				return cache.get(rep).best;
			}
				
//			else cache.remove(rep);
		}
		
		//make out which different diggings there can be done from position i, j:
		if(falls > maxFallDist){ //dead
			return best;
		}
		
		if(i == rowCount -1){ //at bottom
			if(digs < best){
				best = digs;
				System.out.println("Got best " + best);
			}
			return best;
		}
		
		if(rows[i+1].charAt(j) == '.'){
			return move(i+1, j, j, j, digs, best, falls+1);
		}
		
//		//Can this theoretically work given distance from bottom, width and accessible airholes?
		if(!possibleToWin(i,j,leftLimit,rightLimit) ){//|| !possibleToBeatBest(i,j,leftLimit,rightLimit, best)){
			cache.put(rep, new Res(digs, best));
			return best;
		}

		//Lets now see which area we can travel, right and left:
		int rightStopEdge = j;
		for (int pos = j; pos < columnCount; pos++) {
			if(rows[i+1].charAt(pos) == '#' && (pos <= rightLimit ||rows[i].charAt(pos) == '.')){ //we need floor to move around, can also
				 // test +1 / -1 for just falling down
				rightStopEdge = pos;
			} else if (rows[i].charAt(pos) == '.' || pos <= rightLimit){
				rightStopEdge = pos;
				break;
			} else {
				break;
			}
		}
		
		//same for left stop edge
		int leftStopEdge = j;
		for (int pos = j; pos >= 0; pos--) {
			if(rows[i+1].charAt(pos) == '#' && (pos >= leftLimit ||rows[i].charAt(pos) == '.')){ //we need floor to move around
				leftStopEdge = pos;
			} else if (pos >= leftLimit ||rows[i].charAt(pos) == '.'){
				leftStopEdge = pos;
				break;
			} else {
				break;
			}
		}
		
		//if max doesnt work, nothing will work? probably wrong
//		int tmpBest = Integer.MAX_VALUE;
		for (int k = leftStopEdge; k < rightStopEdge; k++) {
			best = getBest(i, digs, best, leftStopEdge, rightStopEdge);
		}
//		
		for (int leftEdge = leftStopEdge; leftEdge < rightStopEdge; leftEdge++) {
//			for (int rightEdge = rightStopEdge; rightEdge >=leftEdge; rightEdge--) {
			for (int rightEdge = leftEdge; rightEdge <= rightStopEdge; rightEdge++) { // better to start with small spans?
				
				//test the drilldown from this funky span, we are guaranteed to have floor =)))) (except for edges)
				//dig while moving right
				best = getBest(i, digs, best, leftEdge, rightEdge);
			}
		}

		cache.put(rep, new Res(digs, best));
		
		return best;
	}

	private int getBest(int i, int digs, int best, int leftEdge, int rightEdge) {
		boolean worked = true;
		
		int extraDigs = 0;
		for (int k = leftEdge; k < rightEdge; k++) { //if we cannot dig the entire span we break
			if(rows[i+1].charAt(k) == '#' && rows[i+1].charAt(k+1) == '#'){
				extraDigs ++;
			} else {
				worked = false;
			}
		}
		
		if(digs + extraDigs > best){
			worked = false;
		}
		
		if(rightEdge > leftEdge && worked){ 
			best = move(i+1, rightEdge-1, leftEdge, rightEdge-1, digs + extraDigs, best, 1);	
		}
		
		worked = true;
		//dig while moving left
		extraDigs = 0;
		for (int k = leftEdge; k < rightEdge; k++) { //if we cannot dig the entire span we break
			if(rows[i+1].charAt(k) == '#' && rows[i+1].charAt(k+1) == '#'){
				extraDigs ++;
			} else {
				worked = false;
			}
		}
		
		if(digs + extraDigs > best){
			worked = false;
		}
		if(leftEdge < rightEdge && worked){
			best = move(i+1, leftEdge+1, leftEdge+1, rightEdge, digs + extraDigs, best, 1);
		}
		
		//test if we can fall on either side (special case)				
		//jump off right side if possible
		if(rows[i+1].charAt(rightEdge) == '.'){
			best = move(i+1, rightEdge, rightEdge, rightEdge, digs, best, 1);
		}
		
		//jump off left
		if(rows[i+1].charAt(leftEdge) == '.'){
			best = move(i+1, leftEdge, leftEdge, leftEdge, digs, best, 1);
		}
		return best;
	}
	
	
	/**
	 * Cant handle air holes that are impossible to match, more of a sanity check
	 */
	private boolean possibleToWin(int i, int j, int leftLimit, int rightLimit) {
//		System.out.println("sorted out the impossible");
		
		int extraAir = 0;
		
		int diff = rightLimit - leftLimit +1;
		
		for (int row = i; row < rowCount; row++) {
			
			//do we need to make search wider?
			if(leftLimit > 0){
				leftLimit --;
				while(leftLimit > 0 && rows[row].charAt(leftLimit) == '.'){
					leftLimit --;
				}
			}
			
			if(rightLimit < columnCount -2){
				rightLimit ++;
				while(rightLimit < columnCount -2&& rows[row].charAt(rightLimit) == '.'){
					rightLimit ++;
				}
			}
			
			for (int k = leftLimit; k <= rightLimit; k++) {
				if(rows[row].charAt(k) == '.'){
					extraAir ++;
				}
			}
			
			if(diff + extraAir >= rowCount-i-1){
				return true;
			}
		}
		
		if(diff + extraAir >= rowCount-i-1){
			return true;
		}
		
		possibleToWin ++;
		return false;
	}


	DigginTwo() throws IOException {
		in = new BufferedReader(new FileReader(f));
		out = new PrintWriter(f + ".out");
		
		eat("");
		
		long start = System.currentTimeMillis();
		
		int tests = nextInt();
		for (int test = 0; test < tests; ++test) {
			cache = new HashMap<State, Res>();
			System.out.println("Test #" + (test + 1));
			out.print("Case #" + (test + 1) + ": ");
			solve();
			System.gc();
//			for (State state : cache.keySet()) {
//				System.out.println(cache.get(state));
//			}
		}
		
		System.out.println("Time: " + (System.currentTimeMillis() - start));
		System.out.println("Read from cache: " + readFromCache);
		System.out.println("possibleToWin rejected: " + possibleToWin);
		System.out.println("timeSpentInPossibleToWinMethod : " + timeSpentInPossibleToWinMethod); 		
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
		new DigginTwo();
	}
	
	class State {
		int i;
		int j;
		int leftLimit;
		int rightLimit;
		public State(int i, int j, int leftLimit, int rightLimit) {
			super();
			this.i = i;
			this.j = j;
			this.leftLimit = leftLimit;
			this.rightLimit = rightLimit;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + i;
			result = prime * result + j;
			result = prime * result + leftLimit;
			result = prime * result + rightLimit;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (i != other.i)
				return false;
			if (j != other.j)
				return false;
			if (leftLimit != other.leftLimit)
				return false;
			if (rightLimit != other.rightLimit)
				return false;
			return true;
		}
		private DigginTwo getOuterType() {
			return DigginTwo.this;
		}
		
	}
	
	class Res {
		public Res(int digs, int best) {
			super();
			this.digs = digs;
			this.best = best;
		}
		int digs;
		int best;
	}

}
