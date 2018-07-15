package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents some general collection of objects.
 * 
 * @author Mate Gasparini
 */
public class Collection {
	
	/**
	 * Default constructor.
	 */
	protected Collection() {
		// Do nothing.
	}
	
	/**
	 * Returns <code>true</code> if this collection contains no objects,
	 * and <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if empty, <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * Returns the number of currently stored objects in this collection.
	 * 
	 * @return Number of stored objects.
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object to this collection.
	 * 
	 * @param value The given object.
	 */
	public void add(Object value) {
		// Do nothing.
	}
	
	/**
	 * Returns <code>true</code> if the collection contains given
	 * <code>value</code> (determined by the <code>equals</code> method).
	 * 
	 * @param value The given object.
	 * @return <code>true</code> if collection contains the given object,
	 * 			<code>false</code> otherwise.
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Returns <code>true</code> if the collection contains the given
	 * <code>value</code> and removes one occurrence of it.
	 * 
	 * @param value The given object.
	 * @return <code>true</code> if the given object was found and removed,
	 * 			<code>false</code> otherwise.
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Allocates a new array with the same size of this collection,
	 * fills it with the same content and returns it.
	 * 
	 * @return A copy of this collection as an array.
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException("Method not implemented.");
	}
	
	/**
	 * Calls <code>processor.process(element)</code> for each
	 * <code>element</code> of this collection.
	 * 
	 * @param processor A <code>Processor</code> specifying the
	 * 					<code>process</code> method.
	 */
	public void forEach(Processor processor) {
		// Do nothing.
	}
	
	/**
	 * Adds all of the other collection's elements to this collection.
	 * The other collection remains unchanged.
	 * 
	 * @param other Other collection.
	 * @throws NullPointerException If <code>other</code> is <code>null</code>.
	 */
	public void addAll(Collection other) {
		if (other == null) {
			throw new NullPointerException(
				"Other collection must not be null."
			);
		}
		
		/**
		 * Local <code>Processor</code> class in which the <code>process</code>
		 * method is implemented to add a value to this collection.
		 * 
		 * @author Mate Gasparini
		 */
		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		LocalProcessor processor = new LocalProcessor();
		
		other.forEach(processor);
	}
	
	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
		// Do nothing.
	}
}
