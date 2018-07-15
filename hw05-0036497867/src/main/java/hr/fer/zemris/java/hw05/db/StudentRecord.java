package hr.fer.zemris.java.hw05.db;

/**
 * Class representing a record which contains some student's information
 * (JMBAG, first name, last name and final grade).
 * 
 * @author Mate Gasparini
 */
public class StudentRecord {
	
	/**
	 * The student's JMBAG.
	 */
	private String jmbag;
	/**
	 * The student's last name.
	 */
	private String lastName;
	/**
	 * The student's first name.
	 */
	private String firstName;
	/**
	 * The student's final grade.
	 */
	private String finalGrade;
	
	/**
	 * Constructor specifying student information.
	 * 
	 * @param jmbag Student's JMBAG.
	 * @param lastName Student's last name.
	 * @param firstName Student's first name.
	 * @param finalGrade Student's final grade.
	 */
	public StudentRecord(String jmbag, String lastName,
			String firstName, String finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Returns the student's JMBAG.
	 * 
	 * @return The student's JMBAG.
	 */
	public String getJmbag() {
		return jmbag;
	}
	
	/**
	 * Returns the student's last name.
	 * 
	 * @return The student's last name.
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Returns the student's first name.
	 * 
	 * @return The student's first name.
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Returns the student's final grade.
	 * 
	 * @return The student's final grade.
	 */
	public String getFinalGrade() {
		return finalGrade;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
}
