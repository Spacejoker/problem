package pe;

public class PE_38 {

	public PE_38() {
		
		long maxPandigital = 0;
		int limit = 1000000000/3;
		
		System.out.println("Limit: " + limit);
		
		outer: for (int i = 1; i <= limit ; i++) {
			
//			if(i % 10000000 == 0){
//				System.out.println(i);
//			}
			
			long factor = 0;
			long val = 0;
			
			StringBuffer buf = new StringBuffer();
			
			while(buf.length() < 9){
				factor ++;
				val = i*factor;
				String string = Long.valueOf(val).toString();
				if(string.contains("0")){
					continue outer;
				}
				buf.append(string);
			}
			
			if(isPandigital(buf.toString())){
				long valueOf = Long.valueOf(buf.toString());
				if(valueOf > maxPandigital){
					maxPandigital = valueOf;
					System.out.println(maxPandigital);
				}
			}
			
			
		}
		
		
	}
	
	private boolean isPandigital(String string) {
		
		int len = string.length();
		
		boolean[] nrs = new boolean[len];
		
		for (int i = 0; i < len; i++) {
			int index = string.charAt(i) - '1';
			if(nrs[index] == true){
				return false;
			}
			nrs[index] = true;
		}
		
		return true;
	}

	public static void main(String[] args){
		long t0 = System.currentTimeMillis();
		new PE_38();
		System.out.println("Time: " + (System.currentTimeMillis() - t0));
	}
}
