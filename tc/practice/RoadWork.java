import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class RoadWork {

	class Point{
		int position;
		boolean start;
		public Point(int position, boolean start) {
			super();
			this.position = position;
			this.start = start;
		}
	}
	
	public int fraudFeet(int[] start, int[] end){
		List<Point> points = new ArrayList<Point>();
		
		for (int i = 0; i < start.length; i++) {
			points.add(new Point(start[i], true));
		}
		for (int i = 0; i < end.length; i++) {
			points.add(new Point(end[i], false));
		}
		Collections.sort(points, new Comparator<Point>() {

			@Override
			public int compare(Point o1, Point o2) {
				return o1.position - o2.position;
//				if(o1.position > o2.position)
//					return 1;
//				return -1;
			}
		});
		
		int starts = 0;
		int pos = 0;
		int res = 0;
		for (Point point : points) {
			if(point.start) starts ++;
			else starts --;
			
			if(starts == 2 && point.start == true){
				pos = point.position;
			} else if (starts == 1 && point.start == false){
				res += point.position - pos;
			}
		}
		
		
		return res;
	}

	static int[][] testCase = {{12,32,92},{991,161,1093}};
	
	public static void main(String[] args){
		System.out.println(new RoadWork().fraudFeet(testCase[0], testCase[1]));
		
//		System.out.println();	
	}
}
