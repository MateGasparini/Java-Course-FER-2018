package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * {@link ListModel} which stores only prime numbers.<br>
 * They can be generated and added in order, but not removed once added.
 * 
 * @author Mate Gasparini
 */
public class PrimListModel implements ListModel<Integer> {
	
	/**
	 * The {@code List} of currently stored prime numbers.
	 */
	private List<Integer> numbers = new ArrayList<>();
	/**
	 * The {@code List} containing all list data listeners.
	 */
	private List<ListDataListener> listeners = new ArrayList<>();
	/**
	 * Last generated prime number.
	 * Initially set to 1 as the model initially contains it.
	 */
	private int lastGenerated = 1;
	
	/**
	 * Default class constructor containing only the number 1.
	 */
	public PrimListModel() {
		numbers.add(1);
	}
	
	@Override
	public int getSize() {
		return numbers.size();
	}
	
	@Override
	public Integer getElementAt(int index) {
		return numbers.get(index);
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}
	
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.add(l);
	}
	
	/**
	 * When called, a next prime number is generated and added
	 * to the internal list of prime numbers.
	 */
	public void next() {
		while (true) {
			if (isPrime(++ lastGenerated)) {
				break;
			}
		}
		addLastGenerated();
	}
	
	/**
	 * Adds the last generated prime number to the internal number list.
	 */
	private void addLastGenerated() {
		int position = numbers.size();
		numbers.add(lastGenerated);
		
		ListDataEvent event = new ListDataEvent(
			this, ListDataEvent.INTERVAL_ADDED, position, position
		);
		listeners.forEach(l -> l.intervalAdded(event));
	}
	
	/**
	 * Tests if the given number is prime or not.
	 * 
	 * @param number The given number.
	 * @return {@code true} if the given number is prime,
	 * 			or {@code false} otherwise.
	 */
	private boolean isPrime(int number) {
		if (number < 2) {
			return false;
		} else if (number == 2) {
			return true;
		} else if (number % 2 == 0) {
			return false;
		}
		
		for (int i = 3; i*i <= number; i += 2) {
			if (number % i == 0) {
				return false;
			}
		}
		
		return true;
	}
}
