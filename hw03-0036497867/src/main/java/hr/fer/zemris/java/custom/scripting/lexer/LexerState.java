package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration specifying the behavior of <code>Lexer</code>.
 * 
 * @author Mate Gasparini
 */
public enum LexerState {
	/**
	 * In this state, everything is read as a text token.
	 * Valid escape sequences are "\\" and "\{".
	 */
	TEXT,
	/**
	 * In this state, reading of special non-text tokens is available and
	 * blank characters are skipped.
	 * Valid escape sequences are "\\", "\"", "\n", "\r" and "\t".
	 */
	TAG
}
