package pe;

public class PE_49 {

	public PE_49() {
		
//		long maxVal = 10000;
//		long[] siev = new long[(int) maxVal];
//		
//		for (long i = 0; i < siev.length; i++) {
//			siev[(int) i] = i;
//		}
//		siev[1] = 0;
//		
//		
//		for (long i = 0; i < siev.length; i++) {
//			if(siev[(int) i] == 0){
//				continue;
//			}
//			
//			long x = 2*i;
//			while(x < siev.length){
//				siev[(int) x] = 0;
//				x += i;
//			}
//		}
		
		
		for (int addVal = 1; addVal < 5000; addVal++) {
			long stopVal = 9999-6659;
		
			outer: for (int startVal = 1000 ; startVal < stopVal; startVal++) {
				if(startVal == 1487 && addVal == 3330){
//					continue;
					System.out.println("apa");
				}
				
				int val2 = startVal + addVal;
				int val3 = val2 + addVal;
				if(val2 >= 10000 || val3 >= 10000){
					continue;
				}
				
				if(isPrime(startVal) && isPrime(val3) && isPrime(val2) ){
					
					int[] hits = new int[10];
					int[] hits2 = new int[10];
					int[] hits3 = new int[10];
					
					int cp1 = startVal;
					int cp2 = val2;
					int cp3 = val3;
					
					while(cp1 > 0){
						hits[cp1%10] ++;
						cp1 /= 10;
					}
					
					while(cp2 > 0){
						hits2[cp2%10] ++;
						cp2 /= 10;
					}
					
					while(cp3 > 0){
						hits3[cp3%10] ++;
						cp3 /= 10;
					}
					
					for (int i = 0; i < hits.length; i++) {
						if(hits[i] != hits2[i] ||hits[i] != hits3[i] ){
							continue outer;
						}
					}
					
					
					
//					//find the 4 perms of startVals
//					String s = Integer.valueOf(startVal).toString();
//					String perm1 = s.subSequence(1, 4).toString() + s.subSequence(0,1).toString();
//					String perm2 = perm1.subSequence(1, 4).toString() + perm1.subSequence(0,1).toString();
//					String perm3 = perm2.subSequence(1, 4).toString() + perm2.subSequence(0,1).toString();
//					
//					int p1 = Integer.valueOf(perm1);
//					int p2 = Integer.valueOf(perm2);
//					int p3 = Integer.valueOf(perm3);
//					
//					if((p1 == val2 || p2== val2 || p3 == val2) && (p1 == val3 || p2== val3 || p3 == val3)){
					System.out.println("its güüd: " + startVal + "" + val2 + "" + val3);
//					}
					
//					System.out.println(perm3);
					
					
				}				
			}
		}
		
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
		new PE_49();
	}
}
