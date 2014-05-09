package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * @author Jens Staahl
 */

public class jez {

	// some local config
	static boolean test = true;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		int n = io.getInt(), m = io.getInt();
		long k = io.getLong();
		int mm = 1;
		if (m > n) {
			mm =0;
			int x = n;
			n = m;
			m = x;
		}
		int[] size = new int[n + m + 3];
		Arrays.fill(size, -1);
		int lo = 0, hi = n + m + 2;
		while (hi - lo > 1) {
			int mid = (lo + hi) / 2;
			long kk = k;
			if (mid < m) {
				kk += (m - 1) * (m) / 2 - ((m - mid) * (m - mid - 1)) / 2;
			} else {
				kk += (m - 1) * m / 2;
			}
			if (mid > n) {
				kk += (mid - n) * (mid - n + 1) / 2;
			}
			if (kk >= mid * m) {
				lo = mid;
			} else {
				hi = mid;
			}
		}
		{
			int mid = lo + 1;// (lo + hi) / 2;
			long kk = k;
			if (mid < m) {
				kk += (m - 1) * (m) / 2 - ((m - mid) * (m - mid - 1)) / 2;
			} else {
				kk += (m - 1) * m / 2;
			}
			if (mid > n) {
				kk += (mid - n) * (mid - n + 1) / 2;
			}
			if (kk >= mid * m) {
				lo = mid;
			}
		}
		int cur = 0;
		long used = 0;
		int lastrow = lo + 1;
		while (lo >= 0) {
			size[lo] = Math.min(cur, m);
			used += size[lo];
			cur++;
			lo--;
		}
		long kk = k - used;

		if (lastrow % 2 == (mm)) {
			if (lastrow > n) {
				kk += lastrow - n - 1;
			}
			for (int i = lastrow-1; kk > 0; i--) {
				if (i >= 0) {
					size[i]++;

				}
				kk--;
			}
		} else {
			if (lastrow < m) {
				kk += m - lastrow;
			}
			for (int i = lastrow - m; kk > 0; i++) {
				if (i >= 0) {
					size[i]++;
				}
				kk--;
			}
		}

		long ans = 0;
//		Stack<Boolean> bits = new Stack();
		long timer[] = new long[5];
		boolean[] bit = new boolean[21];
		for (int row = 1; row < n; row++) {

			long right = size[row]; // getRight(row, k, n, m);// 10; // todo
			long val = row;

			int idx = 0;
			long numzero = 0;
			timer[0] -= System.currentTimeMillis();
			numzero = 21;
			while (val > 0) {
				if (val % 2 == 1) {
					bit[idx] = true;
					numzero --;
				} else {
					bit[idx] = false;
//					numzero++;
				}
				idx++;
				val /= 2;
			}
			timer[0] += System.currentTimeMillis();

			long rowans = 0;
			timer[1] -= System.currentTimeMillis();
			for (int i = 20; i >= 0; i--) {
				idx = i+1;
				Boolean pop = bit[i];//bits.pop();

				if (!pop) {
					idx--;
					numzero--;
					continue;
				}
				long cycleLength = (1 << idx);
				long perCycle = (1 << idx) - (1 << numzero);
				long numCycles = right / (1 << idx);
				right %= cycleLength;
				if (right > cycleLength / 2) {
					rowans += right - cycleLength / 2;
					right = cycleLength / 2;
				}
				rowans += perCycle * numCycles;
				idx--;
			}
			timer[1] += System.currentTimeMillis();
			ans += rowans;
		}
		System.out.println(Arrays.toString(size));
		System.out.println(k - ans);
	}

	private int getRight(int row, long k, int n, int m) {
		long lo = 0, hi = m + 1;
		while (hi - lo > 1) {
			long mid = (hi + lo) / 2;
			long size = 0;

			long x = row + mid - 1;
			if (x <= m) {
				size = x * x / 2;
				if (row % 2 == 0) {
					size += mid;
				} else {
					size += x - mid + 1;
				}
			} else {
				size = x * m - m * m / 2 + (row % 2 == 0 ? mid : m - mid + 1);
			}
			if (size <= k) {
				lo = mid;
			} else {
				hi = mid;
			}
		}
		return (int) lo;
	}

	public static void main(String[] args) throws Throwable {
		new jez().solve();
	}

	public jez() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}