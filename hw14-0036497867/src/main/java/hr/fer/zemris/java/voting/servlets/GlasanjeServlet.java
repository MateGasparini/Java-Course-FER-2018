package hr.fer.zemris.java.voting.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.voting.dao.DAOException;
import hr.fer.zemris.java.voting.dao.DAOProvider;
import hr.fer.zemris.java.voting.model.Poll;
import hr.fer.zemris.java.voting.model.PollOption;

/**
 * {@link HttpServlet} which handles GET requests by loading the {@code List} of
 * {@link PollOption}s corresponding to the specified <i>pollID</i> parameter,
 * as well as the corresponding {@link Poll}, setting a request attribute
 * to its references and forwarding the {@link HttpServletRequest} to the
 * corresponding JSP file (glasanjeIndex.jsp).
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/servleti/glasanje"})
public class GlasanjeServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			Long pollId = GlasanjeUtil.readLongParameter(req, "pollID");
			if (pollId == null) {
				req.getRequestDispatcher(GlasanjeUtil.ERROR_JSP).forward(req, resp);
				return;
			}
			
			Poll poll = DAOProvider.getDao().getPoll(pollId);
			req.setAttribute("poll", poll);
			List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollId);
			req.setAttribute("pollOptions", pollOptions);
			
			req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
		} catch (DAOException ex) {
			resp.sendError(500, "Internal server error");
			ex.printStackTrace();
		}
	}
}
