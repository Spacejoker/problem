public class Planet implements Cloneable {
	
	private double offencePosition;
	private int expectedUnitCount;
	
	public int getExpectedUnitCount() {
		return expectedUnitCount;
	}

	public void setExpectedUnitCount(int expectedUnitCount) {
		this.expectedUnitCount = expectedUnitCount;
	}

	public double getOffencePosition() {
		return offencePosition;
	}

	public void setOffencePosition(double offencePosition) {
		this.offencePosition = offencePosition;
	}

	public int getPlanetID() {
		return planetID;
	}

	public void setPlanetID(int planetID) {
		this.planetID = planetID;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getNumShips() {
		return numShips;
	}

	public void setNumShips(int numShips) {
		this.numShips = numShips;
	}

	public int getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(int growthRate) {
		this.growthRate = growthRate;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	// Initializes a planet.
	public Planet(int planetID, int owner, int numShips, int growthRate,
			double x, double y) {
		this.planetID = planetID;
		this.owner = owner;
		this.numShips = numShips;
		this.growthRate = growthRate;
		this.x = x;
		this.y = y;
	}

	// Accessors and simple modification functions. These should be mostly
	// self-explanatory.
	public int PlanetID() {
		return planetID;
	}

	public int Owner() {
		return owner;
	}

	public int NumShips() {
		return numShips;
	}

	public int GrowthRate() {
		return growthRate;
	}

	public double X() {
		return x;
	}

	public double Y() {
		return y;
	}

	public void Owner(int newOwner) {
		this.owner = newOwner;
	}

	public void NumShips(int newNumShips) {
		this.numShips = newNumShips;
	}

	public void AddShips(int amount) {
		numShips += amount;
	}

	public void RemoveShips(int amount) {
		numShips -= amount;
	}

	private int planetID;
	private int owner;
	private int numShips;
	private int growthRate;
	private double x, y;

	private Planet(Planet _p) {
		planetID = _p.planetID;
		owner = _p.owner;
		numShips = _p.numShips;
		growthRate = _p.growthRate;
		x = _p.x;
		y = _p.y;
	}

	public Object clone() {
		return new Planet(this);
	}

	public void modExpectedUnitCount(int numShips) {
		expectedUnitCount += numShips;
	}
}
