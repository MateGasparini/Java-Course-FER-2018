package hr.fer.zemris.java.hw07.shell.parser;

/**
 * Thrown to indicate that the application has attempted to parse
 * some command's arguments, but failed.
 * 
 * @author Mate Gasparini
 */
public class ArgumentParserException extends RuntimeException {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -7760208000316752438L;
	
	/**
	 * Constructs a {@code ArgumentParserException} with no detail message.
	 */
	public ArgumentParserException() {
		super();
	}
	
	/**
	 * Constructs a {@code ArgumentParserException} with the specified
	 * detail message.
	 * 
	 * @param message The detail message.
	 */
	public ArgumentParserException(String message) {
		super(message);
	}
}
