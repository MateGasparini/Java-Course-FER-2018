package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testing class for the {@code ComparisonOperators} class.
 * 
 * @author Mate Gasparini
 */
public class ComparisonOperatorsTest {
	
	@Test
	public void lessTest() {
		assertTrue(ComparisonOperators.LESS.satisfied("AB", "AC"));
		assertTrue(ComparisonOperators.LESS.satisfied("12", "13"));
	}
	
	@Test
	public void lessOrEqualsTest() {
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("AB", "AC"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("AB", "AB"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("12", "13"));
		assertTrue(ComparisonOperators.LESS_OR_EQUALS.satisfied("12", "12"));
	}
	
	@Test
	public void greaterTest() {
		assertTrue(ComparisonOperators.GREATER.satisfied("AC", "AB"));
		assertTrue(ComparisonOperators.GREATER.satisfied("13", "12"));
	}
	
	@Test
	public void greaterOrEqualsTest() {
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("AC", "AB"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("AC", "AC"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("13", "12"));
		assertTrue(ComparisonOperators.GREATER_OR_EQUALS.satisfied("13", "13"));
	}
	
	@Test
	public void equalsTest() {
		assertTrue(ComparisonOperators.EQUALS.satisfied("AB", "AB"));
		assertTrue(ComparisonOperators.EQUALS.satisfied("12", "12"));
	}
	
	@Test
	public void notEqualsTest() {
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("AB", "AC"));
		assertTrue(ComparisonOperators.NOT_EQUALS.satisfied("12", "13"));
	}
	
	@Test
	public void likeTest() {
		assertFalse(ComparisonOperators.LIKE.satisfied("AAA", "AA*AA"));
		assertFalse(ComparisonOperators.LIKE.satisfied("ABBA", "A*BB"));
		
		assertTrue(ComparisonOperators.LIKE.satisfied("ABAB", "AB*AB"));
		assertTrue(ComparisonOperators.LIKE.satisfied("ABAAB", "AB*AB"));
		
		assertTrue(ComparisonOperators.LIKE.satisfied("ABCCC", "AB*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("CCCAB", "*AB"));
		
		assertTrue(ComparisonOperators.LIKE.satisfied("ABCDE", "*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("A", "*"));
		assertTrue(ComparisonOperators.LIKE.satisfied("", "*"));
	}
}
