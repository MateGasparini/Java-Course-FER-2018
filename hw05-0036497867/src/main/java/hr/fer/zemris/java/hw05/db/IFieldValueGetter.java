package hr.fer.zemris.java.hw05.db;

/**
 * A strategy with a single method which is responsible for obtaining
 * a requested field value.
 * 
 * @author Mate Gasparini
 */
public interface IFieldValueGetter {
	
	/**
	 * Returns a requested field value from the given student record.
	 * 
	 * @param record The given student record.
	 * @return The requested field value.
	 */
	public String get(StudentRecord record);
}
