import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.util.StringTokenizer;

import org.junit.Test;


public class TestHelpers {

	@Test
	public void test() {
		
		for (int i = 0; i < 10000; i++) {
			Integer val = KonkordansKonstruktion.convertToInt(KonkordansKonstruktion.convertToByte(i));
			if(val != i){
				fail("error!");
			}
		}
	}
	
	private static final String CHARSET = "ISO-8859-1";
	private boolean debug = true;
	private int numlines = 2;
	private static final String IDX_DELIMITER = "<<<";
	private static final String NAME_DELIMITER = "~>~";
	private static final String INDICES_FILE = "/var/tmp/jensst_konk_idx";
	private static final String NAME_TO_INDICES_MAP_FILE = "/var/tmp/jensst_konk_intern";
	private static final String DATA_FILE = "/info/adk12/labb1/korpus";
	private static final int NEWLINE_CHARCOUNT = 1;
	
	static byte[] convertToByte(int maxValue) {
		byte[] arr = BigInteger.valueOf(maxValue).toByteArray();
		int shift = 4 - arr.length;
		byte[] ret = new byte[4];
		for (int i = 0; i < arr.length; i++) {
			ret[i + shift] = arr[i];
		}
		return ret;
	}
	
	@Test
	public void charsetTest() throws Throwable{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(DATA_FILE)), CHARSET));
		
		String readLine = reader.readLine();
		StringTokenizer stringTokenizer = new StringTokenizer(readLine);
		String str= stringTokenizer.nextToken();
		
		System.out.println(str);
		
		for (int i = 2; i < 1000; i=i*2-1) {
			System.out.println("Was: " + i);
			byte[] b = writeread(i);
		}
		
//		System.out.println("oe" + new String(b));
	}

	private byte[] writeread(int value) throws IOException, FileNotFoundException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("tmp"));
		
		byte[] b = convertToByte(value);
		
//		byte[] b = new byte[]{0,0,4,-93};
		
		for (int i = 0; i < b.length; i++) {
			bufferedWriter.write(b[i]);
		}
//		bufferedWriter.write(new String(b, "ISO-8859-1"));
		bufferedWriter.close();
		
		FileWriter writer = new FileWriter("tmp");
		
		RandomAccessFile rnd = new RandomAccessFile("tmp", "r");
		rnd.read(b);
//		b.toString()
		String string = new String(b, CHARSET);
		System.out.println("STR: " + string);
		Integer convertToInt = convertToInt(b);
		System.out.println("As int: " + convertToInt);
		for (int i = 0; i < b.length; i++) {
			System.out.println(i + ":" + b[i]);
		}
		rnd.close();
		return b;
	}
	
	static Integer convertToInt(byte[] asBytes) {
		return new BigInteger(asBytes).intValue();
	}
	

}
