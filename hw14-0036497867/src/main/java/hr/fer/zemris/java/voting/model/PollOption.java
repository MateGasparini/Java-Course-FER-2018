package hr.fer.zemris.java.voting.model;

/**
 * Class which models a voting option (corresponding to the PollOptions votingDB table).
 * 
 * @author Mate Gasparini
 */
public class PollOption {
	
	/** Poll option's ID. */
	private long id;
	
	/** Poll option's title. */
	private String title;
	
	/** Link to some information about the poll option. */
	private String link;
	
	/** ID of the corresponding poll this option belongs to. */
	private long pollId;
	
	/** Number of registered votes for this poll option. */
	private long votesCount;
	
	/**
	 * Constructor specifying all needed poll option information.
	 * 
	 * @param id The specified poll option's ID.
	 * @param title The specified poll option's title.
	 * @param link The specified link to some information about the poll option.
	 * @param pollId The specified ID of the corresponding poll this option belongs to.
	 * @param votesCount The specified number of registered votes for this poll option.
	 */
	public PollOption(long id, String title, String link, long pollId, long votesCount) {
		this.id = id;
		this.title = title;
		this.link = link;
		this.pollId = pollId;
		this.votesCount = votesCount;
	}
	
	/**
	 * Returns the poll option's ID.
	 * 
	 * @return The poll option's ID.
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Returns the poll option's title.
	 * 
	 * @return The poll option's title.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the poll option's link.
	 * 
	 * @return The poll option's link.
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * Returns the poll option's corresponding poll ID.
	 * 
	 * @return The poll option's corresponding poll ID.
	 */
	public long getPollId() {
		return pollId;
	}
	
	/**
	 * Returns the poll option's votes count.
	 * 
	 * @return The poll option's votes count.
	 */
	public long getVotesCount() {
		return votesCount;
	}
}
