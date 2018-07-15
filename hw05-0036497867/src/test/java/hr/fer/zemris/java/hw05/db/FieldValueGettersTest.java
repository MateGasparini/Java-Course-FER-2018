package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the {@code FieldValueGetters} class.
 * 
 * @author Mate Gasparini
 */
public class FieldValueGettersTest {
	
	private StudentRecord record;
	
	@Before
	public void setUp() {
		record = new StudentRecord("0000000001", "Akšamović", "Marin", "2");
	}
	
	@Test
	public void firstNameTest() {
		assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	public void lastNameTest() {
		assertEquals("Akšamović", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	public void jmbagTest() {
		assertEquals("0000000001", FieldValueGetters.JMBAG.get(record));
	}
}
