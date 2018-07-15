package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration specifying different types of tokens.
 * 
 * @author Mate Gasparini
 */
public enum TokenType {
	/**
	 * End of file, the last accessible token.
	 */
	EOF,
	/**
	 * Basic text, it appears when lexer is in the <code>TEXT</code> state.
	 */
	TEXT,
	/**
	 * Consists of two characters: '{' and '$'. It opens a tag.
	 */
	TAG_OPENED,
	/**
	 * Consists of two characters: '$' and '}'. It closes an opened tag.
	 */
	TAG_CLOSED,
	/**
	 * This is the '=' sign. It marks an empty =-tag.
	 */
	EQUALS_SIGN,
	/**
	 * Starts with a letter, and consists of letters, digits and underscores.
	 * It can be a variable, but also a tag keyword (FOR, END etc.).
	 */
	VARIABLE_NAME,
	/**
	 * Starts with the '@' character after which a letter follows and
	 * after that it consists of letters, digits and underscores.
	 */
	FUNCTION_NAME,
	/**
	 * Marks a mathematical operator inside of a tag.
	 * Valid operators are: '+', '-', '*', '/' and '^'.
	 */
	OPERATOR,
	/**
	 * Marks a constant integer value inside of a tag.
	 */
	INTEGER_CONSTANT,
	/**
	 * Marks a constant double value inside of a tag.
	 */
	DOUBLE_CONSTANT,
	/**
	 * Marks a string inside of a tag. It starts and ends in double quotes.
	 */
	STRING
}
