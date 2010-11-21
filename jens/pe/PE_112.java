package pe;

import java.math.BigDecimal;

public class PE_112 {

	public PE_112(){
		long nr = 0;
		long bouncy = 0;
		long nonBouncy = 0;
		
		while(bouncy != nonBouncy*99 || bouncy == 0){
			nr ++;
			
			if(isBouncy(nr)){
				bouncy ++;
			} else {
				nonBouncy ++;
			}
				
		}
		System.out.println(nr);
	}
	
	public boolean isBouncy(long val){
		BigDecimal bd = new BigDecimal(val);
		String str = bd.toString();
		if(str.length() == 1){
			return false;
		}
		boolean inc = false;
		boolean dec = false;
		for (int i = 0; i < str.length()-1 && !(inc && dec); i++) {
			if(str.charAt(i) > str.charAt(i+1)){
				dec = true;
			} else if(str.charAt(i) < str.charAt(i+1)) {
				inc = true;
			}
		}
		return inc && dec;
	}
	
	
	public static void main(String[] args){
		new PE_112();
	}
}
