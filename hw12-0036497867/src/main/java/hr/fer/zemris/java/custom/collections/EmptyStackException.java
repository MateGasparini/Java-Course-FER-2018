package hr.fer.zemris.java.custom.collections;

/**
 * Thrown to indicate that the application has attempted to access
 * the top of a stack of objects, but the stack was empty.
 * 
 * @author Mate Gasparini
 */
public class EmptyStackException extends RuntimeException {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -461419548532405074L;
	
	/**
	 * Constructs a <code>EmptyStackException</code> with no detail message.
	 */
	public EmptyStackException() {
		super();
	}
	
	/**
     * Constructs a <code>EmptyStackException</code> with the
     * specified detail message.
     *
     * @param message The detail message.
     */
	public EmptyStackException(String message) {
		super(message);
	}
}
