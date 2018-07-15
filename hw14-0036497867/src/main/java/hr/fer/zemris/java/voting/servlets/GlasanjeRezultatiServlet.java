package hr.fer.zemris.java.voting.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.voting.model.PollOption;

/**
 * {@link HttpServlet} which handles GET requests by loading the poll results (specified
 * by the given <i>pollID</i> URL parameter) from the DB, setting a request attribute
 * to the band {@code List} reference and forwarding the {@link HttpServletRequest}
 * to the corresponding JSP file (glasanjeRez.jsp).
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/servleti/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<PollOption> options = GlasanjeUtil.getPollResults(req);
		if (options == null) {
			req.getRequestDispatcher(GlasanjeUtil.ERROR_JSP).forward(req, resp);
			return;
		}
		
		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
