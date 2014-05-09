package algos;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Jens Staahl
 */

public class maxcolinear {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		while (true) {
			int n = io.getInt();
			if (n == 0) {
				break;
			}
			Point[] pts = new Point[n];
			for (int i = 0; i < n; i++) {
				pts[i] = new Point(io.getInt(), io.getInt());
			}

			int max = 1;
			if (n > 1) {
				Arrays.sort(pts, new Comparator<Point>() {
					@Override
					public int compare(Point o1, Point o2) {
						return o1.x- o2.x;
					}
				});
				int cur = 0;
				int lastx = -Integer.MAX_VALUE;
				for (int i = 0; i < pts.length; i++) {
					cur ++;
					if(pts[i].x != lastx){
						cur = 1;
						lastx = pts[i].x;
					}
					max = Math.max(max, cur);
				}
				for (int a = 0; a < pts.length; a++) {
					List<Double> angles = new ArrayList(pts.length + 1);
					for (int b = 0; b < pts.length; b++) {
						if (a == b) {
							continue;
						}
						int nom = (pts[a].y - pts[b].y);
						int denom = (pts[a].x - pts[b].x);
						if(denom == 0){
							continue;
						}
//						int gcd = BigInteger.valueOf(Math.abs(nom)).gcd(BigInteger.valueOf(Math.abs(denom))).intValue();
//						nom /= gcd;
//						denom /= gcd;
						angles.add((double)(nom)/(double)(denom));
					}
					Collections.sort(angles);
//					Collections.sort(angles, new Comparator<Point>() {
//						@Override
//						public int compare(Point o1, Point o2) {
//							if(o1.x < o2.x){
//								return -1;
//							}
//							if(o2.x < o1.x){
//								return 1;
//							}
//							return o1.x - o2.x;
//						}
//					});
					double last = Long.MAX_VALUE;
					cur = 0;
					for (int i = 0; i < angles.size(); i++) {
						double d = angles.get(i);
						cur ++;
						if(Math.abs(last - d) > 1e-10){
							cur = 2;
							last = d;
						}
						max = Math.max(max, cur);
					}
				}
			}
			out.write((max) + "\n");
		}
		out.flush();
	}

	public static void main(String[] args) throws Throwable {
		new maxcolinear().solve();
	}

	public maxcolinear() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}