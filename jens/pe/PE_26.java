package pe;

import java.math.BigDecimal;

public class PE_26 {
	
	static int maxCycle = 999;
	
	public PE_26() {
		BigDecimal fraction = null;
		
		int maxLen = 0;
		int bestVal = 0;
		
		for (int i = 983; i < 984; i++) {
			fraction = BigDecimal.ONE.divide(new BigDecimal(i), 5000, BigDecimal.ROUND_HALF_UP);
			
			//look for cycle
			String s = fraction.toString().split("\\.")[1];
//System.out.println(s);
//			while(s.charAt(s.length() -1 ) == '0'){
//				s = s.substring(0, s.length()-1);
//			}
//			
//			System.out.println(s);
			
			int a = cycleLenght(s);
			if(a > maxLen){
				maxLen = a;
				bestVal = i;
			}
		}
		System.out.println(bestVal + " on the val: " + maxLen);
	}
	
	private int cycleLenght(String string) {
		int bestCycle  = 0;
		for (int startPos = 0; startPos < 100; startPos++) {
			for (int i = 1; i < 983; i++) {
				
				int cycle = i;
				boolean isCycle = true;
				for (int j = 0; j < cycle; j++) {
					int firstPos = startPos;
					int secondPos = startPos + cycle;
					
					if(!(string.length() > secondPos ) || string.charAt(firstPos) != string.charAt(secondPos)){
						isCycle = false;
					}
				}
				if(isCycle){
					if(bestCycle < cycle){
						bestCycle = cycle;
					}
					break;
				}
			}
		}
		return bestCycle;
	}

	public static void main(String[] args){
		new PE_26();
	}

}
