package algos;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * @author Jens Staahl, Rikard Blixt, Anders Ye
 */

class Rational {
	long nom, denom;

	public Rational(long nom, long denom) {
		super();
		
		if(nom < 0 && denom < 0){
			nom *= -1;
			denom *= -1;
		}
		if(nom > 0 && denom < 0){
			nom *= -1;
			denom *= -1;
		}
		
		long div = Math.abs( GCD(nom, denom));
		
		this.nom = nom/div;
		this.denom = denom/div;
	}

	public Rational add(Rational r){
		
		long div = GCD(r.denom, denom);
		
		long _nom = nom*r.denom/div;
		long _denom = denom*r.denom/div;
		long _rnom = r.nom*denom/div;
		
		return new Rational(_nom + _rnom, _denom);
	}
	
	public Rational subtract(Rational r){
		
		long div = GCD(r.denom, denom);
		
		long _nom = nom*r.denom/div;
		long _denom = denom*r.denom/div;
		long _rnom = r.nom*denom/div;
		
		return new Rational(_nom - _rnom, _denom);
	}
	
	public Rational multi(Rational r){
		return new Rational(r.nom * nom , r.denom * denom);
	}
	public Rational div(Rational r){
		return new Rational(nom * r.denom, denom*r.nom);
	}
	
	public long GCD(long a, long b) {
		if (b == 0)
			return a;
		return GCD(b, a % b);
	}

	@Override
	public String toString() {
		return nom + " / " + denom ;
	}
}

public class Arithmetic {

	// some local config
	static boolean test = false;
	static String testDataFile = "testdata.txt";
	private static String ENDL = "\n";

	

	// Just solves the acutal kattis-problem
	ZKattio io;

	private void solve() throws Throwable {
		io = new ZKattio(stream);
		
		int n = io.getInt();
		for (int i = 0; i < n; i++) {
			Rational r1 = new Rational(io.getInt(), io.getInt());
			String op = io.getWord();
			Rational r2 = new Rational(io.getInt(), io.getInt());
			if(op.equals("+")){
				io.write(r1.add(r2) + "\n");
			}
			if(op.equals("-")){
				io.write(r1.subtract(r2) + "\n");
			}
			if(op.equals("*")){
				io.write(r1.multi(r2) + "\n");
			}
			if(op.equals("/")){
				io.write(r1.div(r2) + "\n");
			}
			
		}
		io.flush();
	}

	public static void main(String[] args) throws Throwable {
		new Arithmetic().solve();
	}

	public Arithmetic() throws Throwable {
		if (test) {
			stream = new FileInputStream(testDataFile);
		}
	}

	InputStream stream = System.in;
	BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));// outStream = System.out;

}