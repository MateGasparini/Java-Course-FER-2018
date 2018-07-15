package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link HttpServlet} which handles GET requests by invalidating
 * the {@link HttpServletRequest}'s session and redirecting to {@link MainServlet}.
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/servleti/logout"})
public class LogoutServlet extends HttpServlet {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
