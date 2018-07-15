package hr.fer.zemris.java.hw03.prob1;

/**
 * Thrown to indicate that the lexer has attempted to tokenize
 * some data, but a problem occurred.
 * 
 * @author Mate Gasparini
 */
public class LexerException extends RuntimeException {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -8689371241741643179L;
	
	/**
	 * Constructs a <code>LexerException</code> with no detail message.
	 */
	public LexerException() {
		super();
	}
	
	/**
     * Constructs a <code>LexerException</code> with the
     * specified detail message.
     *
     * @param message The detail message.
     */
	public LexerException(String message) {
		super(message);
	}
}
