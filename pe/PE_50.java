package pe;

import java.util.ArrayList;
import java.util.List;

public class PE_50 {

	public PE_50() {
		long maxVal = 1000000;
		long[] siev = new long[(int) maxVal];
		
		for (long i = 0; i < siev.length; i++) {
			siev[(int) i] = i;
		}
		siev[1] = 0;
		
		
		for (long i = 0; i < siev.length; i++) {
			if(siev[(int) i] == 0){
				continue;
			}
			
			long x = 2*i;
			while(x < siev.length){
				siev[(int) x] = 0;
				x += i;
			}
		}
		
		//ok, time to find the max stuffs
		List<Long> primes = new ArrayList<Long>();
		
		for (long i = 0; i < siev.length; i++) {
			if(siev[(int) i] != 0){
				primes.add(i);
			}
		}
		
		long bestSum = 0;
		long bestCount = 0;
		
		for (long start = 0; start < primes.size() ; start++) {
			
			long sum = 0;
			long count = 0;
			
			for (long i = start; i < primes.size() ; i++) {
				count ++;
				
				sum += primes.get((int) i);
				if(sum > siev.length || sum < 0){
					continue;
				}
				
				if(sum < siev.length && siev[(int) sum] != 0 && count > bestCount){
					bestSum = sum;
					bestCount = count;
				}
			}
		}
		
		System.out.println("nr of primes: " + bestCount);
		System.out.println(bestSum);
		
	}

	public static void main(String[] args) {
		new PE_50();
	}

}
