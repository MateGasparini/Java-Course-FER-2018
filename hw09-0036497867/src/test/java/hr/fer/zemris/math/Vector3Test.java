package hr.fer.zemris.math;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the {@code Vector3} class.
 * 
 * @author Mate Gasparini
 */
public class Vector3Test {
	
	private static final double EPSILON = 1E-6;
	
	private static Vector3 v1;
	private static Vector3 v2;
	
	@Before
	public void setUp() {
		v1 = new Vector3(1.0, 2.0, 3.0);
		v2 = new Vector3(-1.0, 4.0, 0.0);
	}
	
	@Test
	public void normTest() {
		assertEquals(3.74165738, v1.norm(), EPSILON);
		assertEquals(4.12310562, v2.norm(), EPSILON);
	}
	
	@Test
	public void normalizedTest() {
		Vector3 v3 = v1.normalized();
		assertEquals(1.0, v3.norm(), EPSILON);
		
		Vector3 v4 = v2.normalized();
		assertEquals(1.0, v4.norm(), EPSILON);
	}
	
	@Test
	public void addTest() {
		Vector3 v3 = v1.add(v2);
		
		assertEquals(0.0, v3.getX(), EPSILON);
		assertEquals(6.0, v3.getY(), EPSILON);
		assertEquals(3.0, v3.getZ(), EPSILON);
	}
	
	@Test
	public void subTest() {
		Vector3 v3 = v1.sub(v2);
		
		assertEquals(2.0, v3.getX(), EPSILON);
		assertEquals(-2.0, v3.getY(), EPSILON);
		assertEquals(3.0, v3.getZ(), EPSILON);
	}
	
	@Test
	public void dotTest() {
		assertEquals(7.0, v1.dot(v2), EPSILON);
	}
	
	@Test
	public void crossTest() {
		Vector3 v3 = v1.cross(v2);
		
		assertEquals(-12.0, v3.getX(), EPSILON);
		assertEquals(-3.0, v3.getY(), EPSILON);
		assertEquals(6.0, v3.getZ(), EPSILON);
	}
	
	@Test
	public void scaleTest() {
		Vector3 v3 = v1.scale(1.0);
		
		assertEquals(v1.getX(), v3.getX(), EPSILON);
		assertEquals(v1.getY(), v3.getY(), EPSILON);
		assertEquals(v1.getZ(), v3.getZ(), EPSILON);
		
		v3 = v1.scale(2.0);
		
		assertEquals(2.0, v3.getX(), EPSILON);
		assertEquals(4.0, v3.getY(), EPSILON);
		assertEquals(6.0, v3.getZ(), EPSILON);
		
		v3 = v1.scale(0.0);
		
		assertEquals(0.0, v3.norm(), EPSILON);
	}
	
	@Test
	public void cosAngleTest() {
		assertEquals(0.4537426, v1.cosAngle(v2), EPSILON);
		assertEquals(0.4537426, v2.cosAngle(v1), EPSILON);
	}
	
	@Test
	public void toArrayTest() {
		double[] array = v1.toArray();
		
		assertEquals(1.0, array[0], EPSILON);
		assertEquals(2.0, array[1], EPSILON);
		assertEquals(3.0, array[2], EPSILON);
	}
}
