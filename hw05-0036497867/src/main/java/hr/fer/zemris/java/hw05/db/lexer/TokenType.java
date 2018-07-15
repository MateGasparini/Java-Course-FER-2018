package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Enumeration specifying different types of tokens.
 * 
 * @author Mate Gasparini
 */
public enum TokenType {
	
	/* Note that all simple operators are a type for themselves.
	 * This is (I think) better, because it is more elegant than
	 * making a single OPERATOR type, and then juggling with Strings
	 * later on to determine what the OPERATOR really is. */
	
	/**
	 * The last accessible token.
	 */
	EOF,
	/**
	 * The name of some record's attribute.
	 */
	ATTRIBUTE_NAME,
	/**
	 * The "<" operator.
	 */
	OPERATOR_LESS,
	/**
	 * The "<=" operator.
	 */
	OPERATOR_LESS_OR_EQUALS,
	/**
	 * The ">" operator.
	 */
	OPERATOR_GREATER,
	/**
	 * The ">=" operator.
	 */
	OPERATOR_GREATER_OR_EQUALS,
	/**
	 * The "=" operator.
	 */
	OPERATOR_EQUALS,
	/**
	 * The "!=" operator.
	 */
	OPERATOR_NOT_EQUALS,
	/**
	 * The operator which can be used for checking a pattern.
	 */
	OPERATOR_LIKE,
	/**
	 * A string (given inside quotes).
	 */
	STRING_LITERAL,
	/**
	 * A different operator which allows an intersection of multiple queries.
	 */
	AND_OPERATOR
}
