package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class representing a simple iterable hash table.
 * It contains an array of slots. Each slot is initially empty,
 * but can be filled with a key-value pair ({@code TableEntry}).
 * Overflows are handled by chaining - for every new key that is mapped
 * to an already occupied slot, the new key-value pair is pushed
 * to the back of the corresponding slot's list.
 * 
 * @author Mate Gasparini
 *
 * @param <K> Type of all keys.
 * @param <V> Type of all values.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {
	
	/**
	 * A table of entry slots; each slots can be either empty,
	 * or filled with one or more entries (linked in a list).
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Number of currently stored pairs in the table.
	 */
	private int size;
	/**
	 * Number of (any structure-changing) modifications performed on the table.
	 */
	private int modificationCount;
	
	/**
	 * Default number of slots in the underlying {@code TableEntry} array.
	 */
	public static final int DEFAULT_CAPACITY = 16;
	/**
	 * If the ratio between the number of stored pairs and the number of slots
	 * gets bigger than this constant, the number of slots is doubled.
	 */
	public static final double FILL_TRESHOLD = 0.75;
	
	/**
	 * Default constructor.
	 * Constructs a table with {@code DEFAULT_CAPACITY} number of slots.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor specifying the initial capacity.
	 * 
	 * @param capacity Initial number of table slots.
	 * @throws IllegalArgumentException If {@code capacity} is 0 or negative.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException(
				"Initial capacity must be greater than 0."
			);
		}
		
		int correctedCapacity = 1;
		while (correctedCapacity < capacity) {
			correctedCapacity <<= 1;
		}
		
		table = (TableEntry<K, V>[]) new TableEntry[correctedCapacity];
	}
	
	/**
	 * If the table already contains a pair with the given key, the pair's
	 * value is updated with the given value.<br>
	 * Otherwise, a new pair is constructed and added to the slot calculated
	 * for the given key.<br>
	 * If the number of 
	 * 
	 * @param key The given key.
	 * @param value The given value.
	 * @throws NullPointerException If the given key is {@code null}.
	 */
	public void put(K key, V value) {
		if (key == null) {
			throw new NullPointerException(
				"Key cannot be null."
			);
		}
		
		if (addToList(key, value, position(key))) {
			size ++;
			modificationCount ++;
		}
		
		/* A little stupid if the capacity is 1, but why would you
		 * want a table with capacity 1 in the first place? */
		if ((double) size / table.length >= FILL_TRESHOLD) {
			expandTable();
			modificationCount ++;
		}
	}
	
	/**
	 * Finds a pair with the given key, and returns its value.
	 * If there is no such pair, {@code null} is returned.<br>
	 * Bear in mind that {@code null} could also be returned
	 * if the corresponding value is {@code null}.
	 * 
	 * @param key The given key.
	 * @return The corresponding value.
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}
		
		int position = position(key);
		TableEntry<K, V> entry = table[position];
		
		while (entry != null) {
			if (entry.key.equals(key)) {
				return entry.value;
			}
			
			entry = entry.next;
		}
		
		return null;
	}
	
	/**
	 * Returns the number of currently stored pairs.
	 * 
	 * @return The number of stored {@code TableEntry} references.
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Returns {@code true} if the table contains a {@code TableEntry}
	 * with the given key.
	 * 
	 * @param key The given key.
	 * @return {@code true} if the table contains the given key,
	 * 			and {@code false} otherwise.
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		
		TableEntry<K, V> entry = table[position(key)];
		
		while (entry != null) {
			if (entry.key.equals(key)) {
				return true;
			}
			
			entry = entry.next;
		}
		
		return false;
	}
	
	/**
	 * Returns {@code true} if the table contains a {@code TableEntry}
	 * with the given value.
	 * 
	 * @param value The given value.
	 * @return {@code true} if the table contains the given value,
	 * 			and {@code false} otherwise.
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> entry : table) {
			while (entry != null) {
				if (entry.value.equals(value)) {
					return true;
				}
				
				entry = entry.next;
			}
		}
		
		return false;
	}
	
	/**
	 * Removes the given key's corresponding {@code TableEntry}
	 * from the table.<br>
	 * If the table does not contain the given key, or if the given key
	 * is {@code null}, this method does nothing.<br>
	 * Note: do not use this method for removing entries while iterating - use
	 * {@link IteratorImpl#remove()} instead.
	 * 
	 * @param key The given key.
	 */
	public void remove(Object key) {
		removeEntry(key);
	}
	
	/**
	 * Returns {@code true} if the number of currently stored pairs is 0.
	 * 
	 * @return {@code true} if 0 pairs are currently stored in the table,
	 * 			and {@code false} otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Clears all pairs from the table
	 * (makes all pairs eligible for garbage collection).
	 */
	public void clear() {
		for (int i = 0; i < table.length; i ++) {
			table[i] = null;
		}
		
		size = 0;
		modificationCount ++;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		
		int entryCounter = 0;
		for (TableEntry<K, V> entry : table) {
			while (entry != null) {
				builder.append(entry);
				entryCounter ++;
				
				if (entryCounter < size) {
					builder.append(", ");
				}
				
				entry = entry.next;
			}
		}
		
		builder.append("]");
		
		return builder.toString();
	}
	
	/**
	 * Calculates the position of the pair with the given key
	 * in the current table.
	 * 
	 * @param key The given key.
	 * @return The position (slot) in the table.
	 */
	private int position(Object key) {
		return position(key, table.length);
	}
	
	/**
	 * Calculates the position of the pair with the given key
	 * in a table with given capacity.
	 * 
	 * @param key The given key.
	 * @param capacity The given capacity.
	 * @return The position (slot) in the table.
	 */
	private int position(Object key, int capacity) {
		return Math.abs(key.hashCode()) % capacity;
	}
	
	/**
	 * Adds the key-value pair at the given position (at the end of the list),
	 * or (if the pair with the given key already existed in the list) simply
	 * changes the value of the corresponding {@code TableEntry}.
	 * 
	 * @param key The given key.
	 * @param value The given value.
	 * @param position The given position.
	 * @return {@code true} if the pair was added to the end of the list,
	 * 			{@code false} if the pair with the given key already existed.
	 */
	private boolean addToList(K key, V value, int position) {
		return addToList(key, value, position, table);
	}
	
	/**
	 * Adds the key-value pair at the given position of the given table
	 * (at the end of the list), or (if the pair with the given key
	 * already existed in the list) simply changes the value
	 * of the corresponding {@code TableEntry}.
	 * 
	 * @param key The given key.
	 * @param value The given value.
	 * @param position The given position.
	 * @param table The given table.
	 * @return {@code true} if the pair was added to the end of the list,
	 * 			{@code false} if the pair with the given key already existed.
	 */
	private boolean addToList(K key, V value, int position, TableEntry<K, V>[] table) {
		TableEntry<K, V> entry = table[position];
		
		if (entry == null) {
			table[position] = new TableEntry<>(key, value);
		} else {
			while (entry != null) {
				if (entry.key.equals(key)) {
					entry.value = value;
					return false;
				}
				
				if (entry.next == null) {
					entry.next = new TableEntry<>(key, value);
					break;
				} else {
					entry = entry.next;
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Doubles the table's capacity (by creating a new table with copied
	 * {@code TableEntry} references.
	 */
	private void expandTable() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = (TableEntry<K, V>[]) new TableEntry[2 * table.length];
		
		for (TableEntry<K, V> entry : table) {
			while (entry != null) {
				int position = position(entry.key, newTable.length);
				addToList(entry.key, entry.value, position, newTable);
				
				entry = entry.next;
			}
		}
		
		table = newTable;
	}
	
	/**
	 * Removes the given key's corresponding {@code TableEntry}
	 * from the table.<br>
	 * If the table does not contain the given key, or if the given key
	 * is {@code null}, this method does nothing, and simply returns false.
	 * 
	 * @param key The given key.
	 * @return {@code true} if the corresponding pair was removed,
	 * 			{@code false} there was no such pair.
	 */
	private boolean removeEntry(Object key) {
		if (key == null) {
			return false;
		}
		
		int position = position(key);
		TableEntry<K, V> entry = table[position];
		
		if (entry == null) {
			return false;
		}
		
		if (entry.key.equals(key)) {
			table[position] = entry.next;
		} else {
			while (entry.next != null) {
				if (entry.next.key.equals(key)) {
					entry.next = entry.next.next;
					break;
				}
				
				entry = entry.next;
			}
			
			if (entry.next == null) {
				return false;
			}
		}
		
		size --;
		modificationCount ++;
		
		return true;
	}
	
	/**
	 * Class representing a key-value pair stored
	 * in a {@code SimpleHashtable} slot.<br>
	 * It also contains a reference to the next pair in the slot.
	 * 
	 * @author Mate Gasparini
	 *
	 * @param <K> Type of the key stored.
	 * @param <V> Type of the value stored.
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * Key of the pair.
		 */
		private K key;
		/**
		 * Value of the pair.
		 */
		private V value;
		/**
		 * Reference to the next pair in the slot list.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Constructor specifying the pair's key and value.
		 * 
		 * @param key The specified key.
		 * @param value The specified value.
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Returns the pair's key.
		 * 
		 * @return The pair's key.
		 */
		public K getKey() {
			return key;
		}
		
		/**
		 * Returns the pair's value.
		 * 
		 * @return The pair's value.
		 */
		public V getValue() {
			return value;
		}
		
		/**
		 * Sets the value to the specified value.
		 * 
		 * @param value The specified value.
		 */
		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return new StringBuilder()
					.append(key).append("=").append(value).toString();
		}
	}
	
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Class representing an iterator implementation
	 * for the {@code SimpleHashtable} class.
	 * 
	 * @author Mate Gasparini
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		
		/**
		 * Pair that the iterator is currently pointing to.
		 */
		private TableEntry<K, V> currentEntry;
		/**
		 * Pair that the iterator will be pointing to next.
		 */
		private TableEntry<K, V> nextEntry;
		/**
		 * Table slot of the {@code currentEntry}.
		 */
		private int currentTableSlot = -1;
		/**
		 * Number of (any structure-changing) modifications performed
		 * by this iterator on the table.
		 */
		private int modificationCount;
		
		/**
		 * Default constructor.
		 */
		public IteratorImpl() {
			if (size != 0) {
				moveToNextSlot();
			}
			
			modificationCount = SimpleHashtable.this.modificationCount;
		}
		
		@Override
		public boolean hasNext() {
			if (modificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			return nextEntry != null;
		}
		
		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (modificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if (nextEntry == null) {
				throw new NoSuchElementException(
					"End of the table has been reached."
				);
			}
			
			currentEntry = nextEntry;
			moveToNextEntry();
			
			return currentEntry;
		}
		
		@Override
		public void remove() {
			if (modificationCount != SimpleHashtable.this.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (currentEntry == null) {
				throw new IllegalStateException();
			}
			
			if (SimpleHashtable.this.removeEntry(currentEntry.key)) {
				modificationCount ++;
				currentEntry = null;
			}
		}
		
		/**
		 * Moves the {@code nextEntry} reference to the first next
		 * non-empty table slot, and returns true. If no empty slot
		 * is found, false is returned instead.
		 * 
		 * @return {@code true} if the {@code nextEntry} is moved to the
		 * 			next non-empty slot, or {@code false} if there were only
		 * 			empty slots found.
		 */
		private boolean moveToNextSlot() {
			for (int i = currentTableSlot + 1; i < table.length; i ++) {
				if (table[i] != null) {
					nextEntry = table[i];
					currentTableSlot = i;
					
					return true;
				}
			}
			
			return false;
		}
		
		/**
		 * Moves the {@code nextEntry} reference to the next
		 * {@code TableEntry}, possibly in a different table slot.
		 * If it was already pointing to the last {@code TableEntry},
		 * it is set to {@code null}.
		 */
		private void moveToNextEntry() {
			if (nextEntry.next != null) {
				nextEntry = nextEntry.next;
			} else {
				if (!moveToNextSlot()) {
					nextEntry = null; // End has been reached.
				}
			}
		}
	}
}
