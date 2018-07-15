package hr.fer.zemris.java.tecaj_13.forms;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class representing a generic web-form containing only
 * a {@code Map} of error messages and methods used for validation.
 * 
 * @author Mate Gasparini
 */
public abstract class AbstractForm {
	
	/** {@code Map} containing property names mapped to some error text. */
	protected Map<String, String> errors;
	
	/**
	 * Default constructor.
	 */
	public AbstractForm() {
		errors = new HashMap<>();
	}
	
	/**
	 * Acquires the given property's error message.
	 * 
	 * @param property Given property's name.
	 * @return The corresponding error message, or {@code null} if not set.
	 */
	public String getError(String property) {
		return errors.get(property);
	}
	
	/**
	 * Returns {@code true} if the form contains any errors.<br>
	 * This method should be called after the {@link RegisterForm#validate()} method.
	 * 
	 * @return {@code true} if there are errors present, or {@code false} otherwise.
	 */
	public boolean containsErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Returns {@code true} if the form contains any errors for the given property.<br>
	 * This method should be called after the {@link RegisterForm#validate()} method.
	 * 
	 * @param property Given property's name.
	 * @return {@code true} if there are property errors present, or {@code false} otherwise.
	 */
	public boolean errorPresent(String property) {
		return errors.containsKey(property);
	}
	
	/**
	 * Validates the content of the form (which has to be filled with data).<br>
	 * All values are checked and, if needed, errors are registered to the internal
	 * {@code Map} of error messages.
	 */
	public abstract void validate();
}
