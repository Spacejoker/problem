package srm473;

import java.util.Iterator;

public class SequenceOfCommands {

	public String whatHappens(String[] commands){
		
		int dir = 0;
		
		int posx = 0;
		int posy = 0;
		
		for (int x = 0; x < 1000; x++) {	
			
	
			for (String command : commands){
				for (int i = 0; i < command.length(); i++) {
				
					if(command.charAt(i) == 'R' ){
						dir ++;
					}
					if (command.charAt(i) == 'L'){
						dir --;
					}
					if(dir < 0){
						dir = 3;
					}
					if(dir > 3){
						dir = 0;
					}
					if(command.charAt(i) == 'S'){
						if(dir == 0){
							posx ++;
						}
						if(dir == 1){
							posy ++;
						}
						if(dir == 2){
							posx --;
						}
						if(dir == 3){
							posy --;
						}
					}
				}
			}
		}
		
		String ret = "bounded";
		
		if(posx > 50 || posx < -50 || posy > 50 || posy < -50){
			ret = "unbounded";
		}
			
		
		return ret;
	}
	
	public static void main(String[] args){
		SequenceOfCommands t = new SequenceOfCommands();
		
		String[] s = new String[]{"SSSS","R"};
		
		String res = t.whatHappens(s);
		System.out.println(res);
	}
}
