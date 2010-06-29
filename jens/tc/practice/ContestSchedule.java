import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ContestSchedule {

	int[] prob = new int[50];
	
	class Competition{
		int start;
		int end;
		int prob;
		
		public Competition(int start, int end, int prob) {
			super();
			this.start = start;
			this.end = end;
			this.prob = prob;
		}

		@Override
		public String toString() {
			return "Competition [end=" + end + ", prob=" + prob + ", start=" + start + "]";
		}
	}
	
	public double expectedWinnings(String[] contests){
		
		//add the prob of win to the
		List<Competition> comps = new ArrayList<Competition>(50);
		
		for (String string : contests) {
			comps.add(new Competition(Integer.valueOf(string.split(" ")[0]), Integer.valueOf(string.split(" ")[1]), Integer.valueOf(string.split(" ")[2])));
		}
		
		Collections.sort(comps, new Comparator<Competition>() {

			@Override
			public int compare(Competition o1, Competition o2) {
				if (o1.start < o2.start)
					return- 1;
				return 1;
			}
		});
		
		prob = new int[comps.size()];
		
		for (int i = 0; i < comps.size(); i++) {
			//new score
			Competition competition = comps.get(i);
			int prob2 = comps.get(i).prob;

			if(i == 0){ //no brainer
				prob[i] = prob2;
				continue;
			}
			
			//get previous nr of conflicts:
			int conflicts = 0;
			for (int j = 0; j < contests.length; j++) {
				if(i != j){
					Competition competition2 = comps.get(j);
					if(competition2.end > competition.start && competition2.start < competition.start){
						conflicts ++;//conflictIds.add(j);
					}
				}
			}
			
			if(i-1-conflicts >= 0 && prob[i-1-conflicts] + prob2 > prob[i-1]){
				if(i-1-conflicts >= 0){
					prob[i] = prob2 + prob[i-1-conflicts];	
				} else {
					prob[i] = prob2;
				}
				
			} else if(prob2 > prob[i-1]) {
				prob[i] = prob2;
			} else {
				prob[i] = prob[i-1];
			}
		}
		
		return prob[comps.size()-1] / 100.0d;
	}
	
	static String[] example = 	

	{
	"1361955 8940967 10","628145516 644285978 17","883515280 910484865 36",
	"762888635 769291174 52","98152102 136328674 1","9580638 20450682 50",
	"646139319 664648341 20","484027666 505290970 57","841082555 879295329 99",
	"940563715 966953344 4","579113528 595261527 25","925801172 962952912 9",
	"285845005 307916055 45","403573723 410697485 10","9467290 25698952 90",
	"631265400 650639733 3","520690249 559261759 96","491747709 531061081 86",
	"643215695 672420161 94","614608448 617341712 44","456517316 491770747 24",
	"806956091 828414045 88","528156706 559510719 15","158398859 190439269 4",
	"743974602 761975244 49","882264704 887725893 1","877444309 884479317 59",
	"785986538 806192822 19","732553407 747696021 81","132099860 148305810 97",
	"555144412 572785730 99","506507263 535927950 82","489726669 505160939 90",
	"793692316 804153358 99","901329638 919179990 10","29523791 44318662 58",
	"570497239 595701008 73","706091347 730328348 23","118054686 135301010 39",
	"307178252 337804001 93","896162463 900667971 39","271356542 273095245 80",
	"865692962 891795950 91","593986003 596160000 58","807688147 831190344 59",
	"468916697 496462595 92"
	};
	
	public static void main(String[] args){
		System.out.println(new ContestSchedule().expectedWinnings(example));
	}
}
