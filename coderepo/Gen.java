package algos;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Random;

public class Gen {

	public static void main(String[] args) throws Throwable {
		BufferedWriter writer = new BufferedWriter(new FileWriter("testdata.txt"));
		int n = 100000;
		writer.write(n + "\n");
		Random rng = new Random(System.currentTimeMillis());
		for (int i = 0; i < n; i++) {
			int a = rng.nextInt(2*n) - n;
			int b = rng.nextInt(2*n) - n;
			writer.write(a + " " + b + "\n");
		}
		writer.write("0\n");
		writer.flush();
	}
}
