package hr.fer.zemris.java.tecaj_13.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.util.Util;

/**
 * Web-form used for validation of credentials used for registration.
 * 
 * @author Mate Gasparini
 */
public class RegisterForm extends AbstractForm {
	
	/** Entered first name. */
	private String firstName;
	
	/** Entered last name. */
	private String lastName;
	
	/** Entered e-mail address. */
	private String email;
	
	/** Entered nickname. */
	private String nick;
	
	/** Entered password. */
	private String password;
	
	/**
	 * Default constructor.
	 */
	public RegisterForm() {
		super();
	}
	
	/**
	 * Fills the form properties corresponding to the parameters acquired through
	 * the given {@link HttpServletRequest}.
	 * 
	 * @param req The given {@link HttpServletRequest}.
	 */
	public void fillFromRequest(HttpServletRequest req) {
		this.firstName = Util.prepareString(req.getParameter("firstname"));
		this.lastName = Util.prepareString(req.getParameter("lastname"));
		this.email = Util.prepareString(req.getParameter("email"));
		this.nick = Util.prepareString(req.getParameter("nick"));
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
		user.setFirstName(this.firstName);
		user.setLastName(this.lastName);
		user.setEmail(this.email);
		user.setNick(this.nick);
		user.setPasswordHash(Util.hashEncode(this.password));
	}
	
	@Override
	public void validate() {
		errors.clear();
		
		if (this.firstName.isEmpty()) {
			errors.put("firstname", "First name is mandatory!");
		}
		
		if (this.lastName.isEmpty()) {
			errors.put("lastname", "Last name is mandatory!");
		}
		
		if (this.email.isEmpty()) {
			errors.put("email", "E-mail address is mandatory!");
		} else if (!Util.isValidEmail(this.email)) {
			errors.put("email", "E-mail not valid!");
		}
		
		if (this.nick.isEmpty()) {
			errors.put("nick", "Nickname is mandatory!");
		} else if (Util.existsUser(this.nick)) {
			errors.put("nick", "Nickname already taken!");
		}
		
		if (this.password.isEmpty()) {
			errors.put("password", "Password is mandatory!");
		}
	}
	
	/**
	 * Returns the specified first name.
	 * 
	 * @return The specified first name.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Returns the specified last name.
	 * 
	 * @return The specified last name.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Returns the specified e-mail address.
	 * 
	 * @return The specified e-mail address.
	 */
	public String getEmail() {
		return email;
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
