package hr.fer.zemris.java.hw02;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Testing class for the <code>ComplexNumber</code> class.
 * 
 * @author Mate Gasparini
 */
public class ComplexNumberTest {
	
	private static final double DELTA = 1E-6;
	
	@Test
	public void fromRealTest() {
		ComplexNumber c = ComplexNumber.fromReal(1.2);
		assertEquals(1.2, c.getReal(), DELTA);
		assertEquals(0.0, c.getImaginary(), DELTA);
	}
	
	@Test
	public void fromImaginaryTest() {
		ComplexNumber c = ComplexNumber.fromImaginary(1.2);
		assertEquals(0.0, c.getReal(), DELTA);
		assertEquals(1.2, c.getImaginary(), DELTA);
	}
	
	@Test
	public void magnitudeAndAngleTest() {
		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(1.2, 2.3);
		assertEquals(1.2, c.getMagnitude(), DELTA);
		assertEquals(2.3, c.getAngle(), DELTA);
		
		c = ComplexNumber.fromMagnitudeAndAngle(1.2, 4.5);
		assertEquals(1.2, c.getMagnitude(), DELTA);
		assertEquals(4.5, c.getAngle(), DELTA);
		
		c = ComplexNumber.fromMagnitudeAndAngle(1.2, 12.3);
		assertEquals(1.2, c.getMagnitude(), DELTA);
		assertEquals(12.3 - 2*Math.PI, c.getAngle(), DELTA);
	}
	
	@Test
	public void parseAndToStringTest() {
		ComplexNumber c = new ComplexNumber(1.2, 1.2);
		assertEquals(c.toString(), ComplexNumber.parse("1.2+1.2i").toString());
		
		c = new ComplexNumber(1.2, -1.2);
		assertEquals(c.toString(), ComplexNumber.parse("1.2-1.2i").toString());
		
		c = new ComplexNumber(-1.2, 1.2);
		assertEquals(c.toString(), ComplexNumber.parse("-1.2+1.2i").toString());
		
		c = new ComplexNumber(-1.2, -1.2);
		assertEquals(c.toString(), ComplexNumber.parse("-1.2-1.2i").toString());
		
		c = new ComplexNumber(0.0, 0.0);
		assertEquals(c.toString(), ComplexNumber.parse("0.0").toString());
		assertEquals(c.toString(), ComplexNumber.parse("0.0i").toString());
		
		c = ComplexNumber.fromImaginary(1.0);
		assertEquals(c.toString(), ComplexNumber.parse("1.0i").toString());
		assertEquals(c.toString(), ComplexNumber.parse("i").toString());
		
		c = ComplexNumber.fromImaginary(-1.0);
		assertEquals(c.toString(), ComplexNumber.parse("-1.0i").toString());
		assertEquals(c.toString(), ComplexNumber.parse("-i").toString());
		
		c = ComplexNumber.fromReal(1.0);
		assertEquals(c.toString(), ComplexNumber.parse("1.0").toString());
		
		c = ComplexNumber.fromReal(-1.0);
		assertEquals(c.toString(), ComplexNumber.parse("-1.0").toString());
	}
	
	@Test
	public void addTest() {
		ComplexNumber c1 = new ComplexNumber(1.2, 2.3);
		ComplexNumber c2 = new ComplexNumber(3.4, 4.5);
		ComplexNumber result = c1.add(c2);
		
		assertEquals(1.2 + 3.4, result.getReal(), DELTA);
		assertEquals(2.3 + 4.5, result.getImaginary(), DELTA);
	}
	
	@Test
	public void subTest() {
		ComplexNumber c1 = new ComplexNumber(1.2, 2.3);
		ComplexNumber c2 = new ComplexNumber(3.4, 4.5);
		ComplexNumber result = c1.sub(c2);
		
		assertEquals(1.2 - 3.4, result.getReal(), DELTA);
		assertEquals(2.3 - 4.5, result.getImaginary(), DELTA);
	}
	
	@Test
	public void mulTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(1.1, 1.3);
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(1.2, 1.2);
		ComplexNumber result = c1.mul(c2);
		
		assertEquals(1.1 * 1.2, result.getMagnitude(), DELTA);
		assertEquals(1.3 + 1.2, result.getAngle(), DELTA);
	}
	
	@Test
	public void divTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(1.1, 1.3);
		ComplexNumber c2 = ComplexNumber.fromMagnitudeAndAngle(1.2, 1.2);
		ComplexNumber result = c1.div(c2);
		
		assertEquals(1.1 / 1.2, result.getMagnitude(), DELTA);
		assertEquals(1.3 - 1.2, result.getAngle(), DELTA);
	}
	
	@Test
	public void powTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(1.1, 0.1);
		
		ComplexNumber result = c1.power(0);
		assertEquals(1.0, result.getMagnitude(), DELTA);
		assertEquals(2*Math.PI, result.getAngle(), DELTA);
		
		result = c1.power(1);
		assertEquals(1.1, result.getMagnitude(), DELTA);
		assertEquals(0.1, result.getAngle(), DELTA);
		
		result = c1.power(2);
		assertEquals(1.1 * 1.1, result.getMagnitude(), DELTA);
		assertEquals(0.1 * 2, result.getAngle(), DELTA);
		
		result = c1.power(3);
		assertEquals(1.1 * 1.1 * 1.1, result.getMagnitude(), DELTA);
		assertEquals(0.1 * 3, result.getAngle(), DELTA);
		
		result = c1.power(10);
		assertEquals(Math.pow(1.1, 10), result.getMagnitude(), DELTA);
		assertEquals(0.1 * 10, result.getAngle(), DELTA);
	}
	
	@Test
	public void rootTest() {
		ComplexNumber c1 = ComplexNumber.fromMagnitudeAndAngle(2, Math.PI / 6);
		
		ComplexNumber[] results = c1.root(4);
		assertEquals(4, results.length);
		
		assertEquals(1.17903328333783, results[0].getReal(), DELTA);
		assertEquals(0.155222676457773, results[0].getImaginary(), DELTA);
		
		assertEquals(-0.155222676457773, results[1].getReal(), DELTA);
		assertEquals(1.17903328333783, results[1].getImaginary(), DELTA);
		
		assertEquals(-1.17903328333783, results[2].getReal(), DELTA);
		assertEquals(-0.155222676457773, results[2].getImaginary(), DELTA);
		
		assertEquals(0.155222676457773, results[3].getReal(), DELTA);
		assertEquals(-1.17903328333783, results[3].getImaginary(), DELTA);
	}
}
