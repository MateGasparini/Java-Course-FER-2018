package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testing class for the <code>Dictionary</code> class.
 * 
 * @author Mate Gasparini
 */
public class DictionaryTest {
	
	@Test(expected=NullPointerException.class)
	public void putNullKeyExceptionThrown() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put(null, "value");
	}
	
	@Test
	public void putMultiplePairs() {
		Dictionary dictionary = new Dictionary();
		
		assertTrue(dictionary.isEmpty());
		
		dictionary.put("key 1", "value 1");
		dictionary.put("key 2", "value 2");
		dictionary.put("key 3", null);
		
		assertEquals(3, dictionary.size());
		
		assertEquals("value 1", dictionary.get("key 1"));
		assertEquals("value 2", dictionary.get("key 2"));
		assertEquals(null, dictionary.get("key 3"));
	}
	
	@Test
	public void clearTest() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("key 1", "value 1");
		dictionary.put("key 2", "value 2");
		dictionary.put("key 3", null);
		
		assertEquals(3, dictionary.size());
		
		dictionary.clear();
		
		assertTrue(dictionary.isEmpty());
		
		assertEquals(null, dictionary.get("key 1"));
		assertEquals(null, dictionary.get("key 2"));
		assertEquals(null, dictionary.get("key 3"));
	}
	
	@Test
	public void overwriteTest() {
		Dictionary dictionary = new Dictionary();
		
		dictionary.put("key 1", "value 1");
		dictionary.put("key 2", "value 2");
		
		assertEquals(2, dictionary.size());
		assertEquals("value 2", dictionary.get("key 2"));
		
		dictionary.put("key 2", "overwrite");
		
		assertEquals(2, dictionary.size());
		assertEquals("overwrite", dictionary.get("key 2"));
		
		dictionary.put("key 2", "overwrite again");
		
		assertEquals(2, dictionary.size());
		assertEquals("overwrite again", dictionary.get("key 2"));
	}
}
