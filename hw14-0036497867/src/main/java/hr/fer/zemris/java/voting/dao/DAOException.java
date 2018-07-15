package hr.fer.zemris.java.voting.dao;

/**
 * Thrown to indicate that the application requested some information
 * from the DB using the {@link DAO} interface, but failed.
 * 
 * @author Mate Gasparini
 */
public class DAOException extends RuntimeException {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a {@link DAOException} with no detail message.
	 */
	public DAOException() {
		super();
	}
	
	/**
	 * Constructs a {@link DAOException} with the specified detail message, cause,
	 * suppression enabled or disabled, and writable stack trace enabled or disabled.
	 * 
	 * @param message The specified detail message.
	 * @param cause The specified cause.
	 * @param enableSuppression Whether or not suppression is enabled or disabled.
	 * @param writableStackTrace Whether or not the stack trace should be writable.
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
	/**
	 * Constructs a {@link DAOException} with the specified detail message and cause.
	 * 
	 * @param message The specified message.
	 * @param cause The specified cause.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructs a {@link DAOException} with the specified detail message.
	 * 
	 * @param message The detail message.
	 */
	public DAOException(String message) {
		super(message);
	}
	
	/**
	 * Constructs a {@link DAOException} with the specified cause.
	 * 
	 * @param cause The specified cause.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}