package hr.fer.zemris.java.hw01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Program that constantly reads the user's input
 * and, if the input is a valid integer, it prints
 * its factorial.
 * The end is reached by entering a <code>String</code>
 * with the same content as <code>END_INPUT</code>.
 * 
 * @author Mate Gasparini
 */
public class Factorial {
	
	private final static int FACTORIAL_LOWEST = 1;
	private final static int FACTORIAL_HIGHEST = 20;
	/**
	 * String that, when entered, ends program execution.
	 */
	public final static String END_INPUT = "kraj";
	
	/**
	 * Method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(System.in))) {
			
			while (true) {
				System.out.print("Unesite broj > ");
				String lineOfInput = null;
				
				try {
					lineOfInput = reader.readLine();
					
					int number = Integer.parseInt(lineOfInput);
					long factorial = calculateFactorial(number);
					
					System.out.println(number + "! = " + factorial);
				} catch (NumberFormatException ex) {
					if (lineOfInput.equals(END_INPUT)) {
						break;
					}
					
					System.out.println("'" + lineOfInput + "' nije cijeli broj.");
				} catch (IllegalArgumentException ex) {
					System.out.println(ex.getMessage());
				}
			}
		} catch (IOException ex) {
			System.out.println("Unos vrijednosti više nije moguć.");
		}
		
		System.out.println("Doviđenja.");
	}
	
	/**
	 * Method that calculates the factorial of the
	 * given number.
	 * 
	 * @param number Number whose factorial needs to be calculated.
	 * @return Factorial value of <code>number</code>.
	 * @throws IllegalArgumentException Thrown when <code>number</code>
	 * 									out of range.
	 */
	public static long calculateFactorial(int number) throws IllegalArgumentException {
		if (number < FACTORIAL_LOWEST || number > FACTORIAL_HIGHEST) {
			throw new IllegalArgumentException(
				"'" + number + "' nije broj u dozvoljenom rasponu."
			);
		}
		
		long factorial = 1;
		for (int i = 2; i <= number; i++) {
			factorial *= i;
		}
		
		return factorial;
	}
}
