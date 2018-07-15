package hr.fer.zemris.java.hw06.observer2;

/**
 * Program which demonstrates the functionality of the {@code IntegerStorage}
 * class and different {@code IntegerStorageObserver} implementations.
 * 
 * @author Mate Gasparini
 */
public class ObserverExample {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		
		IntegerStorageObserver observer = new SquareValue();
		
		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		
		//istorage.removeObserver(observer);
		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
