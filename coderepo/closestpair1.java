package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jens Staahl
 */

public class closestpair1 {

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

		private closestpair1 getOuterType() {
			return closestpair1.this;
		}

	}

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		while (true) {
			int n = io.getInt();
			if(n == 0){
				break;
			}
			int numBuckets = (int) (Math.sqrt(n)/2)+1;
			List[][] buckets = new List[numBuckets][numBuckets];
			for (int i = 0; i < buckets.length; i++) {
				for (int j = 0; j < buckets[i].length; j++) {
					buckets[i][j] = new ArrayList();
				}
			}
			for (int i = 0; i < n; i++) {
				Point p = new Point(io.getDouble(), io.getDouble());
				int xbuck = (int) ((p.x + 100000)/(200000)*numBuckets);
				int ybuck = (int) ((p.y + 100000)/(200000)*numBuckets);
				buckets[ybuck][xbuck].add(p);
			}
			double dmin = Integer.MAX_VALUE;
			Point a=null, b=null;
			for (int ybuck = 0; ybuck < buckets.length; ybuck++) {
				for (int xbuck = 0; xbuck < buckets.length; xbuck++) {
					for (int i = 0; i < buckets[ybuck][xbuck].size(); i++) {
						Point p = (Point) buckets[ybuck][xbuck].get(i);
						for (int xmod = -1; xmod <= 1; xmod++) {
							for (int ymod = -1; ymod <= 1; ymod++) {
								int newx = xbuck + xmod;
								int newy = ybuck + ymod;
								if(newx >= 0 && newx < numBuckets && newy >= 0 && newy < numBuckets){
									for (int other = 0; other < buckets[newy][newx].size(); other++) {
										if(newy == ybuck && newx == xbuck && other == i){
											continue;
										}
										Point p2 = (Point) buckets[newy][newx].get(other);
										if(p.dist(p2) < dmin){
											dmin = p.dist(p2);
											a = p;
											b = p2;
										}
									}
								}
							}
						}
					}
				}
			}
			out.write(a + " " + b + "\n");
		}
		out.flush();
	}

	public static void main(String[] args) throws Throwable {
		new closestpair1().solve();
	}

	public closestpair1() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}