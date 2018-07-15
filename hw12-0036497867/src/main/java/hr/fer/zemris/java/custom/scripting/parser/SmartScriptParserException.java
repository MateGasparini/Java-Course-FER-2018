package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Thrown to indicate that the parser has attempted to parse
 * some tokens, but a problem occurred.
 * 
 * @author Mate Gasparini
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -7146405271722626823L;
	
	/**
	 * Constructs a <code>SmartScriptParserException</code> with no detail message.
	 */
	public SmartScriptParserException() {
		super();
	}
	
	/**
     * Constructs a <code>SmartScriptParserException</code> with the
     * specified detail message.
     *
     * @param message The detail message.
     */
	public SmartScriptParserException(String message) {
		super(message);
	}
	
	/**
     * Constructs a <code>SmartScriptParserException</code> with the
     * specified detail message and cause.
     *
     * @param message The detail message.
     * @param cause The cause of the exception.
     */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
