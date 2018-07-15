package hr.fer.zemris.java.tecaj_13.forms;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.Util;

/**
 * Web-form used for validation of entry content creation/modification.
 * 
 * @author Mate Gasparini
 */
public class EntryForm extends AbstractForm {
	
	/** Entered title. */
	private String title;
	
	/** Entered text. */
	private String text;
	
	/**
	 * Default constructor.
	 */
	public EntryForm() {
	}
	
	/**
	 * Fills the form properties corresponding to the parameters acquired through
	 * the given {@link HttpServletRequest}.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.title = Util.prepareString(req.getParameter("title"));
		this.text = Util.prepareString(req.getParameter("text"));
	}
	
	/**
	 * Fills the form properties corresponding to the parameters acquired through
	 * the given {@link BlogEntry}.
	 * 
	 * @param entry The given {@link BlogEntry}.
	 */
	public void fillFromEntry(BlogEntry entry) {
		this.title = entry.getTitle();
		this.text = entry.getText();
	}
	
	/**
	 * Sets the attributes of the given {@link BlogEntry} corresponding to the
	 * form's properties and to the given {@link BlogUser} it belongs to.<br>
	 * This method should be called after the {@link RegisterForm#validate()} method
	 * and after it is concluded that the form does not contain any errors.
	 * 
	 * @param entry The given {@link BlogEntry}.
	 * @param creator The given {@link BlogUser}.
	 */
	public void fillEntry(BlogEntry entry, BlogUser creator) {
		entry.setTitle(this.title);
		entry.setText(this.text);
		Date now = new Date();
		entry.setCreatedAt(now);
		entry.setLastModifiedAt(now);
		entry.setCreator(creator);
	}
	
	/**
	 * Sets the attributes of the given {@link BlogEntry} corresponding to the
	 * form's properties, but modifies only the title, the text and the {@code Date}
	 * of last modification.
	 * 
	 * @param entry The given {@link BlogEntry}.
	 */
	public void updateEntry(BlogEntry entry) {
		entry.setTitle(this.title);
		entry.setText(this.text);
		entry.setLastModifiedAt(new Date());
	}
	
	@Override
	public void validate() {
		errors.clear();
		
		if (this.title.isEmpty()) {
			errors.put("title", "Title is mandatory!");
		}
		
		if (this.text.isEmpty()) {
			errors.put("text", "Content must not be empty!");
		}
	}
	
	/**
	 * Returns the specified title.
	 * 
	 * @return The specified title.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the specified text.
	 * 
	 * @return The specified text.
	 */
	public String getText() {
		return text;
	}
}
