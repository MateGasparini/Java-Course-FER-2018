package hr.fer.zemris.java.hw07.shell;

/**
 * Thrown to indicate that the application has attempted to communicate
 * with the user, but failed.
 * 
 * @author Mate Gasparini
 */
public class ShellIOException extends RuntimeException {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = 7382308040682782708L;
	
	/**
	 * Constructs a {@code ShellIOException} with no detail message.
	 */
	public ShellIOException() {
		super();
	}
	
	/**
	 * Constructs a {@code ShellIOException} with the specified detail message.
	 * 
	 * @param message The detail message.
	 */
	public ShellIOException(String message) {
		super(message);
	}
}
