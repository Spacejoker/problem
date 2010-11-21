package pe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PE_59 {

	public PE_59() {
		
		String decrypt = readFile("cipher1.txt");
		
		char start = 97;
		char end = 123;
		for (char bitOne = start; bitOne <= end ; bitOne++) {
			for (char bitTwo = start; bitTwo <= end ; bitTwo++) {
				for (char bitThree = start; bitThree <= end ; bitThree++) {
					
					int bitNr = 0;
					char nextBit = bitOne;
					
					StringBuffer buf = new StringBuffer();
					for (int i = 0; i < decrypt.length() ; i++) {
						
						char a = decrypt.charAt(i);
						
						buf.append((char)(a ^ nextBit));
						bitNr ++;
						if (bitNr > 2){
							bitNr = 0;
						}
						switch(bitNr){
						case 0:
							nextBit = bitOne;
							break;
						case 1:
							nextBit = bitTwo;
							break;
						case 2:
							nextBit = bitThree;
							break;
						}
					}
					if(buf.toString().contains("Gospel") && buf.toString().contains("is") && buf.toString().contains("and")){
						
						System.out.println("val: " + ((int)(bitOne) + (int)(bitTwo) + (int)(bitThree)));
						System.out.println(buf.toString());
						
						int sum = 0;
						for (int i = 0; i < buf.toString().length(); i++) {
							sum += buf.charAt(i);
						}
						System.out.println(sum);
						
					}
//					System.out.println(buf.toString());
				}
			}
		}
		
		char a = 65;
		char b = 42;
		a = (char) (a ^ b);
		System.out.println(a);
		a = (char) (a ^ b);
		System.out.println(a);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new PE_59();
	}
	
	public String readFile(String filename){
		
		StringBuffer buf = new StringBuffer();
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filename)));

			String[] s = bufferedReader.readLine().split(",");
			
			for (String string : s) {
				int val = Integer.valueOf(string);
				char c = (char)val;
				buf.append(c);	
			}
		
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return buf.toString();
		
	}

}
