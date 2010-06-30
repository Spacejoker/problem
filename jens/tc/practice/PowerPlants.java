import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


public class PowerPlants {
	
	static int inf = Integer.MAX_VALUE;

	public int minCost(String[] connectionCost, String plantList, int numPlants){
		
		
		//represent all powerplants as a bit in an int, could be up to 32 of them?
		int n = connectionCost.length;
		int N = 1<<n;//(int) Math.pow(2, n);
		int[][] cost = new int[n][n];
		
		for (int i = 0; i < connectionCost.length; i++) {
			for (int j = 0; j < connectionCost[0].length(); j++) {
				char charAt = connectionCost[i].charAt(j);
				
				cost[i][j] = charAt <= '9' ? charAt - '0' : charAt - 'A' + 10; 
			}
		}
		
		int[] res = new int[N];
		int min = inf;
		int start = 0;
		
		Arrays.fill(res, inf);
		
		for (int i = 0; i < plantList.length(); i++) {
			if(plantList.charAt(i) == 'Y')
				start += 1<<i;
		}
		res[start] = 0;
		
		for (int index = 0; index < N; index++) {
			if(res[index] == inf){
				continue;
			}
			if(Integer.bitCount(index) >= numPlants && res[index] < min){
				min = res[index];
			}
			for (int i = 0; i < n; i++) {
				if(((index >> i)&1) == 0){ //lets light it
					int nextIndex = index + (int)Math.pow(2,i);
					int nextCost = inf;
					for (int j = 0; j < n; j++) {
						if(((index >> j)&1) == 1){
							if(cost[j][i] < nextCost)
								nextCost = cost[j][i];
						}
					}
					
					res[nextIndex] = Math.min(res[nextIndex], nextCost + res[index]);
				}
			}
		}
		
		return min;
	}
	
//	public int minCost(String[] connectionCost, String plantList, int need){
//		
//		int n = connectionCost.length, c[][] = new int[n][n], N = (int) Math.pow(2,n);
//		
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				c[i][j] = connectionCost[i].charAt(j);
//				if(c[i][j] >= '0' && c[i][j] <= '9') c[i][j] -= '0';
//				else c[i][j] = c[i][j] -'A'+10;
//			}
//		}
//		
//		int start = 0, d[] = new int[N], min = inf;
//		
//		for (int i = 0; i < n; i++) {
//			if(plantList.charAt(i) == 'Y'){
//				start += Math.pow(2, i);
//			}
//		}
//		
//		Arrays.fill(d, inf);
//		d[start] = 0;
//		
//		for (int mask = 0; mask < N ; mask++) { // go over all possible states
//			if(d[mask] != inf){ //if we have a valid state (we have created this state earlier if it's valid)
//				if(Integer.bitCount(mask) >= need && d[mask] < min) //enough set and new best?
//					min = d[mask];
//				
//				for (int i = 0; i < n; i++) { //over all plants
//					if( ((mask>>i)&1) == 0){ // if it is turned off we can try to turn it on
//						int cost = inf;  //new best cost;
//						int next = mask|(1<<i); //the next id, gotta figure out the data structure
//						
//						for (int j = 0; j < n; j++) { // go thourgh all and pick the best one that is alive
//							if( ((mask>>j)&1)==1 && c[j][i] < cost) // if this one is light up we can use it to enhance the next one
//								cost = c[j][i]; //find min cost
//						}
//						if(d[next] > d[mask] + cost)  //if the next state is better of with this setup, lets make it that way
//							d[next] = d[mask] + cost;
//					}
//				}
//			}
//		}
//		
//		return min;
//	}
//	
//	
//	public int myMinCost(String[] connectionCost, String plantList, int need){
//		
//		int n = connectionCost.length;
//		int[][] cost = new int[n][n];
//		int N = (int) Math.pow(2,n);
//		
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				cost[i][j] = connectionCost[i].charAt(j);
//				if(cost[i][j] >= '0' && cost[i][j] <= '9') cost[i][j] -= '0';
//				else cost[i][j] = cost[i][j] -'A'+10;
//			}
//		}
//		
//		int start = 0;
//		int res[] = new int[N];
//		int min = inf;
//		
//		for (int i = 0; i < n; i++) {
//			if(plantList.charAt(i) == 'Y'){
//				start += Math.pow(2, i); //typ bit-tänket 
//			}
//		}
//		
//		Arrays.fill(res, inf); //worst
//		res[start] = 0; //start where all are set
//		
//		for (int mask = 0; mask < N ; mask++) { // go over all possible states
//			if(res[mask] != inf){ //if we have a valid state (we have created this state earlier if it's valid)
//				if(Integer.bitCount(mask) >= need && res[mask] < min) //enough set and new best?
//					min = res[mask];
//				
//				for (int i = 0; i < n; i++) { //over all plants
//					if( ((mask)&(int)Math.pow(2, i)) == 0){ // if it is turned off we can try to turn it on
//						int bestCost = inf;  //new best cost;
//						int next = mask + (int)Math.pow(2, i);//mask|(1<<i); //the next id, gotta figure out the data structure
//						
//						for (int j = 0; j < n; j++) { // go thourgh all and pick the best one that is alive
//							if( ((mask)&(int)Math.pow(2, j))==Math.pow(2, j) && cost[j][i] < bestCost) // if this one is light up we can use it to enhance the next one
//								bestCost = cost[j][i]; //find min cost
//						}
//						if(res[next] > res[mask] + bestCost)  //if the next state is better of with this setup, lets make it that way
//							res[next] = res[mask] + bestCost;
//					}
//				}
//			}
//		}
//		
//		return min;
//	}

//	static int[][] costs;
//	public int minCost(String[] connectionCost, String plantList, int numPlants){
//		
//		costs = new int[connectionCost.length][plantList.length()];
//		boolean[] plants = new boolean[plantList.length()];
//
//		for (int i = 0; i < connectionCost.length; i++) {
//			if(plantList.charAt(i) == 'Y'){
//				plants[i] = true;
//			}
//		}
//		
//		for (int i = 0; i < connectionCost.length; i++) {
//			String s = connectionCost[i];
//			for (int j = 0; j < s.length(); j++) {
//				char charAt = s.charAt(j);
//				
//				if(charAt < 64)
//					charAt -= 48;
//				else
//					charAt -= 55;
//				costs[i][j] = Integer.valueOf(charAt);
//			}
//		}
//		
//		int best = Integer.MAX_VALUE;
//		best = getCost(plants, 0, best, numPlants);
//		
//		return best;
//		
//	}
//	
//	Map<Key, Integer> cache = new HashMap<Key, Integer>();
//	
//	class Key{
//		boolean[] b;
//		int score;
//	}
//	
//	private int getCost(boolean[] plants, int currentScore, int best, int needed) {
//	
//		Key key = new Key();
//		key.b = plants;
//		key.score = currentScore;
//		
//		if(cache.containsKey(key)){
//			return cache.get(key);
//		}
//		
//		if(currentScore > best){
//			return best;
//		}
//		
//		int nrOfOn = 0;
//		
//		for (int i = 0; i < plants.length; i++) {
//			if(plants[i])
//				nrOfOn ++;
//		}
//		
//		
//		if(nrOfOn >= needed){
//			return Math.min(currentScore, best);
//		}
//		
//		
//		for (int i = 0; i < costs.length; i++) {
//			for (int j = 0; j < costs[i].length; j++) {
//				if(plants[i] == false && plants[j] == true){
//					plants[i] = true;
//					int nextScore = currentScore + costs[j][i];
//					best = getCost(plants, nextScore, best, needed);
//					
//					plants[i] = false;
//				}
//			}
//			
//			
//		}
//		
//		cache.put(key, best);
//		
//		return best;
//	}
//
	public static void main(String[] args) {
		System.out.println(new PowerPlants().minCost(new String[]{"AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA", "AAAAAAAAAAAAAAAA"}, "NNNNYNNNNNNNNNNN", 16)); //return 5
	}

}
