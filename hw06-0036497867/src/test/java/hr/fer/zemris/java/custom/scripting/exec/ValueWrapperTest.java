package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testing class for the {@code ValueWrapper} class.
 * 
 * @author Mate Gasparini
 */
public class ValueWrapperTest {
	
	private ValueWrapper v1;
	private ValueWrapper v2;
	
	@Test
	public void convertNullToZero() {
		v1 = new ValueWrapper(null);
		v2 = new ValueWrapper(null);
		
		v1.add(v2.getValue());
		assertEquals(0, v1.getValue());
		
		v1 = new ValueWrapper(null);
		
		v1.subtract(v2.getValue());
		assertEquals(0, v1.getValue());
		
		v1 = new ValueWrapper(null);
		
		v1.multiply(v2.getValue());
		assertEquals(0, v1.getValue());
		
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void addIntegerIntegerTest() {
		v1 = new ValueWrapper(Integer.valueOf(1));
		v2 = new ValueWrapper(Integer.valueOf(2));
		
		v1.add(v2.getValue());
		assertEquals(Integer.valueOf(3), v1.getValue());
	}
	
	@Test
	public void addIntegerDoubleTest() {
		v1 = new ValueWrapper(Integer.valueOf(1));
		v2 = new ValueWrapper(Double.valueOf(2.0));
		
		v1.add(v2.getValue());
		assertEquals(Double.valueOf(3.0), v1.getValue());
	}
	
	@Test
	public void addDoubleIntegerTest() {
		v1 = new ValueWrapper(Double.valueOf(1.0));
		v2 = new ValueWrapper(Integer.valueOf(2));
		
		v1.add(v2.getValue());
		assertEquals(Double.valueOf(3.0), v1.getValue());
	}
	
	@Test
	public void addDoubleDoubleTest() {
		v1 = new ValueWrapper(Double.valueOf(1.0));
		v2 = new ValueWrapper(Double.valueOf(2.0));
		
		v1.add(v2.getValue());
		assertEquals(Double.valueOf(3.0), v1.getValue());
	}
	
	@Test
	public void addStringStringTest() {
		v1 = new ValueWrapper("1");
		v2 = new ValueWrapper("2");
		
		v1.add(v2.getValue());
		assertEquals(Integer.valueOf(3), v1.getValue());
		
		v1 = new ValueWrapper("1.0");
		v2 = new ValueWrapper("2");
		
		v1.add(v2.getValue());
		assertEquals(Double.valueOf(3.0), v1.getValue());
		
		v1 = new ValueWrapper("1");
		v2 = new ValueWrapper("2.0");
		
		v1.add(v2.getValue());
		assertEquals(Double.valueOf(3.0), v1.getValue());
		
		v1 = new ValueWrapper("1.0");
		v2 = new ValueWrapper("2.0");
		
		v1.add(v2.getValue());
		assertEquals(Double.valueOf(3.0), v1.getValue());
	}
	
	@Test
	public void subtractIntegerIntegerTest() {
		v1 = new ValueWrapper(Integer.valueOf(1));
		v2 = new ValueWrapper(Integer.valueOf(2));
		
		v1.subtract(v2.getValue());
		assertEquals(Integer.valueOf(-1), v1.getValue());
	}
	
	@Test
	public void subtractIntegerDoubleTest() {
		v1 = new ValueWrapper(Integer.valueOf(1));
		v2 = new ValueWrapper(Double.valueOf(2.0));
		
		v1.subtract(v2.getValue());
		assertEquals(Double.valueOf(-1.0), v1.getValue());
	}
	
	@Test
	public void subtractDoubleIntegerTest() {
		v1 = new ValueWrapper(Double.valueOf(1.0));
		v2 = new ValueWrapper(Integer.valueOf(2));
		
		v1.subtract(v2.getValue());
		assertEquals(Double.valueOf(-1.0), v1.getValue());
	}
	
	@Test
	public void subtractDoubleDoubleTest() {
		v1 = new ValueWrapper(Double.valueOf(1.0));
		v2 = new ValueWrapper(Double.valueOf(2.0));
		
		v1.subtract(v2.getValue());
		assertEquals(Double.valueOf(-1.0), v1.getValue());
	}
	
	@Test
	public void subtractStringStringTest() {
		v1 = new ValueWrapper("1");
		v2 = new ValueWrapper("2");
		
		v1.subtract(v2.getValue());
		assertEquals(Integer.valueOf(-1), v1.getValue());
		
		v1 = new ValueWrapper("1.0");
		v2 = new ValueWrapper("2");
		
		v1.subtract(v2.getValue());
		assertEquals(Double.valueOf(-1.0), v1.getValue());
		
		v1 = new ValueWrapper("1");
		v2 = new ValueWrapper("2.0");
		
		v1.subtract(v2.getValue());
		assertEquals(Double.valueOf(-1.0), v1.getValue());
		
		v1 = new ValueWrapper("1.0");
		v2 = new ValueWrapper("2.0");
		
		v1.subtract(v2.getValue());
		assertEquals(Double.valueOf(-1.0), v1.getValue());
	}
	
	@Test
	public void multiplyIntegerIntegerTest() {
		v1 = new ValueWrapper(Integer.valueOf(1));
		v2 = new ValueWrapper(Integer.valueOf(2));
		
		v1.multiply(v2.getValue());
		assertEquals(Integer.valueOf(2), v1.getValue());
	}
	
	@Test
	public void multiplyIntegerDoubleTest() {
		v1 = new ValueWrapper(Integer.valueOf(1));
		v2 = new ValueWrapper(Double.valueOf(2.0));
		
		v1.multiply(v2.getValue());
		assertEquals(Double.valueOf(2.0), v1.getValue());
	}
	
	@Test
	public void multiplyDoubleIntegerTest() {
		v1 = new ValueWrapper(Double.valueOf(1.0));
		v2 = new ValueWrapper(Integer.valueOf(2));
		
		v1.multiply(v2.getValue());
		assertEquals(Double.valueOf(2.0), v1.getValue());
	}
	
	@Test
	public void multiplyDoubleDoubleTest() {
		v1 = new ValueWrapper(Double.valueOf(1.0));
		v2 = new ValueWrapper(Double.valueOf(2.0));
		
		v1.multiply(v2.getValue());
		assertEquals(Double.valueOf(2.0), v1.getValue());
	}
	
	@Test
	public void multiplyStringStringTest() {
		v1 = new ValueWrapper("1");
		v2 = new ValueWrapper("2");
		
		v1.multiply(v2.getValue());
		assertEquals(Integer.valueOf(2), v1.getValue());
		
		v1 = new ValueWrapper("1.0");
		v2 = new ValueWrapper("2");
		
		v1.multiply(v2.getValue());
		assertEquals(Double.valueOf(2.0), v1.getValue());
		
		v1 = new ValueWrapper("1");
		v2 = new ValueWrapper("2.0");
		
		v1.multiply(v2.getValue());
		assertEquals(Double.valueOf(2.0), v1.getValue());
		
		v1 = new ValueWrapper("1.0");
		v2 = new ValueWrapper("2.0");
		
		v1.multiply(v2.getValue());
		assertEquals(Double.valueOf(2.0), v1.getValue());
	}
	
	@Test
	public void divideIntegerIntegerTest() {
		ValueWrapper v1 = new ValueWrapper(Integer.valueOf(1));
		ValueWrapper v2 = new ValueWrapper(Integer.valueOf(2));
		
		v1.divide(v2.getValue());
		assertEquals(Integer.valueOf(0), v1.getValue());
	}
	
	@Test
	public void divideIntegerDoubleTest() {
		v1 = new ValueWrapper(Integer.valueOf(1));
		v2 = new ValueWrapper(Double.valueOf(2.0));
		
		v1.divide(v2.getValue());
		assertEquals(Double.valueOf(0.5), v1.getValue());
	}
	
	@Test
	public void divideDoubleIntegerTest() {
		v1 = new ValueWrapper(Double.valueOf(1.0));
		v2 = new ValueWrapper(Integer.valueOf(2));
		
		v1.divide(v2.getValue());
		assertEquals(Double.valueOf(0.5), v1.getValue());
	}
	
	@Test
	public void divideDoubleDoubleTest() {
		v1 = new ValueWrapper(Double.valueOf(1.0));
		v2 = new ValueWrapper(Double.valueOf(2.0));
		
		v1.divide(v2.getValue());
		assertEquals(Double.valueOf(0.5), v1.getValue());
	}
	
	@Test
	public void divideStringStringTest() {
		v1 = new ValueWrapper("1");
		v2 = new ValueWrapper("2");
		
		v1.divide(v2.getValue());
		assertEquals(Integer.valueOf(0), v1.getValue());
		
		v1 = new ValueWrapper("1.0");
		v2 = new ValueWrapper("2");
		
		v1.divide(v2.getValue());
		assertEquals(Double.valueOf(0.5), v1.getValue());
		
		v1 = new ValueWrapper("1");
		v2 = new ValueWrapper("2.0");
		
		v1.divide(v2.getValue());
		assertEquals(Double.valueOf(0.5), v1.getValue());
		
		v1 = new ValueWrapper("1.0");
		v2 = new ValueWrapper("2.0");
		
		v1.divide(v2.getValue());
		assertEquals(Double.valueOf(0.5), v1.getValue());
	}
	
	@Test
	public void numCompareIntegerIntegerTest() {
		v1 = new ValueWrapper(Integer.valueOf(1));
		v2 = new ValueWrapper(Integer.valueOf(2));
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
		
		v1 = new ValueWrapper(Integer.valueOf(3));
		v2 = new ValueWrapper(Integer.valueOf(3));
		
		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
	
	@Test
	public void numCompareIntegerDoubleTest() {
		v1 = new ValueWrapper(Integer.valueOf(1));
		v2 = new ValueWrapper(Double.valueOf(2.0));
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
	}
	
	@Test
	public void numCompareDoubleIntegerTest() {
		v1 = new ValueWrapper(Double.valueOf(1.0));
		v2 = new ValueWrapper(Integer.valueOf(2));
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
	}
	
	@Test
	public void numCompareDoubleDoubleTest() {
		v1 = new ValueWrapper(Double.valueOf(1.0));
		v2 = new ValueWrapper(Double.valueOf(2.0));
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
		
		v1 = new ValueWrapper(Double.valueOf(3.0));
		v2 = new ValueWrapper(Double.valueOf(3.0));
		
		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
	
	@Test
	public void numCompareStringStringTest() {
		v1 = new ValueWrapper("1");
		v2 = new ValueWrapper("2");
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
		
		v1 = new ValueWrapper("1.0");
		v2 = new ValueWrapper("2");
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
		
		v1 = new ValueWrapper("1");
		v2 = new ValueWrapper("2.0");
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
		
		v1 = new ValueWrapper("1.0");
		v2 = new ValueWrapper("2.0");
		
		assertTrue(v1.numCompare(v2.getValue()) < 0);
		
		v1 = new ValueWrapper("3");
		v2 = new ValueWrapper("3");
		
		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
	
	@Test
	public void numCompareNullNullTest() {
		v1 = new ValueWrapper(null);
		v2 = new ValueWrapper(null);
		
		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
	
	@Test
	public void numCompareIntegerNullTest() {
		v1 = new ValueWrapper(Integer.valueOf(0));
		v2 = new ValueWrapper(null);
		
		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
	
	@Test
	public void numCompareDoubleNullTest() {
		v1 = new ValueWrapper(null);
		v2 = new ValueWrapper(Double.valueOf(0.0));
		
		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
}
