package hr.fer.zemris.java.hw05.db;

/**
 * Interface with a single method which accepts a {@code StudentRecord}
 * based on some condition.
 * 
 * @author Mate Gasparini
 */
public interface IFilter {
	
	/**
	 * Returns {@code true} if the given record meets the specified conditions.
	 * 
	 * @param record The given record.
	 * @return {@code true} if the given record is accepted,
	 * 			and {@code false} otherwise.
	 */
	public boolean accepts(StudentRecord record);
}
