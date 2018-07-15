package hr.fer.zemris.java.voting.dao;

import java.util.List;

import hr.fer.zemris.java.voting.model.Poll;
import hr.fer.zemris.java.voting.model.PollOption;

/**
 * Interface towards the data persistence layer.
 * 
 * @author Mate Gasparini
 */
public interface DAO {
	
	/**
	 * Returns a {@code List} of {@link Poll} references corresponding
	 * to all rows from the <i>Polls</i> table.
	 * 
	 * @return A {@code List} of all {@link Poll}s.
	 * @throws DAOException If an error occurs.
	 */
	public List<Poll> getPolls() throws DAOException;
	
	/**
	 * Returns a {@link Poll} corresponding to the row from the <i>Polls</i> table
	 * with the specified ID.
	 * 
	 * @param id The specified ID.
	 * @return The corresponding {@link Poll}.
	 * @throws DAOException If an error occurs.
	 */
	public Poll getPoll(long id) throws DAOException;
	
	/**
	 * Returns a {@code List} of {@link PollOption} references corresponding
	 * to all rows from the <i>PollOptions</i> table with the specified PollID.
	 * 
	 * @param pollId The specified PollID.
	 * @return A {@code List} of all corresponding {@link PollOption}s.
	 * @throws DAOException If an error occurs.
	 */
	public List<PollOption> getPollOptions(long pollId) throws DAOException;
	
	/**
	 * Returns a {@link PollOption} corresponding to the row from the
	 * <i>PollOptions</i> table with the specified ID.
	 * 
	 * @param id The specified ID.
	 * @return The corresponding {@link PollOption}.
	 * @throws DAOException If an error occurs.
	 */
	public PollOption getPollOption(long id) throws DAOException;
	
	/**
	 * Finds the {@link PollOption} corresponding to the specified ID in the
	 * <i>PollOptions</i> table and increments its <i>votesCount</i>.
	 * 
	 * @param id The specified ID.
	 * @throws DAOException If an error occurs.
	 */
	public void registerVote(long id) throws DAOException;
}
