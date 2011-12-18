package pe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PE_82 {
	
	int dim = 80;
	
	public PE_82() {
		
		String[] readFile = readFile("matrix.txt");
		
		int[][] cost = new int[dim][dim];
		int[][] val = new int[dim][dim];
		for (int i = 0; i < val.length; i++) {
			Arrays.fill(val[i], Integer.MAX_VALUE);
		}
		
		for (int i = 0; i < dim; i++) {
			String[] s = readFile[i].split(",");
			for (int j = 0; j < dim; j++) {
				cost[i][j] = Integer.valueOf(s[j]);
			}
		}
		
		for (int i = 0; i < val.length; i++) {
			val[i][0] = cost[i][0];
		}
		
		for (int col = 1; col < cost.length; col++) {
			for (int row = 0; row < cost.length; row++) {
				
//				if(col == 3){
//					System.out.println("x");
//				}
				int cheapest = val[row][col-1] ;
				
				if(row > 0 && val[row-1][col] < cheapest){
					cheapest = val[row-1][col];
				}
				
				if(val[row][col] > cost[row][col] + cheapest ){
					val[row][col] = cost[row][col] + cheapest; 
				}
			}
			//	then from the other way
			for (int row = cost.length-1; row >= 0; row--) {
				
				
				int cheapest = val[row][col-1] ;
				
				if(row < cost.length -1&& val[row+1][col] < cheapest){
					cheapest = val[row+1][col];
				}
				
				if(val[row][col] > cost[row][col] + cheapest ){
					val[row][col] = cost[row][col] + cheapest; 
				}
			}
		}
		int best = Integer.MAX_VALUE;
		for (int i = 0; i < val.length; i++) {
			if(val[i][dim-1] < best){
				best =val[i][dim-1]; 
			}
		}
		System.out.println(best);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long t0 = System.currentTimeMillis();
		new PE_82();
		long t1 = System.currentTimeMillis();
		System.out.println("T: " + (t1-t0));
	}
	
	public String[] readFile(String filename){
		
		StringBuffer buf = new StringBuffer();
		List<String> rets = new ArrayList<String>();
		                          
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)));

			while(true){
				String readLine = bufferedReader.readLine();
				if(readLine == null || readLine.equals("")){
					break;
				}
				rets.add(readLine);	
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		String[] s = new String[rets.size()];
		for (int i = 0; i < s.length; i++) {
			s[i] = rets.get(i);
		}
		
		return s;
		
	}


}
