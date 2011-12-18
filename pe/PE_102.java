package pe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PE_102 {
	
	class Co{
		int x;
		int y;
		public Co(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
	}
	
	public PE_102() throws Exception {
		
//		A(-340,495), B(-153,-910), C(835,-947)
		
		BufferedReader buf = new BufferedReader(new FileReader(new File("triangles.txt")));
		long hits = 0;;
//		double bestId = 0;
		
		Co origin = new Co(0,0);
		
		for (int i = 0; i < 1000 ; i++) {
			String[] vals = buf.readLine().split(",");
			
			Co co = new Co(Integer.valueOf(vals[0]), Integer.valueOf(vals[1]));
			Co co2 = new Co(Integer.valueOf(vals[2]), Integer.valueOf(vals[3]));
			Co co3 = new Co(Integer.valueOf(vals[4]), Integer.valueOf(vals[5]));
			
			double totArea = triangleArea(co, co2, co3);
			
			double a1 =  triangleArea(co, co2, origin);
			double a2 =  triangleArea(co2, co3, origin);
			double a3 =  triangleArea(co3, co, origin);
			if(Math.abs(a1+a2+a3-totArea) < 0.000001){
				hits++;
			}
		}
		
		System.out.println(hits);
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

	public static void main(String[] args)  throws Exception {
		new PE_102();
	}
}
