package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.LoginForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * {@link HttpServlet} which handles GET and POST requests by forwarding
 * the {@link HttpServletRequest} to Main.jsp (and providing the {@link LoginForm}).
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/servleti/main"})
public class MainServlet extends HttpServlet {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		processRequest(req, resp);
	}
	
	/**
	 * Method which processes both GET and POST requests.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 * @param resp The given {@link HttpServletResponse}.
	 * @throws IOException If an I/O error occurs
	 * @throws ServletException If the request could not be handled.
	 */
	private void processRequest(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		req.setAttribute("users", DAOProvider.getDAO().getBlogUsers());
		
		String method = req.getParameter("method");
		if (!"Login".equals(method)) {
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}
		
		LoginForm form = new LoginForm();
		form.fillFromRequest(req);
		form.validate();
		
		if (form.containsErrors()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/Main.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getBlogUser(req.getParameter("nick"));
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
