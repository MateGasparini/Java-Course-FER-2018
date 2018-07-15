package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testing class for the <code>Factorial</code> class.
 * 
 * @author Mate Gasparini
 */
public class FactorialTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void negativeNumberExceptionThrown() {
		Factorial.calculateFactorial(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void zeroExceptionThrown() {
		Factorial.calculateFactorial(0);
	}
	
	@Test
	public void oneFactorialIsOne() {
		Assert.assertEquals(1, Factorial.calculateFactorial(1));
	}
	
	@Test
	public void fiveFactorial() {
		Assert.assertEquals(120, Factorial.calculateFactorial(5));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void bigNumberExceptionThrown() {
		Factorial.calculateFactorial(21);
	}
}
