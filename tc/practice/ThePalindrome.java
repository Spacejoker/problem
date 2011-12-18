
public class ThePalindrome {

	public int find(String s){
		StringBuilder b = new StringBuilder();
		
		if(isPalindrome(s)){
			return s.length();
		}
		for (int i = 0; i < s.length() ; i++) {
			
			b.insert(0,s.charAt(i));
			
			if(isPalindrome(s + b.toString())){
				return s.length() + b.length();
			}
		}
		
		return 0;
	}
	
	private boolean isPalindrome(String string) {
		for (int i = 0; i < string.length()/2; i++) {
			if(string.charAt(i) != string.charAt(string.length()-i-1)){
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args){
		System.out.println(new ThePalindrome().find("abdfhdyrbdbsdfghjkllkjhgfds"));
	}
}
