package hr.fer.zemris.java.hw13.servlets.glasanje;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class used for loading band definitions and loading/storing
 * band voting results from/to disk.
 * 
 * @author Mate Gasparini
 */
public class GlasanjeUtil {
	
	/**
	 * Private default constructor.
	 */
	private GlasanjeUtil() {
	}
	
	/**
	 * Loads the band definitions from the file specified by the given file path.
	 * 
	 * @param filePath The given file path.
	 * @return The loaded {@code List} of bands.
	 * @throws IOException If an I/O error occurs.
	 * @throws IllegalArgumentException If the specified definitions file is not valid.
	 */
	public static List<Band> loadBands(Path filePath)
			throws IOException, IllegalArgumentException {
		List<String> lines = Files.readAllLines(filePath);
		List<Band> bands = new ArrayList<>(lines.size());
		for (String line : lines) {
			String[] lineParts = line.split("\\t");
			if (lineParts.length != 3) {
				throw new IllegalArgumentException("Expected 3 tokens per line.");
			}
			Band band = new Band(lineParts[0], lineParts[1], lineParts[2]);
			if (bands.contains(band)) {
				throw new IllegalArgumentException("Multiple bands with the same ID present.");
			}
			bands.add(band);
		}
		return bands;
	}
	
	/**
	 * Loads band definitions, as well as the band voting results, joins them
	 * in one {@code List} of bands, sorts it by score and returns its reference.
	 * 
	 * @param definitionFileName Band definitions file name.
	 * @param resultsFileName Band voting results file name.
	 * @return Sorted {@code List} of bands (sorted by score and name).
	 * @throws IOException If an I/O error occurs.
	 * @throws IllegalArgumentException If the specified definitions file is not valid.
	 */
	public static List<Band> loadSortedResults(String definitionFileName,
			String resultsFileName) throws IOException, IllegalArgumentException {
		Map<String, Integer> results = readResults(Paths.get(resultsFileName));
		if (results == null || results.isEmpty()) {
			return null;
		}
		
		List<Band> bands = loadBands(Paths.get(definitionFileName));
		for (Band band : bands) {
			String id = band.getId();
			int score = results.containsKey(id) ? results.get(id) : 0;
			band.setScore(score);
		}
		Collections.sort(bands);
		
		return bands;
	}
	
	/**
	 * Loads the voting results from the given file path and returns a
	 * {@code Map} filled with band IDs as keys and band scores as values.<br>
	 * If the specified file does not exist, is not regular file or is not readable,
	 * {@code null} is returned instead.<br>
	 * Invalid and non-parseable file lines are simply skipped.
	 * 
	 * @param filePath The given file path.
	 * @return {@code Map} with IDs mapped to their corresponding voting scores.
	 * @throws IOException If an I/O error occurs.
	 */
	public static Map<String, Integer> readResults(Path filePath) throws IOException {
		if (!Files.isRegularFile(filePath) || !Files.isReadable(filePath)) {
			return null;
		}
		
		Map<String, Integer> results = new HashMap<>();
		List<String> lines = Files.readAllLines(filePath);
		for (String line : lines) {
			String[] lineParts = line.split("\\t");
			if (lineParts.length != 2) continue;
			String id = lineParts[0];
			try {
				int score = Integer.parseInt(lineParts[1]);
				results.put(id, score);
			} catch (NumberFormatException ignorable) {}
		}
		
		return results;
	}
	
	/**
	 * Writes the updated {@code Map} of band voting results to the given file path.<br>
	 * Content is written as ID and score separated by TAB (each ID is in new line).
	 * 
	 * @param filePath The given file path.
	 * @param results The given {@code Map} of band voting results.
	 * @throws IOException If an I/O errror occurs.
	 */
	public static synchronized void writeResults(Path filePath, Map<String, Integer> results)
			throws IOException {
		try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
			for (Map.Entry<String, Integer> result : results.entrySet()) {
				bw.write(result.getKey()+"\t"+result.getValue()+"\n");
			}
		}
	}
	
	/**
	 * Creates the band voting results file and writes one result to it (score of 1
	 * for the given band ID).
	 * 
	 * @param filePath The specified results file path.
	 * @param id The given band ID.
	 * @throws IOException If an I/O error occurs.
	 */
	public static synchronized void createResults(Path filePath, String id)
			throws IOException {
		Files.createFile(filePath);
		try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
			bw.write(id+"\t1\n");
		}
	}
}
