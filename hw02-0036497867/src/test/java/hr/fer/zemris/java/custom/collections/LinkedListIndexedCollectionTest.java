package hr.fer.zemris.java.custom.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testing class for the <code>LinkedListIndexedCollection</code> class.
 * 
 * @author Mate Gasparini
 */
public class LinkedListIndexedCollectionTest {
	
	@Test
	public void addTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		assertTrue(collection.isEmpty());
		
		collection.add(52);
		collection.add(43);
		collection.add(78);
		collection.add("abcd");
		
		assertFalse(collection.isEmpty());
		assertEquals(4, collection.size());
		assertTrue(collection.contains(52));
		assertTrue(collection.contains(43));
		assertTrue(collection.contains(78));
		assertTrue(collection.contains("abcd"));
		assertFalse(collection.contains("efgh"));
		assertFalse(collection.contains(null));
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void getFromEmptyCollection() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.get(0);
	}
	
	@Test
	public void getTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(52);
		collection.add(43);
		collection.add(78);
		collection.add("abcd");
		
		assertEquals(52, collection.get(0));
		assertEquals(43, collection.get(1));
		assertEquals(78, collection.get(2));
		assertEquals("abcd", collection.get(3));
	}
	
	@Test
	public void clearTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		assertTrue(collection.isEmpty());
		
		collection.add(52);
		collection.add(43);
		collection.add(78);
		collection.add("abcd");
		
		assertEquals(4, collection.size());
		
		collection.clear();
		
		assertTrue(collection.isEmpty());
	}
	
	@Test
	public void insertTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(52);
		collection.add(43);
		collection.add(78);
		collection.add("abcd");
		
		collection.insert("0", 0);
		assertEquals("0", collection.get(0));
		assertEquals(5, collection.size());
		
		collection.insert("2", 2);
		assertEquals("2", collection.get(2));
		assertEquals(6, collection.size());
		
		collection.insert("6", 6);
		assertEquals("6", collection.get(6));
		assertEquals(7, collection.size());
	}
	
	@Test
	public void indexOfTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(52);
		collection.add(43);
		collection.add(78);
		collection.add("abcd");
		
		assertEquals(-1, collection.indexOf(null));
		assertEquals(0, collection.indexOf(52));
		assertEquals(3, collection.indexOf("abcd"));
		assertEquals(-1, collection.indexOf("efgh"));
	}
	
	@Test
	public void removeTest() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		
		collection.add(52);
		collection.add(43);
		collection.add(78);
		collection.add("abcd");
		
		assertEquals(4, collection.size());
		
		collection.remove(1);
		
		assertEquals(3, collection.size());
		assertEquals("abcd", collection.get(2));
		
		collection.remove(2);
		collection.remove(0);
		collection.remove(0);
		
		assertTrue(collection.isEmpty());
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void removeFromEmptyCollection() {
		LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
		collection.remove(0);
	}
}
