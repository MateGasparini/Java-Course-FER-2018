package hr.fer.zemris.java.voting.model;

/**
 * Class which models a voting poll (corresponding to the Polls votingDB table).
 * 
 * @author Mate Gasparini
 */
public class Poll {
	
	/** Poll's ID. */
	private long id;
	
	/** Poll's title. */
	private String title;
	
	/** Poll's message explaining the user how to vote. */
	private String message;
	
	/**
	 * Constructor specifying the poll's ID, title and message.
	 * 
	 * @param id The specified poll's ID.
	 * @param title The specified poll's title.
	 * @param message The specified poll's message.
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = title;
		this.message = message;
	}
	
	/**
	 * Returns the poll's ID.
	 * 
	 * @return The poll's ID.
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Returns the poll's title.
	 * 
	 * @return The poll's title.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the poll's message.
	 * 
	 * @return The poll's message.
	 */
	public String getMessage() {
		return message;
	}
}
