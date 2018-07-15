package hr.fer.zemris.java.voting.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} which handles GET requests by redirecting
 * to {@link ServletiIndexServlet}.
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/index.html"})
public class IndexServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.sendRedirect("servleti/index.html");
	}
}
