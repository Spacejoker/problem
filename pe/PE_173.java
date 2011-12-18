package pe;

public class PE_173 {

	public PE_173() {
		int maxBricks = 1000000;
		
		long hits = 0;
		
		for (int side = 3; (side-1)*4 <= maxBricks; side++) {
			
			int squareSide = side;
			
			int cost = (side-1)*4;
			long totCost = cost;
			hits ++;
			
			for (int i = 1; ; i++) {
				
				squareSide -= 2;
				if(squareSide <3){
					break;
				}
				cost -= 8;
				totCost += cost;
				
				if(totCost <= maxBricks){
					hits ++;
				} else {
					break;
				}
			}
		}
		System.out.println(hits);
	}

	public static void main(String[] args) {
		new PE_173();
	}

}
