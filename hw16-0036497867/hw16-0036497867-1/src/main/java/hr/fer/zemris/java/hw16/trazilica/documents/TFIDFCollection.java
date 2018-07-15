package hr.fer.zemris.java.hw16.trazilica.documents;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.hw16.trazilica.vocabulary.Vocabulary;
import hr.fer.zemris.java.hw16.trazilica.vocabulary.VocabularyProvider;

/**
 * Class containing a {@code Map} of document file paths mapped to their
 * corresponding {@link TFIDFVector}s.
 * 
 * @author Mate Gasparini
 */
public class TFIDFCollection {
	
	/** {@code Map} containing {@link TFIDFVector}s for document file paths. */
	private Map<Path, TFIDFVector> vectors = new HashMap<>();
	
	/**
	 * Loads {@link TFIDFVector}s for all documents from the given directory.
	 * 
	 * @param directory The given directory.
	 * @throws IOException If an I/O error occurs.
	 */
	public void loadVectors(String directory) throws IOException {
		Files.walkFileTree(Paths.get(directory), new DocumentVisitor());
	}
	
	/**
	 * Returns the {@code Map} of {@link TFIDFVector}s.
	 * 
	 * @return The {@code Map} of {@link TFIDFVector}s.
	 */
	public Map<Path, TFIDFVector> getVectors() {
		return vectors;
	}
	
	/**
	 * File visitor which constructs the {@link TFIDFVector} {@code Map}.
	 * 
	 * @author Mate Gasparini
	 */
	private class DocumentVisitor extends SimpleFileVisitor<Path> {
		
		/** Vocabulary used for picking out valid words. */
		private Vocabulary vocabulary = VocabularyProvider.getVocabulary();
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			TFIDFVector vector = new TFIDFVector();
			Map<String, Integer> tf = new HashMap<>();
			
			List<String> lines = Files.readAllLines(file);
			for (String line : lines) {
				String[] parts = line.split("[^\\p{IsAlphabetic}]");
				for (String word : parts) {
					String wordLower = word.toLowerCase();
					if (vocabulary.contains(wordLower)) {
						tf.put(wordLower, tf.getOrDefault(wordLower, 0) + 1);
					}
				}
			}
			
			vector.calculate(tf);
			vectors.put(file, vector);
			return FileVisitResult.CONTINUE;
		}
	}
}
