package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.forms.CommentForm;
import hr.fer.zemris.java.tecaj_13.forms.EntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.Util;

/**
 * {@link HttpServlet} which handles GET and POST requests
 * used for all actions linked to some URL-specified {@link BlogUser}.
 * 
 * @author Mate Gasparini
 */
@WebServlet(urlPatterns = {"/servleti/author/*"})
public class AuthorSevlet extends HttpServlet {
	
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
			throws ServletException, IOException {
		String path = req.getPathInfo();
		if (path == null || path.length() == 1) {
			forwardError(req, resp, "Author's nickname must be provided.");
			return;
		}
		
		path = path.substring(1);
		String[] parts = path.split("/");
		
		if (parts.length == 1) {
			processAuthor(req, resp, parts);
		} else if (parts.length == 2) {
			String action = parts[1];
			if (action.equals("new")) {
				processNew(req, resp, parts);
			} else {
				processEntryID(req, resp, parts);
			}
		} else if (parts.length == 3) {
			String action = parts[1];
			if (action.equals("edit")) {
				processEdit(req, resp, parts);
			} else {
				forwardError(req, resp, "Invalid action '"+action+"' for given parameters.");
			}
		} else {
			forwardError(req, resp, "Unexpected parameters after '/'.");
		}
	}
	
	/**
	 * Processes author-type requests.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 * @param resp The given {@link HttpServletResponse}.
	 * @param parts The split parts of the URL.
	 * @throws ServletException If the request could not be handled.
	 * @throws IOException If an I/O error occurs.
	 */
	private void processAuthor(HttpServletRequest req, HttpServletResponse resp,
			String[] parts) throws ServletException, IOException {
		String author = parts[0];
		
		if (Util.existsUser(author)) {
			List<BlogEntry> entries = Util.getBlogEntries(author);
			req.setAttribute("entries", entries);
			req.setAttribute("author", author);
			req.getRequestDispatcher("/WEB-INF/pages/AuthorEntries.jsp").forward(req, resp);
		} else {
			forwardError(req, resp, "Author '"+author+"' does not exist.");
		}
	}
	
	/**
	 * Processes new-type requests.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 * @param resp The given {@link HttpServletResponse}.
	 * @param parts The split parts of the URL.
	 * @throws ServletException If the request could not be handled.
	 * @throws IOException If an I/O error occurs.
	 */
	private void processNew(HttpServletRequest req, HttpServletResponse resp,String[] parts)
			throws ServletException, IOException {
		String author = parts[0];
		
		if (author.equals(req.getSession().getAttribute("current.user.nick"))) {
			String method = req.getParameter("method");
			if ("Cancel".equals(method)) {
				resp.sendRedirect(
					req.getServletContext().getContextPath()+"/servleti/author/"+author);
			} else if (!"Submit".equals(method)) {
				req.setAttribute("action", "New");
				req.getRequestDispatcher("/WEB-INF/pages/NewEdit.jsp").forward(req, resp);
			} else {
				EntryForm form = new EntryForm();
				form.fillFromRequest(req);
				form.validate();
				
				if (form.containsErrors()) {
					req.setAttribute("form", form);
					req.getRequestDispatcher("/WEB-INF/pages/NewEdit.jsp").forward(req, resp);
				} else {
					BlogEntry entry = new BlogEntry();
					form.fillEntry(entry, DAOProvider.getDAO().getBlogUser(author));
					DAOProvider.getDAO().registerBlogEntry(entry);
					resp.sendRedirect(
						req.getServletContext().getContextPath()+"/servleti/author/"+author);
				}
			}
		} else {
			forwardError(req, resp, "Access denied. Only '"+author+"' allowed here.");
		}
	}
	
