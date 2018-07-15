package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Class representing a database of students. It provides a {@code List} of
 * student records, as well as an index ({@code SimpleHashtable})
 * for faster retrieval of data.
 * 
 * @author Mate Gasparini
 */
public class StudentDatabase {
	
	/**
	 * List of all student records.
	 */
	private List<StudentRecord> list;
	/**
	 * Index of student records used for faster retrieval of data.
	 * It uses the student's JMBAG as the key.
	 */
	private SimpleHashtable<String, StudentRecord> index;
	
	/**
	 * Constructor which accepts a {@code List} of strings (which represent
	 * rows of student data).
	 * 
	 * @param lines Rows of student data.
	 */
	public StudentDatabase(List<String> lines) {
		list = new LinkedList<>();
		index = new SimpleHashtable<>();
		
		for (String line : lines) {
			String[] parts = line.split("\\t");
			
			if (parts.length == 4) {
				StudentRecord record = new StudentRecord(
					parts[0], parts[1], parts[2], parts[3]
				);
				
				list.add(record);
				index.put(parts[0], record);
			} else {
				throw new IllegalArgumentException(
					"Invalid row content: '" + line + "'."
				);
			}
		}
	}
	
	/**
	 * Uses the internal index for fast retrieval of the {@code StudentRecord}.
	 * If there is no {@code StudentRecord} with the specified JMBAG,
	 * null is returned.
	 * 
	 * @param jmbag The student's JMBAG.
	 * @return Reference to the corresponding {@code StudentRecord},
	 * 			or {@code null} if the {@code StudentRecord} is not found.
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return index.get(jmbag);
	}
	
	/**
	 * Loops through all {@code StudentRecord} references and for each
	 * calls the given filter's {@code accepts} method, and returns
	 * a {@code List} containing all records for which the method
	 * returned {@code true}.
	 * 
	 * @param filter The given filter.
	 * @return A {@code List} containing all filter-accepted
	 * 			{@code StudentRecord} references.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredList = new LinkedList<>();
		
		for (StudentRecord record : list) {
			if (filter.accepts(record)) {
				filteredList.add(record);
			}
		}
		
		return filteredList;
	}
}
