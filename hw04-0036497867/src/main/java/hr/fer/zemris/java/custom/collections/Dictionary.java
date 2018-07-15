package hr.fer.zemris.java.custom.collections;

/**
 * Class that provides dictionary functionality
 * through an array-based collection of key-value pairs.
 * 
 * @author Mate Gasparini
 */
public class Dictionary {
	
	/**
	 * Adaptee class for this class which is the Adaptor.
	 */
	private ArrayIndexedCollection collection;
	
	/**
	 * Default constructor.
	 */
	public Dictionary() {
		collection = new ArrayIndexedCollection();
	}
	
	/**
	 * Returns <code>true</code> if this dictionary contains no pairs,
	 * and <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Returns the number of currently stored pairs in this dictionary.
	 * 
	 * @return Number of stored objects.
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Removes all pairs from this dictionary.
	 */
	public void clear() {
		collection.clear();
	}
	
	/**
	 * Adds the pair specified by the given key and the given value
	 * to this dictionary. If the dictionary already contains a pair
	 * with the given key, the old value is overwritten by the given value.
	 * 
	 * @param key The given key.
	 * @param value The given value.
	 * @throws NullPointerException If the given key is null.
	 */
	public void put(Object key, Object value) {
		if (key == null) {
			throw new NullPointerException(
				"Key cannot be null."
			);
		}
		
		int index = collection.indexOf(new Entry(key, null));
		if (index != -1) {
			collection.remove(index);
		}
		
		collection.add(new Entry(key, value));
	}
	
	/**
	 * Returns the value from the pair which corresponds to the given key.
	 * If there is no such pair, null is returned.
	 * 
	 * @param key The given key.
	 * @return The corresponding value, or <code>null</code> if not found.
	 * @throws NullPointerException If the given key is null.
	 */
	public Object get(Object key) {
		if (key == null) {
			throw new NullPointerException(
				"Key cannot be null."
			);
		}
		
		int index = collection.indexOf(new Entry(key, null));
		if (index == -1) {
			return null;
		}
		
		return ((Entry) collection.get(index)).value;
	}
	
	/**
	 * Static class which represents a dictionary (key-value) pair.
	 * 
	 * @author Mate Gasparini
	 */
	private static class Entry {
		
		/**
		 * The unique key specifying the pair, cannot be null.
		 */
		private Object key;
		/**
		 * The value for the specified key, can be null.
		 */
		private Object value;
		
		/**
		 * Constructor specifying the key and the value.
		 * 
		 * @param key The given key.
		 * @param value The given value.
		 */
		public Entry(Object key, Object value) {
			this.key = key;
			this.value = value;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Entry) {
				Entry other = (Entry) obj;
				return this.key.equals(other.key);
			}
			
			return false;
		}
	}
}
