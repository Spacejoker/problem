package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Jens Staahl, Rikard Blixt
 */

public class CopyOfclosestpair2 {

	// some local config
	static boolean test = true;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	class Point {
		double x, y;
		int id;

		public Point(double x, double y, int id) {
			super();
			this.x = x;
			this.y = y;
			this.id = id;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}

		double dot(Point other) {
			return x * other.x + y * other.y;
		}

		Point sub(Point other) {
			return new Point(x - other.x, y - other.y, -1);
		}

		Point add(Point other) {
			return new Point(x + other.x, y + other.y, -1);
		}

		double norm() {
			return Math.sqrt(x * x + y * y);
		}

		double cross(Point other) {
			return x * other.y - other.x * y;
		}

		double dist(Point other) {
			double xdiff = x - other.x;
			double ydiff = y - other.y;
			return Math.sqrt(xdiff * xdiff + ydiff * ydiff);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			long temp;
			temp = Double.doubleToLongBits(x);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(y);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
				return false;
			if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
				return false;
			return true;
		}

		private CopyOfclosestpair2 getOuterType() {
			return CopyOfclosestpair2.this;
		}

	}

	double d = Long.MAX_VALUE / 2;
	Point a, b;
	long[] timer = new long[5];

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		while (true) {
			int n = io.getInt();

			if (n == 0) {
				break;
			}
			long t0 = -System.currentTimeMillis();
			List<Point> pts = new ArrayList<Point>();
			for (int i = 0; i < n; i++) {
				pts.add(new Point(io.getDouble(), io.getDouble(), i));
			}
			Collections.sort(pts, new Comparator<Point>() {
				@Override
				public int compare(Point o1, Point o2) {
					return Double.compare(o1.x, o2.x);
				}
			});
			d = pts.get(0).dist(pts.get(1)) + 1;
			mindist(pts, 0, pts.size() - 1);
			out.write(a + " " + b + "\n");
			if (test) {

				for (int i = 0; i < timer.length; i++) {
					out.write("timer " + i + ": " + timer[i] + "\n");
				}
				out.write("Total: " + (System.currentTimeMillis() + t0));
			}
		}
		out.flush();
	}

	private void mindist(List<Point> pts, int left, int right ) {
		if (left == right) {
			return;// new ArrayList();
		}
		if (right == left + 1) {
			Point fst = pts.get(left);
			Point snd = pts.get(right);
			double newd = fst.dist(snd);
			if (newd < d) {
				d = newd;
				a = pts.get(left);
				b = pts.get(right);
			}
			List ret = new ArrayList();

			if (fst.y < snd.y) {

				ret.add(fst);
				ret.add(snd);
			} else {
				ret.add(snd);
				ret.add(fst);
			}
			return;
		}

		int mida = left + (right - left) / 2;
		int midb = mida + 1;
		mindist(pts, left, mida);
		mindist(pts, midb, right);
		double x = pts.get(mida).x;
		double xx = pts.get(midb).x;

		timer[0] -= System.currentTimeMillis();
		List<Point> dd = new ArrayList();
		for (int i = midb; i < pts.size(); i++) {
			Point point = pts.get(i);
			if (x - point.x > d) {
				break;
			}
			dd.add(point);
		}
		for (int i = mida; i >= 0; i--) {
			Point point = pts.get(i);
			if (point.x - xx > d) {
				break;
			}
			dd.add(point);
		}
		timer[1] -= System.currentTimeMillis();
		Collections.sort(dd, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				return Double.compare(o1.y, o2.y);
			}
		});
		timer[1] += System.currentTimeMillis();
		for (int i = 0; i < dd.size(); i++) {
			for (int j = i + 1; j <= i + 8 && j < dd.size(); j++) {
				if (Math.abs(dd.get(i).y - dd.get(j).y) > d) {
					break;
				}
				double dist = dd.get(i).dist(dd.get(j));
				if (dist < d) {
					d = dist;
					a = dd.get(i);
					b = dd.get(j);
				}
			}
		}
		timer[0] += System.currentTimeMillis();
	}

	private void test(List<Point> pts, int left, int right) {

	}

	public static void main(String[] args) throws Throwable {
		new CopyOfclosestpair2().solve();
	}

	public CopyOfclosestpair2() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}