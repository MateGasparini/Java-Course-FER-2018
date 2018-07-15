package hr.fer.zemris.java.hw05.db;

/**
 * Class providing different implementations
 * of the {@code IComparisonOperator} interface.
 * 
 * @author Mate Gasparini
 */
public class ComparisonOperators {
	
	/**
	 * Checks if the first value is less than the second value.
	 */
	public static final IComparisonOperator LESS;
	/**
	 * Checks if the first value is less or equal to the second value.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS;
	/**
	 * Checks if the first value is greater than the second value.
	 */
	public static final IComparisonOperator GREATER;
	/**
	 * Checks if the first value is greater or equal to the second value.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS;
	/**
	 * Checks if the first value is equal to the second value.
	 */
	public static final IComparisonOperator EQUALS;
	/**
	 * Checks if the first value is not equal to the second value.
	 */
	public static final IComparisonOperator NOT_EQUALS;
	/**
	 * Checks if the first value matches the second value
	 * (which can contain zero or one '*' wildcard character).
	 */
	public static final IComparisonOperator LIKE;
	
	static {
		LESS = (value1, value2) -> value1.compareTo(value2) < 0;
		LESS_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) <= 0;
		GREATER = (value1, value2) -> value1.compareTo(value2) > 0;
		GREATER_OR_EQUALS = (value1, value2) -> value1.compareTo(value2) >= 0;
		EQUALS = (value1, value2) -> value1.compareTo(value2) == 0;
		NOT_EQUALS = (value1, value2) -> value1.compareTo(value2) != 0;
		LIKE = new IComparisonOperator() {
			
			@Override
			public boolean satisfied(String value1, String value2) {
				if (value2.indexOf('*') != value2.lastIndexOf('*')) {
					throw new IllegalArgumentException(
						"At most one wildcard character is allowed."
					);
				}
				
				return value1.matches(value2.replace("*", ".*"));
			}
		};
	}
}
