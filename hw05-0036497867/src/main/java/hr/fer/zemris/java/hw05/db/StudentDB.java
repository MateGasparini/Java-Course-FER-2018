package hr.fer.zemris.java.hw05.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

/**
 * A program which provides interaction with a student record database.<br>
 * Firstly, the database is loaded from a .txt file.<br>
 * After that, the user is prompted for input. The user can enter
 * the query command followed by the arguments defined in the homework task
 * and the resulting table will be generated and printed out to the stdin.<br>
 * The user can also enter the exit command and the program execution will end.
 * 
 * @author Mate Gasparini
 */
public class StudentDB {
	
	/**
	 * The query command keyword.
	 */
	private static final String QUERY = "query";
	/**
	 * The exit command keyword.
	 */
	private static final String EXIT = "exit";
	
	/**
	 * String which represents a prompt for some user input.
	 */
	private static final String INPUT_PROMPT = "> ";
	/**
	 * The equals sign used for table borders.
	 */
	private static final String EQUALS_SIGN = "=";
	/**
	 * The plus sign used for table borders.
	 */
	private static final String PLUS_SIGN = "+";
	/**
	 * The vertical sign used for table borders.
	 */
	private static final String VERTICAL_SIGN = "|";
	/**
	 * The space sign used for table content alignment.
	 */
	private static final String SPACE_SIGN = " ";
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		List<String> fileLines;
		try {
			fileLines = Files.readAllLines(
				Paths.get("./database.txt"), StandardCharsets.UTF_8
			);
		} catch (IOException ex) {
			System.out.println(
				"A problem occured while trying to read the file."
			);
			return;
		}
		
