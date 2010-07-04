
public class AvoidRoads {

	public long numWays(int width, int height, String[] bad){
		
		int[][] field = new int[height+1][width+1];
		long[][] best = new long[height+1][width+1];
		
		//Mark each field if they can go right / up
		int upDir = 2;
		int rightDir = 1;
		
		for (int i = 0; i < bad.length; i++) {
			String[] coords = bad[i].split(" ");
			int x1 = Integer.valueOf(coords[0]);
			int y1 = Integer.valueOf(coords[1]);
			int x2 = Integer.valueOf(coords[2]);
			int y2 = Integer.valueOf(coords[3]);
			
			if(x2 > x1 && (field[y1][x1] & rightDir) != rightDir){
				field[y1][x1] += rightDir;
			} else if(x1 > x2&& (field[y2][x2] & rightDir) != rightDir){
				field[y2][x2] += rightDir;
			} else if(y2 > y1&& (field[y1][x1] & upDir) != upDir){
				field[y1][x1] += upDir;
			} else if(y1 > y2 &&(field[y2][x2] & upDir) != upDir){
				field[y2][x2] += upDir;
			}
		}
		
		best[0][0] = 1;
		
		for (int i = 0; i <= width; i++) {
			for (int j = 0; j <= height; j++) {
				
				if(i > 0 && (field[j][i-1] & 1) != 1){
					best[j][i] += best[j][i-1]; 
				}
				
				if(j > 0 && (field[j-1][i] & 2) != 2){
					best[j][i] += best[j-1][i]; 
				}
			}
		}
		
		
		return best[height][width];
	}
	
	public static void main(String[] args){
		System.out.println(new AvoidRoads().numWays(100, 100, new String[]{"0 0 0 1", "0 0 1 0", "0 1 0 0", "1 0 0 0"}));
	}
	
}
