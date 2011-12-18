package pe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PE_33 {

	public PE_33(){

		int totNum = 1;
		int totDenom = 1;
		
		for (int a = 1; a < 10; a++) {
			for (int b = 1; b < 10; b++) {
				for (int c = 1; c < 10; c++) {
					for (int d = 0; d < 10; d++) {
						
//						if(a == 4 && b == 9 && c == 9 && d == 8){
//							System.out.println("x");
//						}
						
						if(a == b && c == d){
							continue;
						}
						
						BigDecimal num = new BigDecimal(10*a + b);
						BigDecimal denom = new BigDecimal(10*c + d);
						
						if(num.compareTo(denom) >= 0){
							continue;
						}
						
						BigDecimal val = num.divide(denom, 32, BigDecimal.ROUND_HALF_UP);
						
						BigDecimal ba = new BigDecimal(a);
						BigDecimal bb = new BigDecimal(b);
						BigDecimal bc = new BigDecimal(c);
						BigDecimal bf = new BigDecimal(d);
						
						
						
						if( (c != 0 && b == d && val.compareTo(ba.divide(bc, 32, BigDecimal.ROUND_HALF_UP)) == 0 )||
							(d != 0 && b == c&&val.compareTo(ba.divide(bf, 32, BigDecimal.ROUND_HALF_UP)) == 0 )||
							(c != 0 && a==d&&val.compareTo(bb.divide(bc, 32, BigDecimal.ROUND_HALF_UP)) == 0 )||
							(d != 0 && a==c&&val.compareTo(bb.divide(bf, 32, BigDecimal.ROUND_HALF_UP)) == 0 )
						){
							totNum *= num.intValue();
							totDenom *= denom.intValue();
							System.out.println(num + " / " + denom);
						}
					}
				}
			}
		}
		
		int gcd = GCD(totNum, totDenom);
		
		System.out.println(totDenom / gcd);
		
	}
	
	public int GCD(int a, int b)
	{
	   if (b==0) return a;
	   return GCD(b,a%b);
	}
	
	public static void main(String args[]){
		new PE_33();
	}
}