		StudentDatabase database = new StudentDatabase(fileLines);
		
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(System.in))) {
			while (true) {
				System.out.print(INPUT_PROMPT);
				
				String[] parts = reader.readLine().split("\\s+", 2);
				
				if (parts.length == 1) {
					if (parts[0].equals(EXIT)) {
						break;
					} else if (parts[0].equals(QUERY)) {
						System.out.println("query: Expected arguments.");
					} else if (!parts[0].isEmpty()){
						printInvalidCommand(parts[0]);
					}
					
					continue;
				} else if (parts[0].isEmpty()) {
					continue; // If one or more whitespaces were entered.
				}
				
				String command = parts[0];
				String arguments = parts[1];
				
				if (command.equals(EXIT)) {
					if (arguments.isEmpty()) {
						break;
					} else {
						System.out.println(
							"exit: Invalid operation '" + arguments + "'."
						);
						System.out.println(
							"If you wish to exit, type 'exit' without arguments."
						);
					}
				} else if (command.equals(QUERY)) {
					QueryParser parser;
					try {
						parser = new QueryParser(arguments);
					} catch (QueryParserException ex) {
						System.out.println(ex.getMessage());
						continue;
					}
					
					if (parser.isDirectQuery()) {
						StudentRecord record = database.forJMBAG(
							parser.getQueriedJMBAG()
						);
						
						printSingleResult(record);
					} else {
						printTable(
							database.filter(new QueryFilter(parser.getQuery()))
						);
					}
				} else {
					printInvalidCommand(command);
				}
			}
		} catch (IOException | NullPointerException ex) {
			/* Should not normally happen. NullPointerException here "could"
			 * happen if the end of stream is reached (readLine method). */
			System.out.println("A problem occured while reading from stdin.");
		}
		
		System.out.println("Goodbye!");
	}
	
	/**
	 * Prints a result table for the given record.
	 * If the record is null, no table is printed.
	 * 
	 * @param record The given record.
	 */
	private static void printSingleResult(StudentRecord record) {
		System.out.println("Using index for record retrieval.");
		
		if (record == null) {
			printNumberOfResults(0);
			return;
		}
		
		List<StudentRecord> records = new LinkedList<>();
		records.add(record);
		printTable(records);
		printNumberOfResults(1);
	}
	
	/**
	 * Prints a result table for the given list of records.
	 * If the given list is null or empty, no table is printed.
	 * 
	 * @param records The given list of records.
	 */
	private static void printTable(List<StudentRecord> records) {
		if (records == null || records.isEmpty()) {
			printNumberOfResults(0);
			return;
		}
		
		MaxLengths.reset();
		for (StudentRecord record : records) {
			MaxLengths.update(record);
		}
		
		StringBuilder builder = new StringBuilder();
		
		String header = generateHeader();
		builder.append(header);
		
		for (StudentRecord record : records) {
			String recordLine = generateRecordLine(record);
			builder.append(recordLine);
		}
		
		builder.append(header); // footer, actually
		
		System.out.println(builder.toString());
	}
	
	/**
	 * Prints the number of selected records to stdin.
	 * 
	 * @param number The number of selected records.
	 */
	private static void printNumberOfResults(int number) {
		System.out.println("Records selected: " + number + "\n");
	}
	
	/**
	 * Print the invalid command message to stdin.
	 * 
	 * @param command The name of the invalid command.
	 */
	private static void printInvalidCommand(String command) {
		System.out.println("Invalid command: '" + command + "'.");
	}
	
	/**
	 * Generates and returns a string representing the result table's
	 * header/footer. It is generated dynamically based on the
	 * {@code MaxLengths} values.
	 * 
	 * @return A string representation of the table's header/footer.
	 */
	private static String generateHeader() {
		StringBuilder builder = new StringBuilder(PLUS_SIGN);
		
		appendMultiple(builder, EQUALS_SIGN, MaxLengths.jmbag + 2);
		builder.append(PLUS_SIGN);
		
		appendMultiple(builder, EQUALS_SIGN, MaxLengths.firstName + 2);
		builder.append(PLUS_SIGN);
		
		appendMultiple(builder, EQUALS_SIGN, MaxLengths.lastName + 2);
		builder.append(PLUS_SIGN);
		
		appendMultiple(builder, EQUALS_SIGN, MaxLengths.finalGrade + 2);
		builder.append(PLUS_SIGN).append("\n");
		
		return builder.toString();
	}
	
	/**
	 * Generates and returns a string representing a row of the result table.
	 * It is generated dynamically based on the {@code MaxLengths} values.
	 * 
	 * @return A string representation of a result table's row.
	 */
	private static String generateRecordLine(StudentRecord record) {
		StringBuilder builder = new StringBuilder(VERTICAL_SIGN);
		
		builder.append(SPACE_SIGN);
		builder.append(record.getJmbag());
		int numberOfSpaces = MaxLengths.jmbag - record.getJmbag().length();
		appendMultiple(builder, SPACE_SIGN, numberOfSpaces);
		
		builder.append(SPACE_SIGN).append(VERTICAL_SIGN).append(SPACE_SIGN);
		builder.append(record.getLastName());
		numberOfSpaces = MaxLengths.lastName - record.getLastName().length();
		appendMultiple(builder, SPACE_SIGN, numberOfSpaces);
		
		builder.append(SPACE_SIGN).append(VERTICAL_SIGN).append(SPACE_SIGN);
		builder.append(record.getFirstName());
		numberOfSpaces = MaxLengths.firstName - record.getFirstName().length();
		appendMultiple(builder, SPACE_SIGN, numberOfSpaces);
		
		builder.append(SPACE_SIGN).append(VERTICAL_SIGN).append(SPACE_SIGN);
		builder.append(record.getFinalGrade());
		numberOfSpaces = MaxLengths.finalGrade - record.getFinalGrade().length();
		appendMultiple(builder, SPACE_SIGN, numberOfSpaces);
		
		builder.append(SPACE_SIGN).append(VERTICAL_SIGN).append("\n");
		
		return builder.toString();
	}
	
	/**
	 * Appends the given string to the given builder {@code quantity} times.
	 * 
	 * @param builder The given builder.
	 * @param s The given string.
	 * @param quantity The number of times the given string needs to be
	 * 			appended to the given builder.
	 */
	private static void appendMultiple(StringBuilder builder, String s, int quantity) {
		for (int i = 0; i < quantity; i ++) {
			builder.append(s);
		}
	}
	
	/**
	 * A helper class providing functionality for calculating
	 * the result table's maximum lengths of all table attributes.
	 * This is useful for dynamic result table generation.
	 * 
	 * @author Mate Gasparini
	 */
	private static class MaxLengths {
		
		/* Some of these values should be constant
		 * if we assume the database is correctly stored.
		 * But in case it is not, this could help. */
		/**
		 * Maximum JMBAG length.
		 */
		private static int jmbag; // Should normally be 10.
		/**
		 * Maximum first name length.
		 */
		private static int firstName;
		/**
		 * Maximum last name length.
		 */
		private static int lastName;
		/**
		 * Maximum final grade length.
		 */
		private static int finalGrade; // Should normally be 1.
		
		/**
		 * Resets all maximum lengths to 0.
		 */
		private static void reset() {
			jmbag = 0;
			firstName = 0;
			lastName = 0;
			finalGrade = 0;
		}
		
		/**
		 * Updates all maximum lengths if the given record
		 * contains a bigger length attribute value.
		 * 
		 * @param record The given record.
		 */
		private static void update(StudentRecord record) {
			jmbag = updateMaxLength(jmbag, record.getJmbag().length());
			firstName = updateMaxLength(firstName, record.getFirstName().length());
			lastName = updateMaxLength(lastName, record.getLastName().length());
			finalGrade = updateMaxLength(finalGrade, record.getFinalGrade().length());
		}
		
		/**
		 * Returns the maximum value between the two given values.
		 * 
		 * @param valueMax The first given value.
		 * @param newValue The second given value.
		 * @return The maximum of the two given values.
		 */
		private static int updateMaxLength(int valueMax, int newValue) {
			if (newValue > valueMax) {
				valueMax = newValue;
			}
			
			return valueMax;
		}
	}
}
