package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testing class for the <code>ObjectStack</code> class.
 * 
 * @author Mate Gasparini
 */
public class ObjectStackTest {
	
	@Test
	public void pushPeekPopTest() {
		ObjectStack stack = new ObjectStack();
		
		assertTrue(stack.isEmpty());
		
		stack.push(52);
		stack.push(43);
		stack.push(78);
		stack.push("abcd");
		
		assertFalse(stack.isEmpty());
		assertEquals(4, stack.size());
		
		assertEquals("abcd", stack.peek());
		assertEquals("abcd", stack.pop());
		stack.pop();
		stack.pop();
		assertEquals(52, stack.peek());
		assertEquals(52, stack.pop());
		
		assertTrue(stack.isEmpty());
	}
	
	@Test(expected=EmptyStackException.class)
	public void popFromEmptyStack() {
		ObjectStack stack = new ObjectStack();
		stack.pop();
	}
	
	@Test(expected=EmptyStackException.class)
	public void peekEmptyStack() {
		ObjectStack stack = new ObjectStack();
		stack.peek();
	}
	
	@Test
	public void clearTest() {
		ObjectStack stack = new ObjectStack();
		
		assertTrue(stack.isEmpty());
		
		stack.push(52);
		stack.push(43);
		stack.push(78);
		stack.push("abcd");
		
		assertEquals(4, stack.size());
		
		stack.clear();
		
		assertTrue(stack.isEmpty());
	}
}
