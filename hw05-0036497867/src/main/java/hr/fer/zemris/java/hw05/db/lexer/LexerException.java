package hr.fer.zemris.java.hw05.db.lexer;

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
	private static final long serialVersionUID = -4803443023235175763L;
	
	/**
	 * Constructs a {@code LexerException} with no detail message.
	 */
	public LexerException() {
		super();
	}
	
	/**
     * Constructs a {@code LexerException} with the
     * specified detail message.
     *
     * @param message The detail message.
     */
	public LexerException(String message) {
		super(message);
	}
}
