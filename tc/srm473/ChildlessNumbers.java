package srm473;

import java.util.HashMap;
import java.util.Map;

public class ChildlessNumbers {

	Map<Integer, Integer> map = new HashMap<Integer,Integer>();
	
	int D(int val){
		if(map.containsKey(val)){
			return map.get(val);
		}
		
		int x = 0;
		
		while (val > 0){
			x += val%10;
			val /= 10;
		}
		
		map.put(val, x);
		
		return x;
		
	}
	
	int[] getArtNrs(int pos){
		
		int[] vals = new int[pos*10];
		
		return vals;
		
	}
	
	
	public int howMany(int A, int B){
		
		int childless = 0;
		
		//getmax
		int res = 0;
		int testVal = 1;
		
		for (int i = A; i <= B; i++) {
		
			boolean found = false;
			
			//what is i?
			
			int start = i/Integer.valueOf(i).toString().length();
			
			//61 / 1 med 61 nollor...
			int end = i;
			while(end / (Integer.valueOf(i).toString().length()*10) < i){
				end *= i;
			}
			
			
			for (int j = start; j <end; j++) {
				
				int n = D(j);
				
				if(j%n == 0 && j/n == i){
					found = true;
						
				}
			}
			
			if(!found){
				childless ++;
			}
		}
		
		return childless;
	}
	
	
	
	public static void main( String[] args){
		ChildlessNumbers nr = new ChildlessNumbers();
		
		System.out.println(nr.howMany(275,300));
	}
}
