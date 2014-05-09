package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Jens Staahl
 */

public class closestpair2 {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	class Point {
		double x, y;

		public Point(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return x + " " + y;
		}

		double dot(Point other) {
			return x * other.x + y * other.y;
		}

		Point sub(Point other) {
			return new Point(x - other.x, y - other.y);
		}

		Point add(Point other) {
			return new Point(x + other.x, y + other.y);
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

		private closestpair2 getOuterType() {
			return closestpair2.this;
		}

	}

	// Just solves the acutal kattis-problem
	ZKattio io;

	int[] time = new int[5];

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		while (true) {
			long t0 = System.currentTimeMillis();
			TreeMap<Double, Point> set = new TreeMap<Double, Point>();
			int n = io.getInt();
			if (n == 0) {
				break;
			}
			time[3] -= System.currentTimeMillis();
			List<Point> pts = new ArrayList<Point>();
			for (int i = 0; i < n; i++) {
				pts.add(new Point(io.getDouble(), io.getDouble()));
			}
			time[3] += System.currentTimeMillis();
			time[0] -= System.currentTimeMillis();
			Collections.sort(pts, new Comparator<Point>() {
				@Override
				public int compare(Point o1, Point o2) {
					if (o1.x < o2.x) {
						return -1;
					}
					if (o1.x > o2.x) {
						return 1;
					}
					return 0;
				}
			});
			time[0] += System.currentTimeMillis();
			double h = pts.get(0).dist(pts.get(1));
			Point fst = pts.get(0), snd = pts.get(1);
			int back = 0;
			set.put(pts.get(0).y, pts.get(0));
			set.put(pts.get(1).y, pts.get(1));

			time[1] -= System.currentTimeMillis();
			for (int i = 2; i < n; i++) {
				if (h < 1e-10) {
					break;
				}
				Point cur = pts.get(i);
				while (pts.get(i).x - pts.get(back).x >= h && back < i) {
					Point point = set.get(pts.get(back).y);
					if (point.x == pts.get(back).x) {
						set.remove(pts.get(back).y);
					}
					back++;
				}
//				time[2] -= System.currentTimeMillis();
				SortedMap<Double, Point> subSet = set.tailMap(cur.y - h);
//				time[2] += System.currentTimeMillis();
				double limit = cur.y + h;
//				time[2] -= System.currentTimeMillis();
				for (Entry<Double, Point> pp : subSet.entrySet()) {
					Point point = pp.getValue();
					if (point.y > limit) {
						break;
					}
					if (cur.dist(point) < h) {
						h = cur.dist(point);
						fst = cur;
						snd = point;
					}
				}
//				time[2] += System.currentTimeMillis();
				set.put(cur.y, cur);
			}

			time[1] += System.currentTimeMillis();
			out.write(fst + " " + snd + "\n");
			if (test) {
				out.write((System.currentTimeMillis() - t0) + "\n");
				for (int i = 0; i < time.length; i++) {
					out.write("Timer " + i + ": " + time[i] + "\n");
				}
			}

		}
		out.flush();
	}

	public static void main(String[] args) throws Throwable {
		new closestpair2().solve();
	}

	public closestpair2() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}