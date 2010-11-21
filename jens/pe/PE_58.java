package pe;

public class PE_58 {

	PE_58() {
		
		int dim = 7;
		int[][] grid = new int[dim][dim];
		
		for (int round = 0; round < dim/2; round++) {
			int startX = dim/2 + round;
			int startY = dim/2 -1 + round;
			if(round == 0) {
				startY ++;
			}
			int x = 0;
			for (int i = 0; i < grid.length; i++) {
				System.out.println(x);
			}
			
			System.out.println("starting round " + round + " at (x,y) = (" + startX + ", " + startY+ ");");
		}
		
//		System.out.println(start);
		
	}
	
	public static void main(String[] args){
		new PE_58();
	}
}
