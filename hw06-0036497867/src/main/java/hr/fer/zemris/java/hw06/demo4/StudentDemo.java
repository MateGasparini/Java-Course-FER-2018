package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Program which loads a list of student records from a .txt file
 * and performs the homework-defined stream operations.
 * 
 * @author Mate Gasparini
 */
public class StudentDemo {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		List<String> lines;
		List<StudentRecord> records;
		
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"));
		} catch (IOException e) {
			System.out.println(
				"An error occured while trying to read the file."
			);
			return;
		}
		
		records = convert(lines);
		
		vratiBodovaViseOd25(records);
		vratiBrojOdlikasa(records);
		vratiListOdlikasa(records);
		vratiSortiranuListuOdlikasa(records);
		vratiPopisNepolozenih(records);
		razvrstajStudentePoOcjenama(records);
		vratiBrojStudenataPoOcjenama(records);
		razvrstajProlazPad(records);
	}
	
	/**
	 * Converts the given {@code List} of strings to a corresponding
	 * {@code List} of {@code StudentRecord} references.
	 * 
	 * @param lines The given list of strings.
	 * @return The corresponding list of student records.
	 */
	public static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		
		for (String line : lines) {
			String[] parts = line.split("\\t");
			
			try {
				StudentRecord record = StudentRecord.parse(parts);
				records.add(record);
			} catch (IllegalArgumentException ex) {
//				System.out.println("Skipping invalid record: '" + line + "'.");
			}
		}
		
		return records;
	}
	
	/**
	 * Returns the number of student records with more than 25 points.
	 * 
	 * @param records The given list of student records.
	 * @return The number of student records with more than 25 points.
	 */
	public static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				.map(r -> r.getMiScore() + r.getZiScore() + r.getLabScore())
				.filter(score -> score > 25)
				.count();
	}
	
	/**
	 * Returns the number of student records with the highest grade.
	 * 
	 * @param records The given list of student records.
	 * @return The number of student records with the highest grade.
	 */
	public static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getGrade() == 5)
				.count();
	}
	
	/**
	 * Returns a list of student records with the highest grade.
	 * 
	 * @param records The given list of student records.
	 * @return The generated list of student records.
	 */
	public static List<StudentRecord> vratiListOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getGrade() == 5)
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns a list of student records with the highest grade
	 * (sorted by points).
	 * 
	 * @param records The given list of student records.
	 * @return The generated list of student records.
	 */
	public static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		Comparator<StudentRecord> byScore =
			(r1, r2) -> ((Double) (r2.getMiScore() + r2.getZiScore() + r2.getLabScore()))
						.compareTo(r1.getMiScore() + r1.getZiScore() + r1.getLabScore());
		
		return records.stream()
				.filter(r -> r.getGrade() == 5)
				.sorted(byScore)
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns a list of student JMBAG numbers with the lowest grade.
	 * 
	 * @param records The given list of student records.
	 * @return The generated list of student JMBAG numbers.
	 */
	public static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getGrade() == 1)
				.map(StudentRecord::getJmbag)
				.sorted((jmbag1, jmbag2) -> jmbag1.compareTo(jmbag2))
				.collect(Collectors.toList());
	}
	
	/**
	 * Returns a map which maps the grades to corresponding lists
	 * of student records.
	 * 
	 * @param records The given list of student records.
	 * @return The generated map.
	 */
	public static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getGrade));
	}
	
	/**
	 * Returns a map which maps the grades to corresponding number
	 * of student records with the same grade.
	 * 
	 * @param records The given list of student records.
	 * @return The generated map.
	 */
	public static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.toMap(
					StudentRecord::getGrade, g -> 1, (g1, g2) -> g1 = g1 + 1));
	}
	
	/**
	 * Returns a map which maps true/false to student records
	 * who have passed/failed.
	 * 
	 * @param records The given list of student records.
	 * @return The generated map.
	 */
	public static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.partitioningBy(r -> r.getGrade() > 1));
	}
}
