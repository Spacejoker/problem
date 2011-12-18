package pe;

import java.util.HashSet;
import java.util.Set;

public class PE_32 {

	public PE_32() {
		
		PermutationGenerator pg = new PermutationGenerator(9);
			
		long[] vals = new long[pg.getTotal().intValue()];
		
		Set<Long> prods = new HashSet<Long>();
		
		for (int i = 0; i < vals.length; i++) {
			
			int[] next2 = pg.getNext();
			
			long val = 0;
			for (int j = 0; j < next2.length; j++) {
				val *= 10;
				val += next2[j]+1;
			}
			
			//try to make a product with this permutation...
			
			for (int j = 1; j < 9; j++) {
				for (int j2 = j+1; j2 < 9; j2++) {
					
					//insert multi att j and equals at j2
					String string = Long.valueOf(val).toString();
					long a = Long.valueOf(string.substring(0,j));
					long b = Long.valueOf(string.substring(j,j2));
					long c = Long.valueOf(string.substring(j2));
					
					if(a * b == c){
						prods.add(c);
					}
				}
			}
		}
		
		long sum = 0;
		for (Long long1 : prods) {
			sum += long1;
		}
		System.out.println(sum);
		
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
		new PE_32();
	}
}
