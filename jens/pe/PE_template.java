package pe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import pe.PE_102.Co;

public class PE_template {

	public String[] readFile(String filename){

		StringBuffer buf = new StringBuffer();
		List<String> rets = new ArrayList<String>();
		                          
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)));

			while(!bufferedReader.ready()){
				rets.add(bufferedReader.readLine());	
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return (String[]) rets.toArray();
		
	}	
	
	private double triangleArea(Co co, Co co2, Co co3) {
		double x1 = co.x;
		double y1 = co.y;
		double x2 = co2.x;
		double y2 = co2.y;
		double x3 = co3.x;
		double y3 = co3.y;
		
		return Math.abs(x1*y2+x2*y3+x3*y1-x1*y3-x3*y2-x2*y1)/2;
	}
	public int gcd(int a, int b)
	{
	   if (b==0) return a;
	   return gcd(b,a%b);
	}
	
//	long maxVal = 1000000;
//	long[] siev = new long[(int) maxVal];
//	
//	for (long i = 0; i < siev.length; i++) {
//		siev[(int) i] = i;
//	}
//	siev[1] = 0;
//	
//	
//	for (long i = 0; i < siev.length; i++) {
//		if(siev[(int) i] == 0){
//			continue;
//		}
//		
//		long x = 2*i;
//		while(x < siev.length){
//			siev[(int) x] = 0;
//			x += i;
//		}
//	}
//	
//	//ok, time to find the max stuffs
//	List<Long> primes = new ArrayList<Long>();
//	
//	for (long i = 0; i < siev.length; i++) {
//		if(siev[(int) i] != 0){
//			primes.add(i);
//		}
//	}
}
