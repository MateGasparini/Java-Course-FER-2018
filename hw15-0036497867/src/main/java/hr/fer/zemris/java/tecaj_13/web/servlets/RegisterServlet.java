package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.RegisterForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * {@link HttpServlet} which handles GET and POST requests by forwarding
 * the {@link HttpServletRequest} to RegisterForm.jsp (and providing the {@link RegisterForm}).
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/servleti/register"})
public class RegisterServlet extends HttpServlet {

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
		
		String method = req.getParameter("method");
		if ("Cancel".equals(method)) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
			return;
		} else if (!"Register".equals(method)) {
			req.getRequestDispatcher("/WEB-INF/pages/RegisterForm.jsp").forward(req, resp);
			return;
		}

		RegisterForm form = new RegisterForm();
		form.fillFromRequest(req);
		form.validate();
		
		if (form.containsErrors()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/RegisterForm.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = new BlogUser();
		form.fillUser(user);
		DAOProvider.getDAO().registerBlogUser(user);
		
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}
}
