package pe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PE_205 {

	public PE_205() {
		
//		int[] peter = new int[9*4];
//		int[] colin = new int[6*6];

		List<Integer> sums = new ArrayList<Integer>(); 
		List<Integer> colin = new ArrayList<Integer>();
		
		
		int[] dices = new int[9];
		
		for (int i = 0; i < dices.length; i++) {
			dices[i] = 1;
		}
		
		int[] colDice = new int[6];
		
		for (int i = 0; i < colDice.length; i++) {
			colDice[i] = 1;
		}
		
		for (; ; ) {
			sums.add(getSum(dices));
			boolean breakIt = incrementDices(dices, 4);
			if(breakIt){
				break;
			}
//			System.out.println("len: " + sums.size());
		}
		
		for (; ; ) {
			colin.add(getSum(colDice));
			boolean breakIt = incrementDices(colDice, 6);
			if(breakIt){
				break;
			}
//			System.out.println("len: " + sums.size());
		}
		
		System.out.println("sumsize: " + sums.size());
		System.out.println("colin: " + colin.size());
		
		long matches = 0;
		long peteWins = 0;
		
		for (int die : sums) {
			for (int col : colin) {
				matches ++;
				if(die > col){
					peteWins ++;
				}
			}
		}
		
		System.out.println(dices[8]);
		
		System.out.println(peteWins);
		System.out.println(matches);
		
		System.out.println(new BigDecimal(peteWins).divide(new BigDecimal(matches), 32, BigDecimal.ROUND_HALF_UP));
	}

	private boolean incrementDices(int[] dices, int maxVal) {
		
		int len = dices.length;
		
		int n = 0;
		dices[n] ++;
		
		while(true && n < len){
//			System.out.println("yes with n = " + n);
			if(dices[n] > maxVal){
				dices[n] = 1;
				if(n < len -1){
					dices[n+1] += 1;
				}
				n++;
				
			} else {
				break;
			}
		}
		
		if(n == len){
			System.out.println("n: " + n);
			return true;
		}
		
		return false;
	}

	private Integer getSum(int[] dices) {
		int sum = 0;
		for (int i = 0; i < dices.length; i++) {
			sum += dices[i];
		}
		return sum;
	}

	public static void main(String[] args) {
		new PE_205();
	}

}
