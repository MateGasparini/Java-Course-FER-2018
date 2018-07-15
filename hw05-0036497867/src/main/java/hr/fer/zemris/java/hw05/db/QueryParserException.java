package hr.fer.zemris.java.hw05.db;

/**
 * Thrown to indicate that the parser has attempted to parse
 * some tokens from a query, but a problem occurred.
 * 
 * @author Mate Gasparini
 */
public class QueryParserException extends RuntimeException {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -6988700883884540813L;
	
	/**
	 * Constructs a {@code QueryParserException} with no detail message.
	 */
	public QueryParserException() {
		super();
	}
	
	/**
     * Constructs a {@code QueryParserException} with the
     * specified detail message.
     *
     * @param message The detail message.
     */
	public QueryParserException(String message) {
		super(message);
	}
}
