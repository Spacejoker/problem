package pe;

public class PE_179 {

	public PE_179() {
		long maxVal = 10000000;
		
		long found = 0;
		
		for (long i = 2; i < maxVal; i++) {
			boolean has = hasProperty(i);
			if(has){
				found ++;
			}
			
			if(i % 1000 == 0){
				System.out.println(i);
			}
		}
		System.out.println("Found: " + found);
	}

	private boolean hasProperty(long i) {
		
		long divs = 0;
		
		for (int j = 2; j < i ; j++) {
			if(i % j == 0){
				divs ++;
			}
		}
		
		long secondDivs = 0;
		long second = i +1;
		
		for (int j = 2; j < second ; j++) {
			if(second % j == 0){
				secondDivs ++;
			}
		}
		
		return divs == secondDivs;
	}

	public static void main(String[] args) {
		new PE_179();
	}

}
