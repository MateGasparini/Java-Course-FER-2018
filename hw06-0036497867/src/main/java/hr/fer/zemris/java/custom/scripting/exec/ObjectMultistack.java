package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class that represents a special kind of {@code Map}.<br>
 * It allows the user to store multiple values for each key,
 * providing stack-like abstraction.<br>
 * Keys are {@code String} references, and values are {@code ValueWrapper}
 * references wrapped as {@code MultistackEntry} references
 * that build the corresponding stack.
 * 
 * @author Mate Gasparini
 */
public class ObjectMultistack {
	
	/**
	 * Maps Strings to corresponding stacks.
	 */
	private Map<String, MultistackEntry> map = new HashMap<>();
	
	/**
	 * Pushes the given value wrapper to the stack
	 * specified by the given name.
	 * 
	 * @param name The given name.
	 * @param valueWrapper The given value wrapper.
	 * @throws NullPointerException If either the given value wrapper
	 * 			or the given name is null.
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		Objects.requireNonNull(valueWrapper, "Value wrapper cannot be null.");
		Objects.requireNonNull(name, "Name cannot be null.");
		
		map.put(name, new MultistackEntry(valueWrapper, map.get(name)));
	}
	
	/**
	 * Pops a value wrapper from the stack
	 * specified by the given name and returns it's reference.
	 * 
	 * @param name The given name.
	 * @return The popped value wrapper.
	 * @throws EmptyStackException If the corresponding stack is empty.
	 * @throws NullPointerException If the given name is null.
	 * @throws NonExistentStackException If the corresponding stack
	 * 			is not mapped.
	 */
	public ValueWrapper pop(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");
		
		if (!map.containsKey(name)) {
			throw new NonExistentStackException(
				"Stack with name: " + name + " not mapped."
			);
		}
		
		MultistackEntry poppedEntry = map.get(name);
		
		if (poppedEntry == null) {
			throw new EmptyStackException(
				"Empty stack popping not allowed."
			);
		}
		
		map.put(name, poppedEntry.next);
		
		return poppedEntry.valueWrapper;
	}
	
	/**
	 * Peeks a value wrapper from the stack
	 * specified by the given name and returns it's reference.
	 * 
	 * @param name The given name.
	 * @return The peeked value wrapper.
	 * @throws EmptyStackException If the corresponding stack is empty.
	 * @throws NullPointerException If the given name is null.
	 * @throws NonExistentStackException If the corresponding stack
	 * 			is not mapped.
	 */
	public ValueWrapper peek(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");
		
		if (!map.containsKey(name)) {
			throw new NonExistentStackException(
				"Stack with name: " + name + " not mapped."
			);
		}
		
		MultistackEntry peekedEntry = map.get(name);
		
		if (peekedEntry == null) {
			throw new EmptyStackException(
				"Empty stack peeking not allowed."
			);
		}
		
		return peekedEntry.valueWrapper;
	}
	
	/**
	 * Returns true if the stack specified by the given name has no entries.
	 * 
	 * @param name The given name.
	 * @return {@code true} if the corresponding stack is empty,
	 * 			or {@code false} otherwise.
	 * @throws NullPointerException If the given name is null.
	 * @throws NonExistentStackException If the corresponding stack
	 * 			is not mapped.
	 */
	public boolean isEmpty(String name) {
		Objects.requireNonNull(name, "Name cannot be null.");
		
		if (!map.containsKey(name)) {
			throw new NonExistentStackException(
				"Stack with name: " + name + " not mapped."
			);
		}
		
		return map.get(name) == null;
	}
	
	/**
	 * Represents each entry. It contains a {@code ValueWrapper} reference
	 * and a reference to the next entry (on the stack).
	 * 
	 * @author Mate Gasparini
	 */
	private static class MultistackEntry {
		
		/**
		 * Value wrapper stored in this entry.
		 */
		private ValueWrapper valueWrapper;
		/**
		 * Reference to the next entry of the same stack.
		 */
		private MultistackEntry next;
		
		/**
		 * Constructor specifying the value wrapper and the reference
		 * to the next entry.
		 * 
		 * @param valueWrapper The specified value wrapper.
		 * @param next The specified reference to the next entry.
		 */
		public MultistackEntry(ValueWrapper valueWrapper, MultistackEntry next) {
			this.valueWrapper = valueWrapper;
			this.next = next;
		}
	}
}
