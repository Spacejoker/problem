import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class NameSort {

	class Customer{
		String lastName;
		int prio;
		String fullName;
		public Customer(String lastName, int prio, String fullName) {
			super();
			this.fullName = fullName;
			this.lastName = lastName;
			this.prio = prio;
		}
	}
	
	public String[] newList(String[] list){
	
		List<String> asList = Arrays.asList(list);
		List<Customer> customer = new ArrayList<Customer>();
		
		int prio = 0;
		for (int i = 0; i < list.length; i++) {
			String[] split = asList.get(i).split(" ");
			customer.add(new Customer(split[split.length-1], prio++, asList.get(i)));
		}
		
		Collections.sort(customer, new Comparator<Customer>() {

			@Override
			public int compare(Customer o1, Customer o2) {
				
				int compareTo = o1.lastName.toLowerCase().compareTo(o2.lastName.toLowerCase());
				if(compareTo != 0){
					return compareTo;
				}
				if(o1.prio > o2.prio)
					return -1;
				return 1;
			}
			
		});
		
		for (int i = 0; i < list.length; i++) {
			list[i] = customer.get(i).fullName;
		}
		
		return list;
	}
	
	static String[] testCase = {"Alan","aLan","alAn","alaN","ALan","AlAn","AlaN","aLAn","aLaN","alAN"};
	
	public static void main(String[] args){
		String[] newList = new NameSort().newList(testCase);
		for (String string : newList) {
			System.out.println(string);
		}
//		System.out.println();	
	}
}
