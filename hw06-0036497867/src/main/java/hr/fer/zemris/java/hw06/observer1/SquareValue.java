package hr.fer.zemris.java.hw06.observer1;

/**
 * Implementation of the {@link IntegerStorageObserver} interface
 * which prints the square value of each changed value.
 * 
 * @author Mate Gasparini
 */
public class SquareValue implements IntegerStorageObserver {
	
	@Override
	public void valueChanged(IntegerStorage istorage) {
		long value = istorage.getValue();
		
		System.out.println("Provided new value: " + value
			+ ", square is " + value*value
		);
	}
}
