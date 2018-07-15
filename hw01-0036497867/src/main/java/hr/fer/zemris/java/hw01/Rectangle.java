package hr.fer.zemris.java.hw01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * Program that (for given width and height) calculates
 * and prints the area and perimeter of the corresponding
 * <code>Rectangle</code>.
 * Width and height can be entered as command line arguments,
 * as well as through <code>System.in</code>.
 * 
 * @author Mate Gasparini
 */
public class Rectangle {
	
	private static double width;
	private static double height;
	
	/**
	 * Method which is called when the program starts.
	 * 
	 * @param args Command line arguments representing the width
	 * 				and the height of the <code>Rectangle</code>.
	 */
	public static void main(String[] args) {
		if (args.length == 2) {
			try {
				width = getValidValue(args[0]);
				height = getValidValue(args[1]);
				
				printCalculatedValues();
				
				return;
			} catch (IllegalArgumentException ex) {
				System.err.println(ex.getMessage());
				
				/* If next statement is commented, the program
				 * will continue and try to read from System.in. */
				System.exit(1);
			}
		} else if (args.length != 0) {
			System.err.println(
					"Krivi broj ulaznih argumenata.\n"
					+ "Očekivano {0, 2}, "
					+ "a uneseno : " + args.length + "."
			);
			
			System.exit(1);
		}
		
		try (BufferedReader reader = new BufferedReader(
						new InputStreamReader(System.in))) {
			getValuesFromUser(reader);
			
			printCalculatedValues();
		} catch (IOException ex) {
			System.out.println("Unos vrijednosti više nije moguć.");
		}
	}
	
	/**
	 * Gets valid values for both <code>width</code> and
	 * <code>height</code> from <code>System.in</code>.
	 * 
	 * @param reader Reader used for reading one line at a time.
	 * @throws IOException Chained from <code>reader.readLine()</code>.
	 */
	private static void getValuesFromUser(BufferedReader reader) throws IOException {
		while (true) {
			System.out.print("Unesite širinu > ");
			
			try {
				String line = reader.readLine();
				
				if (line != null) {
					width = getValidValue(line);
					break;
				}
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}
		}
		
		while (true) {
			System.out.print("Unesite visinu > ");
			
			try {
				String line = reader.readLine();
				
				if (line != null) {
					height = getValidValue(line);
					break;
				}
			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	/**
	 * Takes a string and tries to parse it to
	 * <code>double</code> and return it.
	 * 
	 * @param lineOfInput String to parse.
	 * @return Parsed value of <code>lineOfInput</code>.
	 * @throws IllegalArgumentException Thrown if <code>lineOfInput</code>
	 * 			non-parseable or not positive.
	 */
	private static double getValidValue(String lineOfInput) throws IllegalArgumentException {
		String message;
		
		try {
			/* Localization sensitive parsing method.
			 * Alternatively:
			 * double value = Double.parseDouble(lineOfInput); */
			double value = NumberFormat.getInstance()
					.parse(lineOfInput).doubleValue();
			
			if (value > 0.0) {
				return value;
			} else if (value == 0.0) {
				message = "Unijeli ste nulu.";
			} else {
				message = "Unijeli ste negativnu vrijednost.";
			}
		} catch (ParseException ex) {
			message = "'" + lineOfInput + 
					"' se ne može protumačiti kao broj.";
		}
		
		throw new IllegalArgumentException(message);
	}
	
	/**
	 * Calculates the area of a <code>Rectangle</code>
	 * by using <code>width</code> and <code>height</code.
	 * 
	 * @return Calculated area.
	 */
	private static double calculateArea() {
		return width * height;
	}
	
	/**
	 * Calculates the perimeter of a <code>Rectangle</code>
	 * by using <code>width</code> and <code>height</code.
	 * 
	 * @return Calculated perimeter.
	 */
	private static double calculatePerimeter() {
		return 2 * (width + height);
	}
	
	/**
	 * Prints the calculated area and perimeter
	 * of the <code>Rectangle</code>.
	 */
	private static void printCalculatedValues() {
		System.out.printf(
			"Pravokutnik širine %.1f i visine %.1f "
			+ "ima površinu %.1f i opseg %.1f.\n",
			width, height,
			calculateArea(),
			calculatePerimeter()
		);
	}
}
