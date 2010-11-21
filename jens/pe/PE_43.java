package pe;

public class PE_43 {

	public PE_43() {
		
		PermutationGenerator pg = new PermutationGenerator(10);

		Integer[] primes = new Integer[]{2,3,5,7,11,13,17};
		long sum = 0;
		outer: for (int i = 0; i < pg.getTotal().intValue(); i++) {
			
			int[] next2 = pg.getNext();
			
			long val = 0;
			for (int j = 0; j < next2.length; j++) {
				val *= 10;
				val += next2[j];
			}
			
			String s = Long.valueOf(val).toString();
			if(s.length() < 10 ){
				continue;
			}
			//now i've got the next pandigital nr in val
			
			//234 = prime 0
			
			for (int j = 1; j < 8; j++) {
				char first = s.charAt(j);
				char second = s.charAt(j+1);
				char third = s.charAt(j+2);
				
				StringBuffer buf = new StringBuffer();
				if(!(Long.valueOf(buf.append(first).append(second).append(third).toString()) % primes[j-1] == 0)){
					continue outer;
				}
			}
			sum += val;
			
		}
		System.out.println(sum);
	}
	public static void main(String[] args){
		new PE_43();
	}
}
