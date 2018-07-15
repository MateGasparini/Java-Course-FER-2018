package hr.fer.zemris.java.gui.charts;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class containing all needed information for constructing
 * a bar chart (see {@link BarChartDemo} for demonstration).
 * 
 * @author Mate Gasparini
 */
public class BarChart {
	
	/**
	 * List of all values shown in the chart.
	 */
	private List<XYValue> values;
	/**
	 * Textual description for the x-axis.
	 */
	private String descriptionX;
	/**
	 * Textual description for the y-axis.
	 */
	private String descriptionY;
	/**
	 * Starting value of the y-axis.
	 */
	private int minY;
	/**
	 * Ending value of the y-axis.
	 */
	private int maxY;
	/**
	 * Step between each two neighboring y-axis values.
	 */
	private int step;
	
	/**
	 * Class constructor specifying all needed information for creating the chart.
	 * 
	 * @param values The specified list of values.
	 * @param descriptionX The specified x-axis description.
	 * @param descriptionY The specified y-axis description.
	 * @param minY The specified starting value of the y-axis.
	 * @param maxY The specified ending value of the y-axis.
	 * @param step The specified step between each two neighboring y-axis values.
	 */
	public BarChart(List<XYValue> values, String descriptionX, String descriptionY,
			int minY, int maxY, int step) {
		checkValues(values);
		this.descriptionX = descriptionX;
		this.descriptionY = descriptionY;
		checkMinYMaxY(minY, maxY);
		checkStep(step);
	}
	
	/**
	 * Returns the list of all values.
	 * 
	 * @return The list containing all values.
	 */
	public List<XYValue> getValues() {
		return values;
	}
	
	/**
	 * Returns the x-axis description.
	 * 
	 * @return The x-axis description.
	 */
	public String getDescriptionX() {
		return descriptionX;
	}
	
	/**
	 * Returns the y-axis description.
	 * 
	 * @return The y-axis description.
	 */
	public String getDescriptionY() {
		return descriptionY;
	}
	
	/**
	 * Returns the starting value of the y-axis.
	 * 
	 * @return The starting value of the y-axis.
	 */
	public int getMinY() {
		return minY;
	}
	
	/**
	 * Returns the ending value of the y-axis.
	 * 
	 * @return The ending value of the y-axis.
	 */
	public int getMaxY() {
		return maxY;
	}
	
	/**
	 * Returns the step between each two neighboring y-axis values.
	 * 
	 * @return The step between each two neighboring y-axis values.
	 */
	public int getStep() {
		return step;
	}
	
	/**
	 * Checks if the given list of values is not null and is not empty.<br>
	 * If valid, it is also sorted and its reference is stored.
	 * 
	 * @param values The given list of all values.
	 */
	private void checkValues(List<XYValue> values) {
		this.values = Objects.requireNonNull(values, "Values must not be null");
		if (values.size() == 0) {
			throw new IllegalArgumentException("Chart must contain at least one value.");
		}
		Collections.sort(values, (v1, v2) -> Integer.compare(v1.getX(), v2.getX()));
	}
	
	/**
	 * Checks if the given starting and ending y-axis values are valid.<br>
	 * If {@code minY} is bigger than {@code maxY}, they are switched.<br>
	 * If they are equal, then new values are calculated using the list
	 * of all values.
	 * 
	 * @param minY The given starting y-axis value.
	 * @param maxY The given ending y-axis value.
	 */
	private void checkMinYMaxY(int minY, int maxY) {
		if (minY < maxY) {
			this.minY = minY;
			this.maxY = maxY;
		} else if (minY > maxY) {
			this.minY = maxY;
			this.maxY = minY;
		} else {
			// Find min and max values of Y.
			int realMinY = 0;
			int realMaxY = 0;
			
			for (XYValue value : values) {
				int currentY = value.getY();
				if (currentY < realMinY) {
					realMinY = currentY;
				}
				if (currentY > realMaxY) {
					realMaxY = currentY;
				}
			}
			
			if (realMinY == realMaxY) {
				if (realMaxY > 0) {
					realMinY = 1;
				}
			}
			
			this.minY = realMinY - 1;
			this.maxY = realMaxY;
		}
	}
	
	/**
	 * Checks if the given step value is valid.<br>
	 * If it is bigger than the y-axis range, it is set to that range value.<br>
	 * If the y-axis range is not divisible by the given step value, it is
	 * set to the first bigger integer that will fulfill this condition.
	 * 
	 * @param step The given step value.
	 */
	private void checkStep(int step) {
		int range = maxY - minY;
		if (step > range) {
			this.step = range;
		} else {
			while (range % step != 0) {
				step ++;
			}
			this.step = step;
		}
	}
}
