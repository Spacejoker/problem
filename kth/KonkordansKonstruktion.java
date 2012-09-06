import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * Create the idices needed to
 * 
 * @author jensst
 * @param <E>
 * 
 */
public class KonkordansKonstruktion {

	private static final String CHARSET = "ISO-8859-1";
	private boolean debug = false;
	private int numlines = 200;
	private static final String IDX_DELIMITER = "<<<";
	private static final String NAME_DELIMITER = "~>~";
	private static final String INDICES_FILE = "/var/tmp/jensst_konk_idx";
	private static final String NAME_TO_INDICES_MAP_FILE = "/var/tmp/jensst_konk_intern";
	private static final String DATA_FILE = "/info/adk12/labb1/korpus";
	private static final int NEWLINE_CHARCOUNT = 1;
	
	BufferedWriter idxWriter = null;
	RandomAccessFile dataFileRandomAccessReader ;
	private String testword = "en";
	
	public static void main(String[] args) throws Exception {
		new KonkordansKonstruktion();
	}

	public KonkordansKonstruktion() throws Exception {
		long t0 = System.currentTimeMillis();
		
		//set up readers and writers
		idxWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(INDICES_FILE),  CHARSET));
		dataFileRandomAccessReader = new RandomAccessFile(DATA_FILE, "r");
		
		// Read the words from file
		Map<String, Word> allWords = new HashMap<String, Word>();
		retrieveWords(allWords);

		// Sort the words
		TreeSet<String> set = new TreeSet<String>();
		set.addAll(allWords.keySet());

		// create a sorted sequence of the words indices and store them in the indices file.
		// each word must know how many positions are saved to be able to know their own position
		writeWordIndices(allWords, set);

		// Each line represents a lookup for a certain name, on the form "name nrOccurrance firstposinfile"
		writeNameToIndexLookupFile(allWords, set);

		
		//a test-query
		String searchword = testword;

		//TODO: Use a trie to set the upper/lower bounds for the binsearch
		List<Integer> indices = retrieveIndices(searchword, 0, 100000000);
		
		System.out.println(indices.size() + " hit" + (indices.size() != 1 ? "s" : "") + ":");
		
		for (int i = 0; i < indices.size(); i++) {
			Integer idx = indices.get(i);
			
			String str = getDataFromIdx(idx);
			System.out.println(str);
		}
		
		System.out.println("Time used: " + (System.currentTimeMillis() - t0 + 500) / 1000 + " seconds.");
	}

	private List<Integer> retrieveIndices(String searchword, int lo, int hi) throws Exception {

		RandomAccessFile rnd = new RandomAccessFile(INDICES_FILE, "r");

		searchword = searchword.toLowerCase();
		byte[] buf = new byte[8 * 1024];
		while (hi - lo > 1000) {
			int mid = (hi + lo) / 2;
			rnd.seek(mid);

			rnd.read(buf);
			String string = new String(buf);

			int indexOf = string.indexOf(NAME_DELIMITER);
			if (indexOf != -1) {
				String name = getName(string);

				if (searchword.compareTo(name) < 0) {
					lo = mid;
				} else {
					hi = mid;
				}
			}
		}
		if(debug){
			System.out.println("Binsearch ended at " + lo);
		}
		rnd.seek(lo);
		rnd.read(buf);
		
		StringTokenizer tokenizer = new StringTokenizer(new String(buf, CHARSET), NAME_DELIMITER);

		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			String name = getName(token);

			if (name.equals(searchword)) {
				return getOccurrances(token);
			}
		}

		return new ArrayList<Integer>();
	}

	private String getName(String string) {
		return string.split(IDX_DELIMITER)[0];
	}

	private List<Integer> getOccurrances(String string) throws Exception {
		String[] split = string.split(IDX_DELIMITER);
		String s = split.length >= 2 ? split[1] : "";
		List<Integer> ret = new ArrayList<Integer>();
		
		for (int i = 0; i < s.length() - 9; i += 10) {
			String substring = s.substring(i, i + 10);
			ret.add(Integer.valueOf(substring));
		}

		return ret;
	}

	private void writeNameToIndexLookupFile(Map<String, Word> allWords, TreeSet<String> set) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(NAME_TO_INDICES_MAP_FILE));

		// write all our words to file
		for (String key : set) {
			Word word = allWords.get(key);
			writer.write(word.fileString());
		}
		
		writer.close();
	}
	
	// Writes all the words as bytes to easier look up the position
	private void writeWordIndices(Map<String, Word> allWords, TreeSet<String> set) throws IOException {
		
		//process all words in order
		for (String key : set) {
			Word nextWord = allWords.get(key);

			idxWriter.write(NAME_DELIMITER + nextWord.getName() + IDX_DELIMITER);
			
			//write each position on the occurrances, pad zeroes
			for (Integer position : nextWord.occurrances) {
				if(debug && key.equals(testword)){
					System.out.println("The word " + testword + " has a position: " + position);
				}
				idxWriter.write(convertToFileInteger(position));
			}
		}

		idxWriter.write(NAME_DELIMITER);// a final delimiter is needed when parsing this later
		idxWriter.flush();
	}
	
	

	private String convertToFileInteger(Integer position) {
		String string = position.toString();
		
		while (string.length() < 10) {
			string = "0" + string;
			
		}
		return string;
	}

	private void retrieveWords(Map<String, Word> allWords) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(DATA_FILE)), CHARSET));
		int i = 0, pos = 0;
		List<Integer> delimiterCharacters = Arrays.asList(new Integer[] { '.', '\n', ' ' });

		// Process the file and store all words in the allWordsMap
		while (i < numlines || numlines  == -1) {
			i++;
			String line = reader.readLine();
			if (line == null)
				break;
			line = line.toLowerCase();
			
			if(debug ){
				System.out.println("Read a line:");
				System.out.println(line);
			}
			
			StringBuilder curStr = new StringBuilder();

			for (int j = 0; j < line.length(); j++) {
				int c = line.charAt(j);

				if (delimiterCharacters.contains(c)) {
					
					addWord(curStr, pos, allWords);
					pos += curStr.length() + 1;
					curStr = new StringBuilder();
				} else {
					curStr.append((char) c);
				}
			}
			pos += curStr.length();
			pos += NEWLINE_CHARCOUNT;
		}
	}

	private void addWord(StringBuilder curStr, int pos, Map<String, Word> allWords) {
		String entireWord = curStr.toString();
		if (allWords.containsKey(entireWord)) {
			Word word = allWords.get(entireWord);
			word.wordcnt++;
			word.occurrances.add(pos);
		} else {
			allWords.put(entireWord, new Word("", entireWord, pos));
		}
	}
	
	/**
	 * Helper to convert a byte array to an inte value
	 */
	static Integer convertToInt(byte[] asBytes) {
		return new BigInteger(asBytes).intValue();
	}
	
	/**
	 * Convert an inte to a bytearray with length 4 
	 */
	static byte[] convertToByte(int maxValue) {
		byte[] arr = BigInteger.valueOf(maxValue).toByteArray();
		int shift = 4 - arr.length;
		byte[] ret = new byte[4];
		for (int i = 0; i < arr.length; i++) {
			ret[i + shift] = arr[i];
		}
		return ret;
	}

	private String getDataFromIdx(Integer idx) throws Exception {
		
		dataFileRandomAccessReader.seek(idx - 20);
		byte[] buf = new byte[50];
		dataFileRandomAccessReader.read(buf);
		
		String ret = new String(buf, CHARSET).replace("\n", " ");
		
		return ret;
	}
}
