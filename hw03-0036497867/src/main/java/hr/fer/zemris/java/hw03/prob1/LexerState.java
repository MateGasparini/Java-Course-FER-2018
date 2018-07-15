package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeration specifying the behavior of <code>Lexer</code>.
 * 
 * @author Mate Gasparini
 */
public enum LexerState {
	/**
	 * In this state, escape sequences are allowed.
	 */
	BASIC,
	/**
	 * In this state, escape sequences are ignored
	 * and numbers are treated as words.
	 */
	EXTENDED
}