	/**
	 * Processes edit-type requests.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 * @param resp The given {@link HttpServletResponse}.
	 * @param parts The split parts of the URL.
	 * @throws ServletException If the request could not be handled.
	 * @throws IOException If an I/O error occurs.
	 */
	private void processEdit(HttpServletRequest req, HttpServletResponse resp,String[] parts)
			throws ServletException, IOException {
		String author = parts[0];
		String entryIDParameter = parts[2];
		long entryID;
		try {
			entryID = Long.parseLong(entryIDParameter);
			if (!Util.entryBelongsToUser(author, entryID)) {
				forwardError(
					req, resp, "Entry ID '"+entryID+"' does not belong to '"+author+"'.");
				return;
			}
		} catch (NumberFormatException ex) {
			forwardError(req, resp, "Entry ID '"+entryIDParameter+"' must be numeric.");
			return;
		}
		
		if (author.equals(req.getSession().getAttribute("current.user.nick"))) {
			String method = req.getParameter("method");
			if ("Cancel".equals(method)) {
				resp.sendRedirect(
					req.getServletContext().getContextPath()
					+"/servleti/author/"+author+"/"+entryIDParameter);
			} else if (!"Submit".equals(method)) {
				EntryForm form = new EntryForm();
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
				form.fillFromEntry(entry);
				
				req.setAttribute("form", form);
				req.setAttribute("action", "Edit");
				req.getRequestDispatcher("/WEB-INF/pages/NewEdit.jsp").forward(req, resp);
			} else {
				EntryForm form = new EntryForm();
				form.fillFromRequest(req);
				form.validate();
				
				if (form.containsErrors()) {
					req.setAttribute("form", form);
					req.getRequestDispatcher("/WEB-INF/pages/NewEdit.jsp").forward(req, resp);
				} else {
					BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
					form.updateEntry(entry);
					resp.sendRedirect(
						req.getServletContext().getContextPath()
						+"/servleti/author/"+author+"/"+entryIDParameter);
				}
			}
		} else {
			forwardError(req, resp, "Access denied. Only '"+author+"' allowed here.");
		}
	}
	
	/**
	 * Processes EID-type requests.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 * @param resp The given {@link HttpServletResponse}.
	 * @param parts The split parts of the URL.
	 * @throws ServletException If the request could not be handled.
	 * @throws IOException If an I/O error occurs.
	 */
	private void processEntryID(HttpServletRequest req, HttpServletResponse resp,
			String[] parts) throws ServletException, IOException {
		String author = parts[0];
		String action = parts[1];
		long entryID;
		try {
			entryID = Long.parseLong(action);
		} catch (NumberFormatException ex) {
			forwardError(req, resp, "Invalid action '"+action+"'.");
			return;
		}
		
		if (Util.entryBelongsToUser(author, entryID)) {
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(entryID);
			String method = req.getParameter("method");
			if ("Comment".equals(method)) {
				CommentForm form = new CommentForm();
				form.fillFromRequest(req);
				Object currentUserID = req.getSession().getAttribute("current.user.id");
				if (currentUserID != null) {
					BlogUser user = DAOProvider.getDAO().getBlogUser((long) currentUserID);
					form.setEmail(user.getEmail());
				}
				form.validate();
				if (form.containsErrors()) {
					req.setAttribute("entry", entry);
					req.setAttribute("form", form);
					req.getRequestDispatcher("/WEB-INF/pages/Entry.jsp").forward(req, resp);
				} else {
					BlogComment comment = new BlogComment();
					form.fillComment(comment, entry);
					DAOProvider.getDAO().registerBlogComment(comment);
					resp.sendRedirect(
						req.getServletContext().getContextPath()
						+"/servleti/author/"+author+"/"+action);
				}
			} else {
				req.setAttribute("entry", entry);
				req.getRequestDispatcher("/WEB-INF/pages/Entry.jsp").forward(req, resp);
			}
		} else {
			forwardError(req, resp, "Entry ID "+entryID+" does not belong to '"+author+"'.");
		}
	}
	
	/**
	 * Sets the error message attribute and forwards the {@link HttpServletRequest}
	 * to Error.jsp.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 * @param resp The given {@link HttpServletResponse}.
	 * @param message specified error message.
	 * @throws ServletException If the request could not be handled.
	 * @throws IOException If an I/O error occurs.
	 */
	private void forwardError(HttpServletRequest req, HttpServletResponse resp,
			String message) throws ServletException, IOException {
		req.setAttribute("error", message);
		req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
	}
}
