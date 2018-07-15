package hr.fer.zemris.java.gui.layouts;

/**
 * Thrown if the application tried to construct a {@code CalcLayout}
 * with invalid arguments and failed.
 * 
 * @author Mate Gasparini
 */
public class CalcLayoutException extends RuntimeException {
	
	/**
	 * Generated serial version ID.
	 */
	private static final long serialVersionUID = 8465025981115533808L;
	
	/**
	 * Constructs a {@code CalcLayoutException} with no detail message.
	 */
	public CalcLayoutException() {
		super();
	}
	
	/**
	 * Constructs a {@code CalcLayoutException} with the specified
	 * detail message.
	 * 
	 * @param message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
}
