package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents a resizable array-backed collection of objects.
 * Duplicate elements are allowed, but storage of null references is not.
 * 
 * @author Mate Gasparini
 */
public class ArrayIndexedCollection extends Collection {
	
	/**
	 * Number of currently stored elements.
	 */
	private int size;
	/**
	 * Current capacity of allocated array of object references.
	 */
	private int capacity;
	/**
	 * Array of object references.
	 */
	private Object[] elements;
	
	/**
	 * Default capacity of a new collection's array.
	 */
	public final static int DEFAULT_CAPACITY = 16;
	
	/**
	 * Default constructor whose initial capacity is specified
	 * by <code>DEFAULT_CAPACITY</code>.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor specifying the initial capacity.
	 * 
	 * @param initialCapacity Collection's initial capacity.
	 * @throws IllegalArgumentException If <code>initialCapacity</code>
	 * 			non-positive.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException(
				"Initial capacity must be positive. "
				+ "Was: " + initialCapacity + "."
			);
		}
		
		capacity = initialCapacity;
		elements = new Object[capacity];
	}
	
	/**
	 * Constructor specifying another collection whose elements are
	 * copied to this collection. Initially, capacity is set to
	 * <code>DEFAULT_CAPACITY</code>, or (if needed) to the size
	 * of the other collection.
	 * 
	 * @param other The other collection.
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}
	
	/**
	 * Constructor specifying another collection whose elements are
	 * copied to this collection. Initially, capacity is set to
	 * <code>initialCapacity</code>, or (if needed) to the size
	 * of the other collection.
	 * 
	 * @param other The other collection.
	 * @param initialCapacity Preferred initial collection's capacity.
	 * @throws NullPointerException If <code>other</code> is <code>null</code>.
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(
			(other != null && other.size() > initialCapacity) ?
			other.size() : initialCapacity
		);
		
		if (other == null) {
			throw new NullPointerException(
				"Given collection must not be null."
			);
		}
		
		addAll(other);
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}
	
	/**
	 * Returns <code>true</code> if the collection contains the given
	 * <code>value</code> and removes it's first occurrence.
	 * 
	 * @param value The given object.
	 * @return <code>true</code> if the given object was found and removed,
	 * 			<code>false</code> otherwise.
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		
		if (index != -1) {
			// Variable size is changed.
			remove(index);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		for (int i = 0; i < size; i ++) {
			array[i] = elements[i];
		}
		
		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i ++) {
			processor.process(elements[i]);
		}
	}
	
	/**
	 * Adds the given object's reference to the array's first empty place.
	 * If the array is full, it is first reallocated by doubling it's size.
	 * 
	 * @param value The given object.
	 * @throws NullPointerException If <code>value</code> is <code>null</code>.
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Value must not be null.");
		}
		
		if (size == capacity) {
			capacity *= 2;
			
			Object[] elementsExpanded = new Object[capacity];
			for (int i = 0; i < size; i ++) {
				elementsExpanded[i] = elements[i];
			}
			
			elements = elementsExpanded;
		}
		
		elements[size ++] = value;
	}
	
	/**
	 * Returns the object that is stored at position <code>index</code>.
	 * 
	 * @param index Position of the object in the collection's array.
	 * @return Object from the specified position.
	 * @throws IndexOutOfBoundsException If <code>index</code> is negative, or
	 * 			if it is greater or equal to the current collection's size.
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(
				"Index must be non-negative and lower than " + size + ". "
				+ "Was: " + index + "."
			);
		}
		
		return elements[index];
	}
	
	@Override
	public void clear() {
		for (int i = 0; i < size; i ++) {
			elements[i] = null;
		}
		
		size = 0;
	}
	
	/**
	 * Inserts the given <code>value</code> at the given <code>position</code>.
	 * If there are elements at <code>position</code> or at greater positions,
	 * they are shifted one place toward the end.
	 * 
	 * @param value Object to insert to the collection's array.
	 * @param position Specified insertion index.
	 * @throws IndexOutOfBoundsException If <code>position</code> is negative
	 * 			or higher than the current collection's size.
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException(
				"Position must be non-negative and must not be higher than "
				+ size + ". Was: " + position + "."
			);
		}
		
		// Variable size is changed.
		add(value); // Guarantees there is room for the new value.
		
		for (int i = size - 1; i > position; i --) {
			elements[i] = elements[i - 1];
		}
		
		elements[position] = value;
	}
	
	/**
	 * Returns the index of the first occurrence of <code>value</code>
	 * (determined by the <code>equals</code> method).
	 * If <code>value</code> is <code>null</code> or if it does not exist
	 * in the collection, -1 is returned.
	 * 
	 * @param value Given value.
	 * @return Index of given value's first occurrence, or -1 if not found.
	 */
	public int indexOf(Object value) {
		if (value != null) {
			for (int i = 0; i < size; i ++) {
				if (elements[i].equals(value)) {
					return i;
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * Removes the element at position specified by <code>index</code>.
	 * All elements at greater positions are shifted toward the beginning.
	 * 
	 * @param index Specified element position.
	 * @throws IndexOutOfBoundsException If <code>index</code> is negative, or
	 * 			if it is greater or equal to the current collection's size.
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(
				"Index must be non-negative and lower than " + size + ". "
				+ "Was: " + index + "."
			);
		}
		
		for (int i = index; i < size - 1; i ++) {
			elements[i] = elements[i + 1];
		}
		
		elements[-- size] = null;
	}
}
