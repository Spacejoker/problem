package srm473;

public class OnTheFarmDivTwo {

	OnTheFarmDivTwo(){
		
	}
	
	public int[] animals(int heads, int legs){
		int chickens = 0;
		int cows = 0;
		
		int diff = legs - heads*2;
		
		int[] res = new int[2];
		
		if(diff % 2 == 0){
			cows = diff/2;
			chickens = heads - cows;
			res[0] = chickens;
			res[1] = cows;
		} else {
			res = new int[0];
		}
		
		if(chickens < 0 || cows < 0){
			res = new int[0];
		}
		

				
		
		return res;
	}
	
	private static int[] testData = {10, 40};
	
	public static void main (String[] args){
		
		OnTheFarmDivTwo o = new OnTheFarmDivTwo();
		
		int[] x = o.animals(0, 0);
		if (x.length > 0){
			System.out.println(x[0] +", " + x[1]);	
		}
		
	}
}
