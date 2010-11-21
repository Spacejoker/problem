package pe;

public class PE_41 {

	public PE_41() {
		
		PermutationGenerator pg = new PermutationGenerator(7);
		
//		if(isPrime(3)){
//			return;
//		}
		System.out.println(isPrime(3));
		
		
		long[] vals = new long[pg.getTotal().intValue()];
		
		for (int i = 0; i < vals.length; i++) {
			
			int[] next2 = pg.getNext();
			
			long val = 0;
			for (int j = 0; j < next2.length; j++) {
				val *= 10;
				val += next2[j]+1;
			}
			
			if(isPrime(val)){
				System.out.println("found prime!");
				System.out.println(val);
//				System.out.println("TES");
//				throw new RuntimeException();
//				return;
			}
//			System.out.println(i);
			vals[vals.length-1-i] = val; 
		}
		
//		System.out.println(vals[0]);

		
		
//		int[] next = pg.getNext();
//		for (int j = 0; j < next.length; j++) {
//			System.out.println(next[j]);		
//		}
	}
	
	private boolean isPrime(long n) {
		
		boolean prime = true;
		for (long i = 3; i <= Math.sqrt(n); i += 2)
			if (n % i == 0) {
				prime = false;
				break;
			}
		if (( n%2 !=0 && prime && n > 2) || n == 2) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args){
		new PE_41();
	}
}
