package hr.fer.zemris.lsystems.impl;

/**
 * Thrown to indicate that the application has attempted to
 * configure the <code>LSystemBuilder</code> with invalid data.
 * 
 * @author Mate Gasparini
 */
public class LSystemBuilderException extends RuntimeException {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = -2641350571258869139L;
	
	/**
	 * Constructs a <code>LSystemBuilderException</code>
	 * with no detail message.
	 */
	public LSystemBuilderException() {
		super();
	}
	
	/**
     * Constructs a <code>LSystemBuilderException</code> with the
     * specified detail message.
     *
     * @param message The detail message.
     */
	public LSystemBuilderException(String message) {
		super(message);
	}
}
