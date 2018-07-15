package hr.fer.zemris.java.tecaj_13.dao;

/**
 * Thrown to indicate that the application requested some information
 * from the database using the {@link DAO} interface, but failed.
 * 
 * @author Mate Gasparini
 */
public class DAOException extends RuntimeException {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
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
}