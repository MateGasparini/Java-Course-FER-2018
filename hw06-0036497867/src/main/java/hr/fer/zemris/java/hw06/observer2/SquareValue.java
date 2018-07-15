package hr.fer.zemris.java.hw06.observer2;

/**
 * Implementation of the {@link IntegerStorageObserver} interface
 * which prints the square value of each changed value.
 * 
 * @author Mate Gasparini
 */
public class SquareValue implements IntegerStorageObserver {
	
	@Override
	public void valueChanged(IntegerStorageChange change) {
		long value = change.getNewValue();
		
		System.out.println("Provided new value: " + value
			+ ", square is " + value*value
		);
	}
}
