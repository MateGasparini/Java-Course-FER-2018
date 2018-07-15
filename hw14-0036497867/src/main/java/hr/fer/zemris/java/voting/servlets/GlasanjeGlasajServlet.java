package hr.fer.zemris.java.voting.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.voting.dao.DAOException;
import hr.fer.zemris.java.voting.dao.DAOProvider;

/**
 * {@link HttpServlet} which handles GET requests by reading the specified id parameter,
 * registering a vote for it in the DB and redirecting to {@link GlasanjeRezultatiServlet}
 * with the specified <i>pollID</i> URL parameter.
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long id = GlasanjeUtil.readLongParameter(req, "id");
		if (id == null) {
			req.getRequestDispatcher(GlasanjeUtil.ERROR_JSP);
			return;
		}
		
		try {
			DAOProvider.getDao().registerVote(id);
		} catch (DAOException ex) {
			req.setAttribute(GlasanjeUtil.ERROR_ATTRIBUTE, "Nepostojeći ID: '"+id+"'.");
			req.getRequestDispatcher(GlasanjeUtil.ERROR_JSP);
			return;
		}
		
		long pollId = DAOProvider.getDao().getPollOption(id).getPollId();
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID="+pollId);
	}
}
