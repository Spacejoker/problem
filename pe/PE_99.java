package pe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringBufferInputStream;

public class PE_99 {

	public PE_99() throws Exception{
		BufferedReader buf = new BufferedReader(new FileReader(new File("base_exp.txt")));
		double best = 0;
		double bestId = 0;
		for (int i = 0; i < 1000 ; i++) {
			String[] vals = buf.readLine().split(",");
			int base = Integer.valueOf(vals[0]);
			int exp = Integer.valueOf(vals[1]);
			
			double log = Math.log(base);
			double val = log*exp;
			if(best < val){
				best = val;
				bestId = i+1;
			}
		}
		
		System.out.println(bestId);
		
	}
	
	public static void main(String[] args) throws Exception{
		new PE_99();	
	}
}
