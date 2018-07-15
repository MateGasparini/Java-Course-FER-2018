package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Thrown to indicate that the application has attempted to access
 * a non-existent (non-mapped) stack from a multistack.
 * 
 * @author Mate Gasparini
 */
public class NonExistentStackException extends RuntimeException {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -3843058238341882830L;
	
	/**
	 * Constructs an {@code NonExistentStackException} with no detail message.
	 */
	public NonExistentStackException() {
		super();
	}
	
	/**
     * Constructs a {@code NonExistentStackException} with the
     * specified detail message.
     *
     * @param message The detail message.
     */
	public NonExistentStackException(String message) {
		super(message);
	}
}
