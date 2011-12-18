package pe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PE_79 {

	public PE_79() throws Exception{
		BufferedReader r = new BufferedReader(new FileReader(new File("keylog.txt")));
		
		List<String> integers = new ArrayList<String>();
		
		while(true){
			String s = r.readLine();
			if( s != null){
				integers.add(s);
			} else {
				break;
			}
		}
		outer:
		for (long val = 0; ; val++) {
			for (String string : integers) {
				if(!validNr(string, val)){
					continue outer;
				}
//				System.out.println(string);
			}
			System.out.println(val);
			break;
		}
	}
	
	private boolean validNr(String string, long val) {
		
		
		
		String valStr = new BigDecimal(val).toString();
		
		char a = string.charAt(0);
		char b = string.charAt(1);
		char c = string.charAt(2);
		
		if(valStr.indexOf(a) != -1 && valStr.indexOf(b) > valStr.indexOf(a) && valStr.indexOf(c)  > valStr.indexOf(b)){
			return true;
		}
		
		return false;
	}

	public static void main(String[] args) throws Exception{
		new PE_79();
	}

}
