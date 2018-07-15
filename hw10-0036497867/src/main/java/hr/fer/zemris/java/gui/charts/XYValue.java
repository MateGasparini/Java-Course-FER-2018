package hr.fer.zemris.java.gui.charts;

/**
 * Class representing a pair of values containing read-only
 * x and y integer values.
 * 
 * @author Mate Gasparini
 */
public class XYValue {
	
	/**
	 * X value.
	 */
	private int x;
	/**
	 * Y value.
	 */
	private int y;
	
	/**
	 * Class constructor specifying the x and y values.
	 * 
	 * @param x The specified x value.
	 * @param y The specified y value.
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x value.
	 * 
	 * @return The x value.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y value.
	 * 
	 * @return The y value.
	 */
	public int getY() {
		return y;
	}
}
