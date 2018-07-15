package hr.fer.zemris.java.hw06.demo4;

/**
 * Class containing data about some student and some subject
 * he/she took.<br>
 * Apart from the identification number and the student's name,
 * it contains all the scores and the final grade.
 * 
 * @author Mate Gasparini
 */
public class StudentRecord {
	
	/**
	 * Student's JMBAG.
	 */
	private String jmbag;
	/**
	 * Student's last name.
	 */
	private String lastName;
	/**
	 * Student's first name.
	 */
	private String firstName;
	/**
	 * Student's MI score.
	 */
	private double miScore;
	/**
	 * Student's ZI score.
	 */
	private double ziScore;
	/**
	 * Student's laboratory score.
	 */
	private double labScore;
	/**
	 * Student's final grade.
	 */
	private int grade;
	
	/**
	 * Number of attributes that each record must contain.
	 */
	private static final int NUMBER_OF_ATTRIBUTES = 7;
	
	/**
	 * Constructor specifying all data.
	 * 
	 * @param jmbag The specified JMBAG.
	 * @param lastName The specified last name.
	 * @param firstName The specified first name.
	 * @param miScore The specified MI exam score.
	 * @param ziScore The specified ZI exam score.
	 * @param labScore The specified laboratory score.
	 * @param grade The specified final grade.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName,
			double miScore, double ziScore, double labScore, int grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.miScore = miScore;
		this.ziScore = ziScore;
		this.labScore = labScore;
		this.grade = grade;
	}
	
	/**
	 * Parses the given array of strings, each string representing
	 * one attribute, and returns a new corresponding StudentRecord.
	 * 
	 * @param parts The given array specifying the attributes.
	 * @return A corresponding {@code StudentRecord}, or {@code null}
	 * 			if the parsing was not possible.
	 * @throws NullPointerException If the given array is null.
	 */
	public static StudentRecord parse(String[] parts) {
		/* Could use
		 * 
		 * StudentRecord.class.getDeclaredFields().length
		 * instead of NUMBER_OF_ATTRIBUTES,
		 * 
		 * but IMO this is more readable and a bit faster. */
		if (parts.length != NUMBER_OF_ATTRIBUTES) {
			throw new IllegalArgumentException("Wrong number of attributes.");
		}
		
		double miScore, ziScore, labScore;
		int grade;
		try {
			miScore = Double.parseDouble(parts[3]);
			ziScore = Double.parseDouble(parts[4]);
			labScore = Double.parseDouble(parts[5]);
			grade = Integer.parseInt(parts[6]);
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("String array not parseable.");
		}
		
		return new StudentRecord(
			parts[0], parts[1], parts[2], miScore, ziScore, labScore, grade
		);
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
	 * Returns the student's MI exam score.
	 * @return The student's MI exam score.
	 */
	public double getMiScore() {
		return miScore;
	}
	
	/**
	 * Returns the student's ZI exam score.
	 * 
	 * @return The student's ZI exam score.
	 */
	public double getZiScore() {
		return ziScore;
	}
	
	/**
	 * Returns the student's laboratory score.
	 * 
	 * @return The student's laboratory score.
	 */
	public double getLabScore() {
		return labScore;
	}
	
	/**
	 * Returns the student's final grade.
	 * 
	 * @return The student's final grade.
	 */
	public int getGrade() {
		return grade;
	}
}
