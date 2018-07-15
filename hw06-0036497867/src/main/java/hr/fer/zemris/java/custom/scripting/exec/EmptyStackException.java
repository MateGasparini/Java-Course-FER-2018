package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Thrown to indicate that the application has attempted to access
 * the top of the stack, but the stack was empty.
 * 
 * @author Mate Gasparini
 */
public class EmptyStackException extends RuntimeException {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = 4297685169289267650L;
	
	/**
	 * Constructs an {@code EmptyStackException} with no detail message.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
     * Constructs a {@code EmptyStackException} with the
     * specified detail message.
     *
     * @param message The detail message.
     */
	public EmptyStackException(String message) {
		super(message);
	}
}
