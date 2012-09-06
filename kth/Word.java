import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


public class Word {
	public String prefix;
	public String suffix;
	public int wordcnt;
	public List<Integer> occurrances;
	public int seekIndexInDataFile;
	
	public Word(String prefix, String suffix, int firstOccurrance) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
		this.wordcnt = 1;
		this.occurrances = new ArrayList<Integer>();
		occurrances.add(firstOccurrance);
	}

	public Word(String parseLine) {
		StringTokenizer s = new StringTokenizer(parseLine, " ");
		suffix = s.nextToken();
		wordcnt = Integer.parseInt(s.nextToken());
		seekIndexInDataFile = Integer.parseInt(s.nextToken());
	}

	@Override
	public String toString() {
		return "Word [prefix=" + prefix + ", suffix=" + suffix + ", wordcnt="
				+ wordcnt + ", occurrances=" + occurrances + "]";
	}
		
	public String fileString(){
		Collections.sort(occurrances);
		return prefix + suffix + " " + wordcnt + " "  + seekIndexInDataFile + "\n";
	}
	
	public String getName(){
		return prefix != null ? prefix + suffix : suffix;
	}
}