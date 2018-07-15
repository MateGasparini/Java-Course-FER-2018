package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for the {@code ObjectMultistack} class.
 * 
 * @author Mate Gasparini
 */
public class ObjectMultistackTest { 
	
	private static ObjectMultistack multistack;
	
	@Before
	public void setUp() {
		multistack = new ObjectMultistack();
	}
	
	@Test
	public void pushTest() {
		multistack.push("numbers", new ValueWrapper(0));
		assertFalse(multistack.isEmpty("numbers"));
	}
	
	@Test(expected=NullPointerException.class)
	public void pushNullTest() {
		multistack.push("numbers", null);
	}
	
	@Test
	public void popTest() {
		multistack.push("numbers", new ValueWrapper(0));
		multistack.push("numbers", new ValueWrapper(1));
		
		assertEquals(1, multistack.pop("numbers").getValue());
		assertEquals(0, multistack.pop("numbers").getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void popFromEmptyStackExceptionThrown() {
		multistack.pop("numbers");
	}
	
	@Test
	public void peekTest() {
		multistack.push("numbers", new ValueWrapper(0));
		multistack.push("numbers", new ValueWrapper(1));
		
		assertEquals(1, multistack.peek("numbers").getValue());
		assertEquals(1, multistack.peek("numbers").getValue());
	}
	
	@Test(expected=RuntimeException.class)
	public void peekEmptyStackExceptionThrown() {
		multistack.peek("numbers");
	}
	
	@Test
	public void pushPopTest() {
		multistack.push("a", new ValueWrapper("A"));
		multistack.push("b", new ValueWrapper("B"));
		multistack.push("a", new ValueWrapper("AA"));
		multistack.push("b", new ValueWrapper("BB"));
		
		assertEquals("AA", multistack.pop("a").getValue());
		assertEquals("BB", multistack.pop("b").getValue());
		
		multistack.push("a", new ValueWrapper("AAA"));
		multistack.push("b", new ValueWrapper("BBB"));
		
		assertEquals("AAA", multistack.pop("a").getValue());
		assertEquals("BBB", multistack.pop("b").getValue());
		assertEquals("A", multistack.pop("a").getValue());
		assertEquals("B", multistack.pop("b").getValue());
		
		assertTrue(multistack.isEmpty("a"));
		assertTrue(multistack.isEmpty("b"));
	}
}
