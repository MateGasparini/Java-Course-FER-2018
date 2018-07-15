package hr.fer.zemris.java.custom.collections;

/**
 * Class that represents a linked list-backed collection of objects.
 * Duplicate elements are allowed, but storage of null references is not.
 * 
 * @author Mate Gasparini
 */
public class LinkedListIndexedCollection extends Collection {
	
	/**
	 * Number of currently stored elements.
	 */
	private int size;
	/**
	 * Reference to the first node of the list.
	 */
	private ListNode first;
	/**
	 * Reference to the last node of the list.
	 */
	private ListNode last;
	
	/**
	 * Static nested class representing a node of a doubly linked list.
	 * It contains a reference for value storage, and references to the
	 * next and to the previous node in the list.
	 * 
	 * @author Mate Gasparini
	 */
	private static class ListNode {
		/**
		 * The value of the node.
		 */
		private Object value;
		/**
		 * Reference to the next node in the list.
		 */
		private ListNode next;
		/**
		 * Reference to the previous node in the list.
		 */
		private ListNode previous;
	}
	
	/**
	 * Default empty list constructor.
	 */
	public LinkedListIndexedCollection() {
		first = null;
		last = null;
	}
	
	/**
	 * Constructor specifying another collection whose elements are
	 * copied to this collection.
	 * 
	 * @param other The other collection.
	 */
	public LinkedListIndexedCollection(Collection other) {
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
		ListNode currentNode = first;
		
		while (currentNode != null && !currentNode.value.equals(value)) {
			currentNode = currentNode.next;
		}
		
		if (currentNode == null) {
			return false;
		} else {
			if (size == 1) {
				first = null;
				last = null;
			} else if (currentNode == first) {
				first = first.next;
				first.previous = null;
			} else if (currentNode == last) {
				last = last.previous;
				last.next = null;
			} else {
				currentNode.previous.next = currentNode.next;
				currentNode.next.previous = currentNode.previous;
				
				currentNode.previous = null;
				currentNode.next = null;
			}
			size --;
			
			return true;
		}
	}
	
	@Override
	public Object[] toArray() {
		Object[] array = new Object[size];
		
		ListNode currentNode = first;
		int currentIndex = 0;
		while (currentNode != null) {
			array[currentIndex] = currentNode.value;
			currentNode = currentNode.next;
			currentIndex ++;
		}
		
		return array;
	}
	
	@Override
	public void forEach(Processor processor) {
		ListNode currentNode = first;
		while (currentNode != null) {
			processor.process(currentNode.value);
			currentNode = currentNode.next;
		}
	}
	
	/**
	 * Adds the given object at the end of the collection. This is performed
	 * in constant time.
	 * 
	 * @param value The given object.
	 * @throws NullPointerException If <code>value</code> is <code>null</code>.
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Value must not be null.");
		}
		
		ListNode newNode = new ListNode();
		newNode.value = value;
		newNode.previous = last;
		
		if (last != null) {
			last.next = newNode;
		} else {
			first = newNode;
		}
		last = newNode;
		
		size ++;
	}
	
	/**
	 * Returns the object that is stored at position <code>index</code>.
	 * 
	 * @param index Position of the object in the collection.
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
		
		ListNode currentNode;
		if (index < size / 2) {
			currentNode = first;
			for (int i = 0; i < index; i ++) {
				currentNode = currentNode.next;
			}
		} else {
			currentNode = last;
			for (int i = size - 1; i > index; i --) {
				currentNode = currentNode.previous;
			}
		}
		
		return currentNode.value;
	}
	
	@Override
	public void clear() {
		first = null;
		last = null;
		size = 0;
	}
	
	/**
	 * Inserts the given <code>value</code> at the given <code>position</code>.
	 * If there are elements at <code>position</code> or at greater positions,
	 * they are shifted one place toward the end.
	 * 
	 * @param value Object to insert to the collection's list.
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
		
		if (position == 0) {
			ListNode newNode = new ListNode();
			newNode.value = value;
			newNode.next = first;
			
			if (first != null) {
				first.previous = newNode;
			} else {
				last = newNode;
			}
			first = newNode;
			
			size ++;
		} else if (position == size) {
			// Variable size is changed.
			add(value);
		} else {
			ListNode newNode = new ListNode();
			newNode.value = value;
			
			ListNode currentNode;
			
			if (position <= size / 2) {
				currentNode = first;
				for (int i = 0; i < position - 1; i ++) {
					currentNode = currentNode.next;
				}
			} else {
				currentNode = last;
				for (int i = size - 1; i > position - 1; i --) {
					currentNode = currentNode.previous;
				}
			}
			
			newNode.next = currentNode.next;
			newNode.next.previous = newNode;
			newNode.previous = currentNode;
			currentNode.next = newNode;
			
			size ++;
		}
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
			ListNode currentNode = first;
			int currentIndex = 0;
			
			while (currentNode != null) {
				if (currentNode.value.equals(value)) {
					return currentIndex;
				}
				
				currentNode = currentNode.next;
				currentIndex ++;
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
		
		if (size == 1) {
			first = null;
			last = null;
		} else if (index == 0) {
			first = first.next;
			first.previous = null;
		} else if (index == size - 1) {
			last = last.previous;
			last.next = null;
		} else {
			ListNode currentNode;
			
			if (index <= size / 2) {
				currentNode = first;
				for (int i = 0; i < index; i ++) {
					currentNode = currentNode.next;
				}
			} else {
				currentNode = last;
				for (int i = size - 1; i > index; i ++) {
					currentNode = currentNode.previous;
				}
			}
			
			currentNode.previous.next = currentNode.next;
			currentNode.next.previous = currentNode.previous;
			
			currentNode.previous = null;
			currentNode.next = null;
		}
		
		size --;
	}
}
