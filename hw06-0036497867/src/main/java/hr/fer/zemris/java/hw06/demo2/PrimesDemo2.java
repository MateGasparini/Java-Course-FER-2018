package hr.fer.zemris.java.hw06.demo2;

/**
 * Program which demonstrates nested {@code PrimesCollection} iteration.
 * 
 * @author Mate Gasparini
 */
public class PrimesDemo2 {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: "
						+ prime + ", " + prime2
				);
			}
		}
	}
}
