package hr.fer.zemris.java.hw05.db;

/**
 * Class which models an expression consisting of an {@code IFieldValueGetter}
 * strategy, a string literal and an {@code IComparisonOperator} strategy.
 * 
 * @author Mate Gasparini
 */
public class ConditionalExpression {
	
	/**
	 * The expression's specified field value getter.
	 */
	private IFieldValueGetter fieldGetter;
	/**
	 * The expression's specified string literal.
	 */
	private String stringLiteral;
	/**
	 * The expression's specified comparison operator.
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor specifying the field value getter, the string literal
	 * and the comparison operator.
	 * 
	 * @param fieldGetter The specified field value getter.
	 * @param stringLiteral The specified string literal.
	 * @param comparisonOperator The specified comparison operator.
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral,
			IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}
	
	/**
	 * Returns the field value getter.
	 * 
	 * @return Reference to the field value getter.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}
	
	/**
	 * Returns the string literal.
	 * 
	 * @return Reference to the string literal.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}
	
	/**
	 * Returns the comparison operator.
	 * 
	 * @return Reference to the comparison operator.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
