package r1a2010;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class B {
	
	Map dict = new HashMap<String, Integer>();
	
	private void putToSolution(String key, Integer val){
		dict.put(key, val);
	}
	
	private int solve(List<Integer> pixels, int finalValue) {
//		System.out.println("Size: " + pixels.size());
//		#look in dictionary
		
		String key = createString(pixels) + '~' + Integer.valueOf(finalValue);
		if(dict.containsKey(key)){
			return (Integer) dict.get(key);
		}
			

//		#then we are at the bottom
		if(pixels.size() == 0){
			putToSolution(key, 0);
			
			return 0;
		}

		
//		#try to delete it
		int best = solve(pixels.subList(0, pixels.size()-1), finalValue) + D;

		for(int newLastNr = 0; newLastNr < 256; newLastNr ++){
			int score = solve(pixels.subList(0, pixels.size()-1), newLastNr);
			
			int insertCost  = 0;
			if(M == 0){
				insertCost = 100*255;
				if(newLastNr == finalValue){
					insertCost = 0;
				}else{
					int inserts = Math.abs((Math.abs(newLastNr - finalValue) - 1) / M);
					insertCost = inserts * I;
				}
			}
			int changeCost = Math.abs(newLastNr - pixels.get(pixels.size()-1));
			
			if(best > insertCost + changeCost + score){
				best = insertCost + changeCost + score;
			}
		}
				
		putToSolution(key, best);
		
		return best;
	}


//    def putToSolution(key, best):
//		dict[key] = best
//	
//		def solve(pixels, finalValue):
//

	private String createString(List<Integer> pixels) {
		String s = "";
		for(Integer val: pixels){
			s += val + ","; 
		}
		return s;
	}


	int D = 0;
	int I = 0;
	int M = 0;
	int A = 0;
	
	public static void main(String[] args) throws Throwable{
		String fileName = "B-small-practice.in";
		System.out.println("lol");
		
		DataInputStream in = new DataInputStream(new FileInputStream(new File("C:/dev/gcj/practice/r1a/" + fileName)));
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    Integer nrOfCases =  Integer.valueOf(br.readLine());

	    System.out.println(nrOfCases);
	    

		B b = new B();
		
		for(int x = 0; x < nrOfCases.intValue(); x++){
			String result = "";
			
			String line = br.readLine();
			System.out.println(line);
			
			b.D = Integer.valueOf(line.split(" ")[0]);
			b.I = Integer.valueOf(line.split(" ")[1]);
			b.M = Integer.valueOf(line.split(" ")[2]);
			b.A = Integer.valueOf(line.split(" ")[3]);
			
			List<Integer> pixels = new ArrayList<Integer>(b.A);
			
			line = br.readLine();
			
			System.out.println(line);
			for( String val : line.split(" ")){
				pixels.add(Integer.valueOf(val));
			}
			
			int best = -1;
			
			for(int y = 0; x < 256; y++){
				int score = b.solve(pixels, y);
				if( best == -1 || score < best){
					best = score;
				}
			}

			System.out.println("Case #" + x + ": "  + best);
		}
	}
}
