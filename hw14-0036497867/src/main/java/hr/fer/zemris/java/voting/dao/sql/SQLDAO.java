package hr.fer.zemris.java.voting.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.voting.dao.DAO;
import hr.fer.zemris.java.voting.dao.DAOException;
import hr.fer.zemris.java.voting.model.Poll;
import hr.fer.zemris.java.voting.model.PollOption;

/**
 * {@link DAO} implementation which acquires the {@link Connection} through
 * the {@link SQLConnectionProvider#getConnection()} method.
 * 
 * @author Mate Gasparini
 */
public class SQLDAO implements DAO {
	
	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement(GET_POLLS)) {
			try (ResultSet rset = pst.executeQuery()) {
				while (rset != null && rset.next()) {
					polls.add(new Poll(rset.getLong(1), rset.getString(2), rset.getString(3)));
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Error loading poll information.", ex);
		}
		return polls;
	}
	
	@Override
	public Poll getPoll(long id) throws DAOException {
		Poll poll = null;
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement(GET_POLL)) {
			pst.setLong(1, Long.valueOf(id));
			try (ResultSet rset = pst.executeQuery()) {
				if (rset != null && rset.next()) {
					poll = new Poll(rset.getLong(1), rset.getString(2), rset.getString(3));
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Error loading poll information.", ex);
		}
		return poll;
	}
	
	@Override
	public List<PollOption> getPollOptions(long pollId) throws DAOException {
		List<PollOption> options = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement(GET_POLL_OPTIONS)) {
			pst.setLong(1, Long.valueOf(pollId));
			try (ResultSet rset = pst.executeQuery()) {
				while (rset != null && rset.next()) {
					PollOption option = new PollOption(
						rset.getLong(1), rset.getString(2), rset.getString(3),
						rset.getLong(4), rset.getLong(5)
					);
					options.add(option);
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Error loading poll options.", ex);
		}
		return options;
	}
	
	@Override
	public PollOption getPollOption(long id) throws DAOException {
		PollOption option = null;
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement(GET_POLL_OPTION)) {
			pst.setLong(1, Long.valueOf(id));
			try (ResultSet rset = pst.executeQuery()) {
				if (rset != null && rset.next()) {
					option = new PollOption(
						rset.getLong(1), rset.getString(2), rset.getString(3),
						rset.getLong(4), rset.getLong(5)
					);
				}
			}
		} catch(Exception ex) {
			throw new DAOException("Error loading poll information.", ex);
		}
		return option;
	}
	
	@Override
	public void registerVote(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		try (PreparedStatement pst = con.prepareStatement(REGISTER_VOTE)) {
			pst.setLong(1, id);
			int numberOfUpdates = pst.executeUpdate();
			if (numberOfUpdates == 0) {
				throw new DAOException("Update not possible.");
			}
		} catch(Exception ex) {
			throw new DAOException("Error registering vote.", ex);
		}
	}
	
	//-----------------------------------------//
	// SELF-EXPLANATORY SQL COMMANDS CONSTANTS //
	//-----------------------------------------//
	
	private static final String GET_POLLS =
		"select id, title, message from Polls order by id";
	private static final String GET_POLL =
		"select id, title, message from Polls where id=?";
	private static final String GET_POLL_OPTIONS =
		"select id, optiontitle, optionlink, pollid, votescount from PollOptions where pollid=?";
	private static final String GET_POLL_OPTION =
		"select id, optiontitle, optionlink, pollid, votescount from PollOptions where id=?";
	private static final String REGISTER_VOTE =
		"update PollOptions set votescount=votescount+1 where id=?";
}
