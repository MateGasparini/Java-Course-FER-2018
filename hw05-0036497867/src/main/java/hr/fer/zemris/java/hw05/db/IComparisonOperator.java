package hr.fer.zemris.java.hw05.db;

/**
 * A strategy with a single method which checks if a comparison
 * between some values is satisfied.
 * 
 * @author Mate Gasparini
 */
public interface IComparisonOperator {
	
	/**
	 * Returns {@code true} if a comparison between the first and the second
	 * given value is satisfied, and {@code false} otherwise.
	 * 
	 * @param value1 The first value.
	 * @param value2 The second value.
	 * @return {@code true} if the comparison is satisfied.
	 */
	public boolean satisfied(String value1, String value2);
}
