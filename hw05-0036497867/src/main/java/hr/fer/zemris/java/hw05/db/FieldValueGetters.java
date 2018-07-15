package hr.fer.zemris.java.hw05.db;

/**
 * Class providing different implementations
 * of the {@code IFieldValueGetter} interface.
 * 
 * @author Mate Gasparini
 */
public class FieldValueGetters {
	
	/**
	 * Obtains the student's first name.
	 */
	public static final IFieldValueGetter FIRST_NAME;
	/**
	 * Obtains the student's last name.
	 */
	public static final IFieldValueGetter LAST_NAME;
	/**
	 * Obtains the student's JMBAG.
	 */
	public static final IFieldValueGetter JMBAG;
	
	static {
		FIRST_NAME = StudentRecord::getFirstName;
		LAST_NAME = StudentRecord::getLastName;
		JMBAG = StudentRecord::getJmbag;
	}
}
