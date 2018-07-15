package hr.fer.zemris.java.hw06.demo2;

/**
 * Program which demonstrates {@code PrimesCollection} iteration.
 * 
 * @author Mate Gasparini
 */
public class PrimesDemo1 {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
