package hr.fer.zemris.java.hw06.observer2;

/**
 * Class that encapsulates all needed information for the change
 * that was made.<br>
 * It contains a reference to the corresponding {@code IntegerStorage},
 * its old value and its new value.
 * 
 * @author Mate Gasparini
 */
public class IntegerStorageChange {
	
	/**
	 * Reference to the corresponding {@code IntegerStorage}.
	 */
	private IntegerStorage integerStorage;
	/**
	 * Value that was stored before the change.
	 */
	private int oldValue;
	/**
	 * Value after the change.
	 */
	private int newValue;
	
	/**
	 * Constructor specifying the {@code IntegerStorage} reference, its old
	 * and its new value.
	 * 
	 * @param integerStorage The specified {@code IntegerStorage} reference.
	 * @param oldValue The specified old value.
	 * @param newValue The specified new value.
	 */
	public IntegerStorageChange(IntegerStorage integerStorage, int oldValue, int newValue) {
		this.integerStorage = integerStorage;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	/**
	 * Returns the {@code IntegerStorage} reference.
	 * 
	 * @return The {@code IntegerStorage} reference.
	 */
	public IntegerStorage getIntegerStorage() {
		return integerStorage;
	}
	
	/**
	 * Returns the old value (before the change).
	 * 
	 * @return The old value.
	 */
	public int getOldValue() {
		return oldValue;
	}
	
	/**
	 * Returns the new value (after the change).
	 * 
	 * @return The new value.
	 */
	public int getNewValue() {
		return newValue;
	}
}
