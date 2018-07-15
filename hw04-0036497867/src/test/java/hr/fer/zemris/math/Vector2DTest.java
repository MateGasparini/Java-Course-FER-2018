package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Testing class for the <code>Vector2D</code> class.
 * 
 * @author Mate Gasparini
 */
public class Vector2DTest {
	
	private static final double EPSILON = 1E-6;
	
	@Test
	public void translateTest() {
		Vector2D vector1 = new Vector2D(5.0, 12.0);
		vector1.translate(new Vector2D(15.0, 8.0));
		
		assertEquals(20.0, vector1.getX(), EPSILON);
		assertEquals(20.0, vector1.getY(), EPSILON);
	}
	
	@Test
	public void translatedTest() {
		Vector2D vector1 = new Vector2D(5.0, 12.0);
		Vector2D vector2 = vector1.translated(new Vector2D(15.0, 8.0));
		
		assertEquals(20.0, vector2.getX(), EPSILON);
		assertEquals(20.0, vector2.getY(), EPSILON);
	}
	
	@Test
	public void rotateTest() {
		Vector2D vector1 = new Vector2D(5.0, 12.0);
		vector1.rotate(-360.0);
		
		assertEquals(5.0, vector1.getX(), EPSILON);
		assertEquals(12.0, vector1.getY(), EPSILON);
		
		vector1.rotate(720.0);
		
		assertEquals(5.0, vector1.getX(), EPSILON);
		assertEquals(12.0, vector1.getY(), EPSILON);
		
		vector1.rotate(30.0);
		
		assertEquals(-1.669872981, vector1.getX(), EPSILON);
		assertEquals(12.892304845, vector1.getY(), EPSILON);
	}
	
	@Test
	public void rotatedTest() {
		Vector2D vector1 = new Vector2D(5.0, 12.0);
		Vector2D vector2 = vector1.rotated(-360.0);
		
		assertEquals(5.0, vector2.getX(), EPSILON);
		assertEquals(12.0, vector2.getY(), EPSILON);
		
		vector2 = vector1.rotated(720.0);
		
		assertEquals(5.0, vector2.getX(), EPSILON);
		assertEquals(12.0, vector2.getY(), EPSILON);
		
		vector2 = vector1.rotated(30.0);
		
		assertEquals(-1.669872981, vector2.getX(), EPSILON);
		assertEquals(12.892304845, vector2.getY(), EPSILON);
		
		assertEquals(5.0, vector1.getX(), EPSILON);
		assertEquals(12.0, vector1.getY(), EPSILON);
	}
	
	@Test
	public void scaleTest() {
		Vector2D vector1 = new Vector2D(5.0, 12.0);
		vector1.scale(1.0);
		
		assertEquals(5.0, vector1.getX(), EPSILON);
		assertEquals(12.0, vector1.getY(), EPSILON);
		
		vector1.scale(-1.0);
		
		assertEquals(-5.0, vector1.getX(), EPSILON);
		assertEquals(-12.0, vector1.getY(), EPSILON);
		
		vector1.scale(-5.0);
		
		assertEquals(25.0, vector1.getX(), EPSILON);
		assertEquals(60.0, vector1.getY(), EPSILON);
	}
	
	@Test
	public void scaledTest() {
		Vector2D vector1 = new Vector2D(5.0, 12.0);
		Vector2D vector2 = vector1.scaled(1.0);
		
		assertEquals(5.0, vector2.getX(), EPSILON);
		assertEquals(12.0, vector2.getY(), EPSILON);
		
		vector2 = vector1.scaled(-1.0);
		
		assertEquals(-5.0, vector2.getX(), EPSILON);
		assertEquals(-12.0, vector2.getY(), EPSILON);
		
		vector2 = vector2.scaled(-5.0);
		
		assertEquals(25.0, vector2.getX(), EPSILON);
		assertEquals(60.0, vector2.getY(), EPSILON);
		
		assertEquals(5.0, vector1.getX(), EPSILON);
		assertEquals(12.0, vector1.getY(), EPSILON);
	}
	
	@Test
	public void copyTest() {
		Vector2D original = new Vector2D(22.0, 36.0);
		Vector2D copy = original.copy();
		
		assertEquals(22.0, copy.getX(), EPSILON);
		assertEquals(36.0, copy.getY(), EPSILON);
	}
}
