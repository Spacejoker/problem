
	private Point vec(Point a, Point b) {
        return new Point(b.x - a.x, b.y - a.y);
	}

	private double cross(Point a, Point b) {
		return a.x * b.y - b.x * a.y;
	}
