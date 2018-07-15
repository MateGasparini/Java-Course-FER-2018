package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the {@code QueryFilter} class.
 * 
 * @author Mate Gasparini
 */
public class QueryFilterTest {
	
	private StudentRecord record;
	private List<ConditionalExpression> expressions;
	
	@Before
	public void setUp() {
		record = new StudentRecord(
			"0000000001", "Akšamović", "Marin", "2"
		);
		
		expressions = new LinkedList<>();
	}
	
	@Test
	public void acceptsTest() {
		expressions.add(new ConditionalExpression(
			FieldValueGetters.FIRST_NAME, "Marin", ComparisonOperators.EQUALS)
		);
		expressions.add(new ConditionalExpression(
			FieldValueGetters.LAST_NAME, "Ak*", ComparisonOperators.LIKE)
		);
		
		QueryFilter filter = new QueryFilter(expressions);
		assertTrue(filter.accepts(record));
	}
	
	@Test
	public void wrongAcceptsTest() {
		expressions.add(new ConditionalExpression(
			FieldValueGetters.FIRST_NAME, "Marin", ComparisonOperators.GREATER)
		);
		
		QueryFilter filter = new QueryFilter(expressions);
		assertFalse(filter.accepts(record));
	}
}
