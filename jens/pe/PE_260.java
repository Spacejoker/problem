package pe;

public class PE_260 {

	int max = 100;
	public PE_260() {
		
		long sum = 0;
		for (int a = 0; a < max; a++) {
			for (int b = 0; b < max; b++) {
				for (int c = 0; c < max; c++) {
					int levels = 0;
					if(a != 0){
						levels ++;
					}
					if(b != 0 && b != a){
						levels ++;
					}
					if(c != 0 && c != a && c != b){
						levels ++;
					}
					if(levels == 2){
						sum ++;
					}
				}
			}
		}
		System.out.println(sum);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new PE_260();
	}

}
