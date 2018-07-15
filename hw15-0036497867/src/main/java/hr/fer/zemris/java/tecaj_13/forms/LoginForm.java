package hr.fer.zemris.java.tecaj_13.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.Util;

/**
 * Web-form used for validation of credentials used for logging in.
 * 
 * @author Mate Gasparini
 */
public class LoginForm extends AbstractForm {
	
	/** Entered nickname. */
	private String nick;
	
	/** Entered password. */
	private String password;
	
	/**
	 * Default constructor.
	 */
	public LoginForm() {
	}
	
	/**
	 * Fills the form properties corresponding to the parameters acquired through
	 * the given {@link HttpServletRequest}.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.nick = req.getParameter("nick");
		this.password = req.getParameter("password");
	}
	
	/**
	 * Sets the attributes of the given {@link BlogUser} corresponding to the
	 * form's properties.<br>
	 * This method should be called after the {@link RegisterForm#validate()} method
	 * and after it is concluded that the form does not contain any errors.
	 * 
	 * @param user Reference to the given {@link BlogUser}.
	 */
	public void fillUser(BlogUser user) {
		user.setNick(this.nick);
		user.setPasswordHash(Util.hashEncode(this.password));
	}
	
	@Override
	public void validate() {
		errors.clear();
		
		if (this.nick.isEmpty()) {
			errors.put("nick", "Nickname is mandatory!");
		} else if (!Util.existsUser(this.nick)) {
			errors.put("password", "There are no users registered with the given nickname!");
		}
		
		if (this.password.isEmpty()) {
			errors.put("password", "Password is mandatory!");
		}
		
		if (errors.isEmpty() && !Util.checkCredentials(this.nick, this.password)) {
			errors.put("password", "Invalid password for the given nickname!");
		}
	}
	
	/**
	 * Returns the specified nickname.
	 * 
	 * @return The specified nickname.
	 */
	public String getNick() {
		return nick;
	}
	
	/**
	 * Returns the specified password.
	 * 
	 * @return The specified password.
	 */
	public String getPassword() {
		return password;
	}
}
