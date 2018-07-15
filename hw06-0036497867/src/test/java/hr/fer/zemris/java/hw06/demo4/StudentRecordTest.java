package hr.fer.zemris.java.hw06.demo4;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Testing class for the {@code StudentRecord} class.
 * 
 * @author Mate Gasparini
 */
public class StudentRecordTest {
	
	private static final String JMBAG = "0987654321";
	private static final String LAST_NAME = "Last Name";
	private static final String FIRST_NAME = "First Name";
	private static final double MI_SCORE = 19.9;
	private static final double ZI_SCORE = 9.9;
	private static final double LAB_SCORE = 0.9;
	private static final int GRADE = 4;
	
	private static final double EPSILON = 10E-4;
	
	@Test
	public void parseTest() {
		String[] parts = {
				JMBAG,
				LAST_NAME,
				FIRST_NAME,
				String.valueOf(MI_SCORE),
				String.valueOf(ZI_SCORE),
				String.valueOf(LAB_SCORE),
				String.valueOf(GRADE)
		};
		
		StudentRecord record = StudentRecord.parse(parts);
		
		assertEquals(JMBAG, record.getJmbag());
		assertEquals(LAST_NAME, record.getLastName());
		assertEquals(FIRST_NAME, record.getFirstName());
		assertEquals(MI_SCORE, record.getMiScore(), EPSILON);
		assertEquals(ZI_SCORE, record.getZiScore(), EPSILON);
		assertEquals(LAB_SCORE, record.getLabScore(), EPSILON);
		assertEquals(GRADE, record.getGrade());
	}
	
	@Test(expected=NullPointerException.class)
	public void parseNullTest() {
		StudentRecord.parse(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parseInvalidNumberOfAttributesTest() {
		String[] parts = {
				JMBAG,
				LAST_NAME
		};
		
		StudentRecord record = StudentRecord.parse(parts);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void parseNonParseableTest() {
		String[] parts = {
				JMBAG,
				LAST_NAME,
				FIRST_NAME,
				"NOT DOUBLE",
				"FOR",
				"SURE",
				"!"
		};
		
		StudentRecord record = StudentRecord.parse(parts);
	}
}
