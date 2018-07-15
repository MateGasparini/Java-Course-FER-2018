package hr.fer.zemris.java.hw16.jvdraw.geometry.objects;

/**
 * Class representing a simple read-only 2-dimensional vector (point on the screen).
 * 
 * @author Mate Gasparini
 */
public class Point {
	
	/** The x coordinate. */
	private int x;
	
	/** The y coordinate. */
	private int y;
	
	/**
	 * Constructor specifying the x and y coordinates.
	 * 
	 * @param x The specified x coordinate.
	 * @param y The specified y coordinate.
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x coordinate.
	 * 
	 * @return The x coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y coordinate.
	 * 
	 * @return The y coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Returns the distance of this point to the point
	 * represented by the given coordinates.
	 * 
	 * @param x The given x coordinate.
	 * @param y The given y coordinate.
	 * @return The distance from the given point.
	 */
	public double distanceFrom(int x, int y) {
		int deltaX = x - this.x;
		int deltaY = y - this.y;
		return Math.sqrt(deltaX*deltaX + deltaY*deltaY);
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
