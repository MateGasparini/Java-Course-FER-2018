package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Class providing storage for an integer value and representing
 * a <i>Subject</i> class for the {@link IntegerStorageObserver}
 * <i>Observers</i>.
 * 
 * @author Mate Gasparini
 */
public class IntegerStorage {
	
	/**
	 * Currently stored value.
	 */
	private int value;
	/**
	 * List of registered observers.
	 */
	private List<IntegerStorageObserver> observers = new ArrayList<>();
	
	/**
	 * Constructor specifying the initial value stored.
	 * 
	 * @param initialValue The specified initial value.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}
	
	/**
	 * Adds the given observer to the internal {@code List} of observers.
	 * 
	 * @param observer The given observer.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}
	
	/**
	 * Removes the given observer from the internal {@code List} of observers.
	 * 
	 * @param observer The given observer.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}
	
	/**
	 * Clears the internal {@code List} of observers.
	 */
	public void clearObservers() {
		observers.clear();
	}
	
	/**
	 * Returns the stored value.
	 * 
	 * @return The stored value.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Updates the stored value with the given value and,
	 * if the stored value changed, calls
	 * {@link IntegerStorageObserver#valueChanged(IntegerStorage)}
	 * for each observer from the internal {@code List} of observers.
	 * 
	 * @param value The given value.
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				for (int i = 0, size = observers.size(); i < size;) {
					IntegerStorageObserver observer = observers.get(i);
					observer.valueChanged(this);
					
					if (observers.size() == size) {
						// If all observers still registered, iterate as usual.
						i ++;
					} else {
						// Otherwise, just update the iteration size.
						size = observers.size();
					}
				}
			}
		}
	}
}
