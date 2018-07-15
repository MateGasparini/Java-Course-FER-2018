package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * 
 * @author Mate Gasparini
 */
public class Node {
	
	/**
	 * Internally managed collection of children.
	 * First created when adding the first child.
	 */
	private ArrayIndexedCollection children;
	
	/**
	 * Adds the given child to the internally managed collection of children.
	 * 
	 * @param child The given child.
	 * @throws IllegalArgumentException
	 * 			If <code>child</code> is <code>null</code>.
	 */
	public void addChildNode(Node child) {
		// Don't even create the collection if the given child is null.
		if (child == null) {
			throw new IllegalArgumentException(
				"Child node cannot be null."
			);
		}
		
		if (children == null) {
			children = new ArrayIndexedCollection();
		}
		
		children.add(child);
	}
	
	/**
	 * Returns the number of (direct) children.
	 * 
	 * @return Number of children stored in the internal collection.
	 */
	public int numberOfChildren() {
		if (children == null) {
			return 0;
		}
		
		return children.size();
	}
	
	/**
	 * Returns the child at selected <code>index</code> (if valid).
	 * 
	 * @param index Child's specified position in the internal collection.
	 * @return Reference to the selected child.
	 * @throws IndexOutOfBoundsException If <code>index</code> is not valid.
	 */
	public Node getChild(int index) {
		// Casting is always fine because only Node references are stored.
		return (Node) children.get(index);
	}
}
