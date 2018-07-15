package hr.fer.zemris.java.voting.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.voting.dao.DAOProvider;
import hr.fer.zemris.java.voting.model.Poll;

/**
 * {@link HttpServlet} which handles GET requests by loading the {@code List}
 * of all {@link Poll}s available in the DB, setting a request attribute
 * to its reference and forwarding the {@link HttpServletRequest} to the
 * corresponding JSP file (index.jsp).
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/servleti/index.html"})
public class ServletiIndexServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPolls();
		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
