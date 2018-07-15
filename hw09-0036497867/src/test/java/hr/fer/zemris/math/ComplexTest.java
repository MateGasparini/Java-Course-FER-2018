package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the {@code Complex} class.
 * 
 * @author Mate Gasparini
 */
public class ComplexTest {
	
	private static final double EPSILON = 1E-6;
	
	private static Complex c1;
	private static Complex c2;
	
	@Before
	public void setUp() {
		c1 = new Complex(2.0, 3.0);
		c2 = new Complex(4.0, -4.0);
	}
	
	@Test
	public void moduleTest() {
		assertEquals(3.605551275, c1.module(), EPSILON);
		assertEquals(5.656854249, c2.module(), EPSILON);
	}
	
	@Test
	public void multiplyTest() {
		Complex c3 = c1.multiply(c2);
		double actual = c3.module();
		double expected = c1.module() * c2.module();
		
		assertEquals(expected, actual, EPSILON);
	}
	
	@Test
	public void divideTest() {
		Complex c3 = c1.divide(c2);
		double actual = c3.module();
		double expected = c1.module() / c2.module();
		
		assertEquals(expected, actual, EPSILON);
	}
	
	@Test
	public void addTest() {
		Complex c3 = c1.add(c2);
		assertEquals("6.0-1.0i", c3.toString());
	}
	
	@Test
	public void subTest() {
		Complex c3 = c1.sub(c2);
		assertEquals("-2.0+7.0i", c3.toString());
	}
	
	@Test
	public void negateTest() {
		Complex c3 = c1.negate();
		assertEquals("-2.0-3.0i", c3.toString());
		
		Complex c4 = c2.negate();
		assertEquals("-4.0+4.0i", c4.toString());
	}
}
