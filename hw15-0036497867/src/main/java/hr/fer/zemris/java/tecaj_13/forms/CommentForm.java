package hr.fer.zemris.java.tecaj_13.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.util.Util;

/**
 * Web-form used for validation of comment posting.
 * 
 * @author Mate Gasparini
 */
public class CommentForm extends AbstractForm {
	
	/** The entered comment message. */
	private String message;
	
	/** The e-mail address of the person posting the comment. */
	private String email;
	
	/**
	 * Default constructor.
	 */
	public CommentForm() {
		super();
	}
	
	/**
	 * Fills the form properties corresponding to the parameters acquired through
	 * the given {@link HttpServletRequest}.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.message = Util.prepareString(req.getParameter("message"));
		this.email = Util.prepareString(req.getParameter("email"));
	}
	
	/**
	 * Sets the attributes of the given {@link BlogComment} corresponding to the
	 * form's properties and to the given {@link BlogEntry} it belongs to.<br>
	 * This method should be called after the {@link RegisterForm#validate()} method
	 * and after it is concluded that the form does not contain any errors.
	 * 
	 * @param comment The given {@link BlogComment}.
	 * @param entry The given {@link BlogEntry}.
	 */
	public void fillComment(BlogComment comment, BlogEntry entry) {
		comment.setBlogEntry(entry);
		comment.setMessage(this.message);
		comment.setPostedOn(new Date());
		comment.setUsersEMail(this.email);
	}
	
	@Override
	public void validate() {
		errors.clear();
		
		if (this.message.isEmpty()) {
			errors.put("message", "Cannot post empty comments.");
		}
		
		if (this.email.isEmpty()) {
			errors.put("email", "Please provide your e-mail address.");
		} else if (!Util.isValidEmail(this.email)) {
			errors.put("email", "E-mail not valid!");
		}
	}
	
	/**
	 * Returns the entered comment message.
	 * 
	 * @return The entered comment message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Returns the e-mail address of the poster.
	 * 
	 * @return The e-mail address of the poster.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Sets the e-mail address of the poster to the given e-mail address.
	 * 
	 * @param email The given e-mail address.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
