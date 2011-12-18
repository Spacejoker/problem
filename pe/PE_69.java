package pe;

public class PE_69 {
	
	public PE_69() {
		
		int iBest = 0;
		double best = 0;
		for (int i = 3; i < 11; i++) {
			int nr = 0;
			for (int j = 1; j < i; j++) {
				if(gcd(j,i) != j){
					nr++;
				}
			}
			
			double ans = ((double)i/(double)nr);
			if(ans > best){
				System.out.println("new best: " + ans + " for " + i);
				best = ans;
			}
		}
		
		System.out.println( best);
	}
	
	public int gcd(int a, int b)
	{
	   if (b==0) return a;
	   return gcd(b,a%b);
	}
	
	public static void main(String[] args){
		new PE_69();
	}
}
