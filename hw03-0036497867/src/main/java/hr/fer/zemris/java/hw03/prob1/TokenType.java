package hr.fer.zemris.java.hw03.prob1;

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
	 * One or more letters, or in <code>EXTENDED</code> mode, everything
	 * except the <code>CHANGE_STATE_SYMBOL</code>.
	 */
	WORD,
	/**
	 * Non-negative long number.
	 */
	NUMBER,
	/**
	 * Every character that is not a letter, nor a digit.
	 */
	SYMBOL
}
