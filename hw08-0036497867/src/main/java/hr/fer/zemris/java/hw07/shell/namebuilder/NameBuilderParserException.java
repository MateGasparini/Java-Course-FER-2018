package hr.fer.zemris.java.hw07.shell.namebuilder;

/**
 * Thrown to indicate that the application has attempted to parse
 * some renaming expression, but failed.
 * 
 * @author Mate Gasparini
 */
public class NameBuilderParserException extends RuntimeException {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -2572133878307863494L;
	
	/**
	 * Constructs a {@code NameBuilderParserException} with no detail message.
	 */
	public NameBuilderParserException() {
		super();
	}
	
	/**
	 * Constructs a {@code NameBuilderParserException} with the specified
	 * detail message.
	 * 
	 * @param message The detail message.
	 */
	public NameBuilderParserException(String message) {
		super(message);
	}
}
