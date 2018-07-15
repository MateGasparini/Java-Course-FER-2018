package hr.fer.zemris.java.hw06.observer2;

/**
 * Implementation of the {@link IntegerStorageObserver} interface
 * which prints the double of the changed value.
 * Also, it counts the number of times the value has changed and,
 * if the value changed specified number of times, it is removed
 * (de-registered) from the {@code IntegerStorage}.
 * 
 * @author Mate Gasparini
 */
public class DoubleValue implements IntegerStorageObserver {
	
	/**
	 * Limit number of value changes.
	 */
	private int limitNumberOfTimes;
	/**
	 * Number of value changes since registration.
	 */
	private int currentNumberOfTimes;
	
	/**
	 * Constructor specifying the limit number of times of value
	 * changes until the observer will be de-registered from the
	 * {@code IntegerStorage}.
	 * 
	 * @param limitNumberOfTimes The limit number of value changes.
	 * @throws IllegalArgumentException If the given limit number
	 * 			is lower or equal to zero.
	 */
	public DoubleValue(int limitNumberOfTimes) {
		if (limitNumberOfTimes <= 0) {
			throw new IllegalArgumentException(
				"Limit number of times must be a positive number."
			);
		}
		
		this.limitNumberOfTimes = limitNumberOfTimes;
	}
	
	@Override
	public void valueChanged(IntegerStorageChange change) {
		System.out.println("Double value: " + change.getNewValue()*2);
		
		currentNumberOfTimes ++;
		if (currentNumberOfTimes == limitNumberOfTimes) {
			change.getIntegerStorage().removeObserver(this);
		}
	}
}
