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
 * @author Jens Staahl
 */

class Light {
	public Light(int pos, int r, int g) {
		super();
		this.pos = pos;
		this.r = r;
		this.g = g;
	}

	@Override
	public String toString() {
		return "Light [pos=" + pos + ", r=" + r + ", g=" + g + "]";
	}

	int pos, r, g;
}
public class semafori {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt"; 
	private static String ENDL = "\n";

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		int n = io.getInt(), l = io.getInt();
		List<Light> pts = new ArrayList();
		for (int i = 0; i < n; i++) {
			pts.add(new Light(io.getInt(), io.getInt(), io.getInt()));
		}
		Collections.sort(pts, new Comparator<Light>() {

			@Override
			public int compare(Light o1, Light o2) {
				if(o1.pos < o2.pos){
					return -1;
				}
				if(o1.pos > o2.pos){
					return 1;
				}
				return 0;
			}
		});
		int pos = 0;
		int time = 0;
		for (int i = 0; i < pts.size(); i++) {
			Light light = pts.get(i);
			time += (light.pos - pos);
			int curCycle = time % (light.r + light.g);
			if(curCycle <= light.r){
				time += light.r - curCycle;
			}
			pos = light.pos;
		}
		time += l - pos;
		System.out.println(time);
	}

	public static void main(String[] args) throws Throwable {
		new semafori().solve();
	}

	public semafori() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}