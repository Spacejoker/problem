package pe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PE_81 {

	public PE_81() throws Throwable{
		
		int[][] matrix = new int[80][80];
		int[][] best = new int[80][80];
		
		BufferedReader r = new BufferedReader(new FileReader(new File("matrix.txt")));
		
		for (int i = 0; i < 80; i++) {
			String[] s = r.readLine().split(",");
			for (int j = 0; j < s.length; j++) {
				matrix[i][j] = Integer.valueOf(s[j]);
			}
		}
		best[0][0] = matrix[0][0];
		for (int i = 1; i < best.length; i++) {
			best[0][i] = matrix[0][i] + best[0][i-1];
			best[i][0] = matrix[i][0] + best[i-1][0];
		}
		
		for (int i = 1; i < best.length; i++) {
			for (int j = 1; j < best.length; j++) {
				best[i][j] = matrix[i][j] + Math.min(best[i-1][j], best[i][j-1]);
			}
		}
		
		System.out.println(best[79][79]);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Throwable {
		new PE_81();
	}

}
