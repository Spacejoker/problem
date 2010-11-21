package pe;

import java.util.ArrayList;
import java.util.List;

public class PE_91 {

	class Coord {
		double x;
		double y;

		public Coord(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Coord [x=" + x + ", y=" + y + "]";
		}

	}

	class Triangle {
		Coord a;
		Coord b;
		Coord c;

		public Triangle(Coord a, Coord b, Coord c) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}

		@Override
		public String toString() {
			return "Triangle [a=" + a + ", b=" + b + ", c=" + c + "]";
		}

	}

	List<Triangle> triangles = new ArrayList<Triangle>();

	public PE_91() {

		int dim = 3;
		long found = 0;
		int ax = 0;
		int ay = 0;
		// for (int ax = 0; ax < dim; ax++) {
		// for (int ay = 0; ay < dim; ay++) {
		for (int bx = 0; bx < dim; bx++) {
			for (int by = 0; by < dim; by++) {
				if (ax == bx && by == ay) {
					continue;
				}

				// calculate the two possible cx,cy and see if they are on the
				// grid

				double xDiff = bx - ax;
				double yDiff = by - ay;

				double cx = ((double) xDiff) / 2.0D - ((double) yDiff) / 2.0D
						+ ax;
				double cy = ((double) xDiff) / 2.0D + ((double) yDiff) / 2.0D
						+ ay;

				// if(cx == ax || cy == ay){
				// continue;
				// }

				if (Math.abs(cx - ((int) cx)) < 0.0001
						&& Math.abs(cy - ((int) cy)) < 0.0001 && cx >= 0
						&& cx <= dim && cy > 0 && cy < dim) { // is on the grid?

					// Create the basic triangle :

					Triangle t = new Triangle(new Coord(0, 0), new Coord(bx
							- ax, by - ay), new Coord(cx - ax, cy - ay));

					if (!triangles.contains(t)) {
						triangles.add(t);
						System.out.println("adding: " + t);
					}
					// System.out.println("Dots: (" +ax+ "," +ay+ "),(" +bx+ ","
					// +by+ "),(" +cx+ "," +cy+ ")");
					found++;
				}

				// cx = ((double)xDiff)/2.0D + ((double)yDiff)/2.0D;
				// cy = ((double)-1*xDiff)/2.0D + ((double)yDiff)/2.0D;
				//
				// if(Math.abs(cx - ((int)cx)) < 0.0001 && Math.abs(cy -
				// ((int)cy)) < 0.0001 &&
				// cx >= 0 && cx <= dim && cy > 0 && cy < dim){
				//
				// Triangle t = new Triangle(new Coord(0, 0), new Coord(bx-ax,
				// by-ay), new Coord(cx-ax, cy-ay));
				//
				// if(!triangles.contains(t)){
				// triangles.add(t);
				// System.out.println("adding: " + t);
				// }
				// // System.out.println("Dots: (" +ax+ "," +ay+ "),(" +bx+ ","
				// +by+ "),(" +cx+ "," +cy+ ")");
				// found ++;
				// }
			}
		}
		// }
		// }
		//
		System.out.println(found);
	}

	public static void main(String[] args) {
		new PE_91();
	}

}
