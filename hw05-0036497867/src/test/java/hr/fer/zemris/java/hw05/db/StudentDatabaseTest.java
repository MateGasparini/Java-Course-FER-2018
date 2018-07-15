package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the {@code StudentDatabase} class.
 * 
 * @author Mate Gasparini
 */
public class StudentDatabaseTest {
	
	private StudentDatabase database;
	
	private static String[] lines = {
		"0000000001	Akšamović	Marin	2",
		"0000000002	Bakamović	Petra	3",
		"0000000003	Bosnić	Andrea	4",
		"0000000004	Božić	Marin	5",
		"0000000005	Brezović	Jusufadis	2"
	};
	
	@Before
	public void setUp() {
		database = new StudentDatabase(Arrays.asList(lines));
	}
	
	@Test
	public void forJMBAGTest() {
		assertEquals("Jusufadis", database.forJMBAG("0000000005").getFirstName());
		assertEquals(null, database.forJMBAG("0000000000"));
	}
	
	@Test
	public void filterTest() {
		List<StudentRecord> fullList = database.filter(record -> true);
		assertEquals(lines.length, fullList.size());
		
		List<StudentRecord> emptyList = database.filter(record -> false);
		assertTrue(emptyList.isEmpty());
	}
}
