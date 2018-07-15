package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that represents an iterable fixed-size collection
 * of prime numbers.<br>
 * Primes are not stored anywhere and are generated dynamically
 * (when needed).
 * 
 * @author Mate Gasparini
 */
public class PrimesCollection implements Iterable<Integer> {
	
	/**
	 * Specified size of the collection.
	 */
	private int size;
	
	/**
	 * Constructor specifying the collection's size (number
	 * of primes that can be generated).
	 * 
	 * @param size The collection's specified size.
	 */
	public PrimesCollection(int size) {
		this.size = size;
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new IteratorImpl();
	}
	
	private int generateNthPrime(int n) {
		if (n < 1) {
			throw new IllegalArgumentException(
				"Index of the wanted prime number must be positive."
			);
		}
		
		int potential = 2;
		int count = 0;
		while (true) {
			if (isPrime(potential)) {
				count ++;
			}
			
			if (count == n) {
				break;
			}
			
			potential ++;
		}
		
		return potential;
	}
	
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
	
	/**
	 * Implemented iterator for the {@code PrimeCollection}.
	 * 
	 * @author Mate Gasparini
	 */
	private class IteratorImpl implements Iterator<Integer> {
		
		/**
		 * If zero, iteration not yet started.
		 * Otherwise, this is the index of the last generated prime number.
		 */
		private int currentIndex;
		
		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}
		
		/**
		 * @throws NoSuchElementException If the end has been reached.
		 */
		@Override
		public Integer next() {
			if (hasNext()) {
				int nextPrime = generateNthPrime(++ currentIndex);
				return nextPrime;
			}
			
			throw new NoSuchElementException(
				"End of the collection has been reached"
			);
		}
	}
}
