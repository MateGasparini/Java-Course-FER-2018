package hr.fer.zemris.java.hw06.observer1;

/**
 * Implementation of the {@link IntegerStorageObserver} interface
 * which counts the number of times the value has changed.
 * 
 * @author Mate Gasparini
 */
public class ChangeCounter implements IntegerStorageObserver {
	
	/**
	 * Number of times the value has changed.
	 */
	private int counter;
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		counter ++;
		
		System.out.println("Number of value changes since tracking: " + counter);
	}
}
