package hr.fer.zemris.java.hw06.demo4;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Testing class for the {@code StudentDemo} class.
 * 
 * @author Mate Gasparini
 */
public class StudentDemoTest {
	
	private static List<StudentRecord> records;
	
	@BeforeClass
	public static void setUpOnce() {
		List<String> lines;
		
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"));
		} catch (IOException e) {
			System.out.println(
				"An error occured while trying to read the file."
			);
			return;
		}
		
		records = StudentDemo.convert(lines);
	}
	
	@Test
	public void vratiBodovaViseOd25Test() {
		long result = StudentDemo.vratiBodovaViseOd25(records);
		
		assertEquals(452, result);
	}
	
	@Test
	public void vratiBrojOdlikasaTest() {
		long result = StudentDemo.vratiBrojOdlikasa(records);
		
		assertEquals(5, result);
	}
	
	@Test
	public void vratiListuOdlikasaTest() {
		List<StudentRecord> result = StudentDemo.vratiListOdlikasa(records);
		
		for (StudentRecord record : result) {
			assertEquals(5, record.getGrade());
		}
	}
	
	@Test
	public void vratiSortiranuListuOdlikasaTest() {
		List<StudentRecord> result = StudentDemo.vratiSortiranuListuOdlikasa(records);
		
		// First record - highest scores
		double previousScore = 100.0;
		for (StudentRecord record : result) {
			assertEquals(5, record.getGrade());
			
			double currentScore = record.getMiScore()
					+ record.getZiScore() + record.getLabScore();
			assertTrue(currentScore <= previousScore);
			previousScore = currentScore;
		}
	}
	
	@Test
	public void vratiPopisNepolozenihTest() {
		List<String> result = StudentDemo.vratiPopisNepolozenih(records);
		
		List<String> expectedResult = new LinkedList<>();
		for (StudentRecord record : records) {
			if (record.getGrade() == 1) {
				expectedResult.add(record.getJmbag());
			}
		}
		
		assertArrayEquals(expectedResult.toArray(), result.toArray());
	}
	
	@Test
	public void razvrstajStudentePoOcjenamaTest() {
		Map<Integer, List<StudentRecord>> result =
				StudentDemo.razvrstajStudentePoOcjenama(records);
		
		for (int grade = 1; grade <= 5; grade ++) {
			for (StudentRecord record : result.get(grade)) {
				assertEquals(grade, record.getGrade());
			}
		}
	}
	
	@Test
	public void razvrstajProlazPadTest() {
		Map<Boolean, List<StudentRecord>> result = StudentDemo.razvrstajProlazPad(records);
		
		for (StudentRecord recordFailed : result.get(false)) {
			assertEquals(1, recordFailed.getGrade());
		}
		
		for (StudentRecord recordPassed : result.get(true)) {
			assertTrue(recordPassed.getGrade() >=2 && recordPassed.getGrade() <=5);
		}
	}
}
