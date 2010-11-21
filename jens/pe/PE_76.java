package pe;

public class PE_76 {
	
	int nr = 100;
	
	public PE_76() {
		
		long[] ways = new long[nr +1];
		ways[5] = 6;
		
		for (int i = 6; i < ways.length; i++) {
			ways[i] = ways[i-1] + 1; //sig själv,  
		}
		System.out.println(ways[100]);
	}
		
//		int[] factors = new int[nr];
//		
//		long ways = 0;
//		
//		while(factors[nr-1] == 0){
//			increments(factors);
//			if(sum(factors) == nr){
//				ways ++;	
//			}
//		}
////		ways ++;
//		System.out.println(ways-1);
//	}
//	
//	long increments = 0;
//	
//	private void increments(int[] factors) {
//		increments++;
//		factors[0] += 1;
//		for (int i = 0; i < factors.length; i++) {
//			if(factors[i] * (i+1) > nr){
//				factors[i+1] ++;
//				factors[i] = 0;
//			}
//		}
//		if(increments % 1000000 == 0){
//			printFactors(factors);	
//		}
//		
//	}
//
//	private void printFactors(int[] factors) {
//		StringBuffer buf = new StringBuffer();
//		for (int i = 0; i < factors.length; i++) {
//			buf.append(i + 1);
//			buf.append(": ");
//			buf.append(factors[i]);
//			buf.append(", ");
//		}
//		System.out.println(buf.toString());
//	}

	private int sum(int[] factors) {
		
		int sum = 0;
		
		for (int i = 0; i < factors.length; i++) {
			sum += (i+1)*factors[i];
		}
//		if(sum == 5){
//			System.out.println("Sum: " + sum);	
//		}
		return sum;
	}

	public static void main(String[] args){
		new PE_76();
	}
	
}
