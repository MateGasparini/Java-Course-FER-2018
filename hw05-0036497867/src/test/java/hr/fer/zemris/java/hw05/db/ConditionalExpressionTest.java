package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the {@code ConditionalExpression} class.
 * 
 * @author Mate Gasparini
 */
public class ConditionalExpressionTest {
	
	private StudentRecord record1;
	private StudentRecord record2;
	
	private ConditionalExpression expression1;
	private ConditionalExpression expression2;
	
	@Before
	public void setUp() {
		record1 = new StudentRecord("0000000001", "Akšamović", "Marin", "2");
		record2 = new StudentRecord("0000000002", "Bakamović", "Petra", "3");
		
		expression1 = new ConditionalExpression(
			FieldValueGetters.FIRST_NAME, "Marin", ComparisonOperators.EQUALS
		);
		expression2 = new ConditionalExpression(
			FieldValueGetters.LAST_NAME, "Bak*", ComparisonOperators.LIKE
		);
	}
	
	@Test
	public void satisfiedTest() {
		assertTrue(expression1.getComparisonOperator().satisfied(
			expression1.getFieldGetter().get(record1), expression1.getStringLiteral())
		);
		
		assertTrue(expression2.getComparisonOperator().satisfied(
			expression2.getFieldGetter().get(record2), expression2.getStringLiteral())
		);
	}
	
	@Test
	public void notSatisfiedTest() {
		assertFalse(expression1.getComparisonOperator().satisfied(
			expression1.getFieldGetter().get(record2), expression1.getStringLiteral())
		);
		
		assertFalse(expression2.getComparisonOperator().satisfied(
			expression2.getFieldGetter().get(record1), expression2.getStringLiteral())
		);
	}
}
