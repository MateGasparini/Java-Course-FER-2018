package hr.fer.zemris.java.hw05.collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.hw05.collections.SimpleHashtable;

/**
 * Testing class for the {@code SimpleHashtable} class.
 * 
 * @author Mate Gasparini
 */
public class SimpleHashtableTest {
	
	private SimpleHashtable<String, Integer> table;
	
	private static String[] keys = new String[] {
		"Nulti", "Prvi", "Drugi", "Treći", "Četvrti"
	};
	
	private static Integer[] values = new Integer[] {
		0, 1, 2, 3, 4
	};
	
	@Before
	public void setUp() {
		table = new SimpleHashtable<>(1);
		
		assertTrue(table.isEmpty());
		putSomeEntries();
		assertEquals(5, table.size());
	}
	
	@Test
	public void putTest() {
		for (int i = 0; i < keys.length; i ++) {
			assertTrue(table.containsKey(keys[i]));
		}
		assertFalse(table.containsKey("dunno"));
		
		for (int i = 0; i < values.length; i ++) {
			assertTrue(table.containsValue(i));
		}
		assertFalse(table.containsValue(999));
	}
	
	@Test
	public void getTest() {
		for (int i = 0; i < keys.length; i ++) {
			assertEquals(Integer.valueOf(i), table.get(keys[i]));
		}
	}
	
	@Test
	public void overwriteTest() {
		for (int i = 0; i < keys.length; i ++) {
			table.put(keys[i], values[(i+1) % values.length]);
		}
		assertEquals(5, table.size());
		
		for (int i = 0; i < keys.length; i ++) {
			assertEquals(values[(i+1) % values.length], table.get(keys[i]));
		}
	}
	
	@Test
	public void removeTest() {
		for (int i = 0; i < keys.length; i ++) {
			table.remove(keys[i]);
			assertEquals(keys.length - i - 1, table.size());
		}
		assertTrue(table.isEmpty());
	}
	
	@Test
	public void clearTest() {
		table.clear();
		
		assertTrue(table.isEmpty());
		
		for (int i = 0; i < keys.length; i ++) {
			assertFalse(table.containsKey(keys[i]));
		}
		
		for (int i = 0; i < values.length; i ++) {
			assertFalse(table.containsValue(values[i]));
		}
	}
	
	@Test
	public void iterationRemoveTest() {
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals(keys[0])) {
				iter.remove();
			}
		}
		
		assertEquals(4, table.size());
		assertFalse(table.containsKey(keys[0]));
	}
	
	@Test
	public void iterationRemoveAllTest() {
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		
		assertTrue(table.isEmpty());
		for (int i = 0; i < keys.length; i ++) {
			assertFalse(table.containsKey(keys[i]));
		}
	}
	
	@Test(expected=IllegalStateException.class)
	public void doubleRemoveExceptionThrown() {
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals(keys[0])) {
				iter.remove();
				iter.remove();
			}
		}
	}
	
	@Test(expected=ConcurrentModificationException.class)
	public void removeOutsideOfIteratorExceptionThrown() {
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals(keys[0])) {
				table.remove(keys[0]);
			}
		}
	}
	
	private void putSomeEntries() {
		for (int i = 0; i < keys.length; i ++) {
			table.put(keys[i], values[i]);
		}
	}
}
