package hr.fer.zemris.java.voting.servlets;

import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.voting.dao.DAOException;
import hr.fer.zemris.java.voting.dao.DAOProvider;
import hr.fer.zemris.java.voting.model.PollOption;

/**
 * Utility class used by different servlets in this subpackage.
 * 
 * @author Mate Gasparini
 */
public class GlasanjeUtil {
	
	/** Name of the {@link HttpServletRequest}'s attribute used for error messages. */
	public static final String ERROR_ATTRIBUTE = "error";
	
	/** Path to the JSP file used for showing error messages. */
	public static final String ERROR_JSP = "/WEB-INF/pages/greska.jsp";
	
	/**
	 * Private default constructor.
	 */
	private GlasanjeUtil() {
	}
	
	/**
	 * Reads the given {@link HttpServletRequest}'s parameter specified by the given
	 * parameter name.<br>
	 * If it is valid (i.e. {@code Long}), it is returned.<br>
	 * Otherwise, {@code null} is returned.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 * @param parameterName The given parameter name.
	 * @return The corresponding parameter value, or {@code null} if invalid or non-existent.
	 */
	public static Long readLongParameter(HttpServletRequest req, String parameterName) {
		String parameter = req.getParameter(parameterName);
		if (parameter == null) {
			req.setAttribute(ERROR_ATTRIBUTE, "Očekivan je "+parameterName+" parametar.");
			return null;
		}
		
		Long value;
		try {
			value = Long.parseLong(parameter);
		} catch (NumberFormatException ex) {
			req.setAttribute(ERROR_ATTRIBUTE, "Nevažeći "+parameterName+": '"+parameter+"'.");
			return null;
		}
		
		return value;
	}
	
	/**
	 * Returns the poll results (specified by the <i>pollID</i> URL parameter)
	 * sorted by their vote count.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 * @return The {@code List} of sorted {@link PollOption}s from the DB,
	 * 			or {@code null} if some kind of an error occurred.
	 */
	public static List<PollOption> getPollResults(HttpServletRequest req) {
		Long pollId = readLongParameter(req, "pollID");
		if (pollId == null) return null;
		
		List<PollOption> options;
		try {
			options = DAOProvider.getDao().getPollOptions(pollId);
			options.sort(Comparator.comparing(PollOption::getVotesCount).reversed());
			if (options == null || options.isEmpty()) {
				req.setAttribute(ERROR_ATTRIBUTE, "Nisu pronađeni rezultati tražene ankete.");
				return null;
			}
			if (hasZeroVotes(options)) {
				req.setAttribute(ERROR_ATTRIBUTE, "Nisu još zabilježeni nikakvi glasovi.");
				return null;
			}
		} catch (DAOException ex) {
			req.setAttribute(ERROR_ATTRIBUTE, "Nepostojeći PollID: '"+pollId+"'.");
			return null;
		}
		return options;
	}
	
	/**
	 * Returns {@code true} if the given {@code List} of {@link PollOption}s
	 * does not contain a single {@link PollOption} with a vote count greater than 0.
	 * 
	 * @param options The given {@code List} of {@link PollOption}s.
	 * @return {@code true} if no votes have been registered,
	 * 			or {@code false} otherwise.
	 */
	private static boolean hasZeroVotes(List<PollOption> options) {
		for (PollOption option : options) {
			if (option.getVotesCount() != 0) return false;
		}
		return true;
	}
}
