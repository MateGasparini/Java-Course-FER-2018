package hr.fer.zemris.java.custom.collections;

/**
 * Class that provides stack functionality
 * through an array-based collection.
 * 
 * @author Mate Gasparini
 */
public class ObjectStack {
	
	/**
	 * Adaptee class for this class which is the Adaptor.
	 */
	private ArrayIndexedCollection collection;
	
	/**
	 * Default class constructor.
	 */
	public ObjectStack() {
		collection = new ArrayIndexedCollection();
	}
	
	/**
	 * Returns <code>true</code> if this stack contains no objects,
	 * and <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Returns the number of currently stored objects in this stack.
	 * 
	 * @return Number of stored objects.
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Pushes (adds) the given object to the top of this stack.
	 * 
	 * @param value The given object.
	 */
	public void push(Object value) {
		collection.add(value);
	}
	
	/**
	 * Pops (removes and returns) the object from the top of this stack.
	 * 
	 * @return The popped object.
	 */
	public Object pop() {
		try {
			Object popped = collection.get(size() - 1);
			collection.remove(size() - 1);
			return popped;
		} catch (IndexOutOfBoundsException ex) {
			throw new EmptyStackException(
				"Pop not possible because stack is empty"
			);
		}
	}
	
	/**
	 * Returns the object from the top of this stack (without removing it from
	 * the stack).
	 * 
	 * @return The peeked object.
	 */
	public Object peek() {
		try {
			return collection.get(size() - 1);
		} catch (IndexOutOfBoundsException ex) {
			throw new EmptyStackException(
				"Peek not possible because stack is empty."
			);
		}
	}
	
	/**
	 * Removes all elements from this stack.
	 */
	public void clear() {
		collection.clear();
	}
}
