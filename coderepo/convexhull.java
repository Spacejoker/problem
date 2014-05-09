package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * @author Jens Staahl
 */

public class convexhull {

	// some local config
	static boolean test = true;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	class Point implements Comparable<Point> {
		int x, y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		// sort first on x then on y
		public int compareTo(Point other) {
			if (x == other.x)
				return y - other.y;
			else
				return x - other.x;
		}

		// cross product of two vectors
		public int cross(Point p) {
			return x * p.y - y * p.x;
		}

		// subtraction of two points
		public Point sub(Point p) {
			return new Point(x - p.x, y - p.y);
		}

		long dot(Point other) {
			return x * other.x + y * other.y;
		}

		Point add(Point other) {
			return new Point(x + other.x, y + other.y);
		}

		double norm() {
			return Math.sqrt(x * x + y * y);
		}
	}

	public Point[] findHull(Point[] points) {
		int n = points.length;
		Arrays.sort(points);
		Point[] ans = new Point[2 * n]; // In between we may have a 2n points
		int k = 0;
		int start = 0; // start is the first insertion point

		for (int i = 0; i < n; i++) // Finding lower layer of hull
		{
			Point p = points[i];
			if (i > 0 && p.x == points[i - 1].x && p.y == points[i - 1].y) {
				continue;
			}
			while (k - start >= 2 && p.sub(ans[k - 1]).cross(p.sub(ans[k - 2])) > 0)
				k--;
			ans[k++] = p;
		}

		k--; // drop off last point from lower layer
		start = k;

		for (int i = n - 1; i >= 0; i--) // Finding top layer from hull
		{
			Point p = points[i];
			if (i > 0 && p.x == points[i - 1].x && p.y == points[i - 1].y) {
				continue;
			}
			while (k - start >= 2 && p.sub(ans[k - 1]).cross(p.sub(ans[k - 2])) > 0)
				k--;
			ans[k++] = p;
		}
		k--; // drop off last point from top layer

		return Arrays.copyOf(ans, k); // convex hull is of size k
	}

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
			Point[] findHull = null;
			if (n == 1) {
				findHull = pts;
			} else if (n == 2) {
				if(pts[0].equals(pts[1])){
					findHull = pts;
				} else {
					findHull = new Point[]{pts[0]};
				}

			} else {
				findHull = findHull(pts);
				int from = 0, to = 2;
				while(to != 1){
					if (distPointToLineSegment(pts[to - 1], pts[from], pts[to]) < 1e-10) {
						pts[to-1] = null;
					}
					to ++;
//					to %= find
					
				}
			}

			out.write(findHull.length + "\n");
			for (int i = 0; i < findHull.length; i++) {
				out.write(findHull[i].x + " " + findHull[i].y + "\n");
			}
		}

		out.flush();// ("\n");
	}

	double distPointToLineSegment(Point pt, Point A, Point B) {
		if (B.sub(A).dot(pt.sub(A)) < 0) {
			return pt.sub(A).norm();
		}
		if (A.sub(B).dot(pt.sub(B)) < 0) {
			return pt.sub(B).norm();
		}
		return Math.abs((pt.sub(A).cross(B.sub(A))) / A.sub(B).norm());
	}

	public static void main(String[] args) throws Throwable {
		new convexhull().solve();
	}

	public convexhull() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}