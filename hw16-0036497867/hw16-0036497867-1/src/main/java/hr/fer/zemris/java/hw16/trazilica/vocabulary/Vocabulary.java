package hr.fer.zemris.java.hw16.trazilica.vocabulary;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class containing a {@code Map} of words mapped to the number of unique
 * document in which they can be found, as well a {@code List} of Croatian stop words.
 * 
 * @author Mate Gasparini
 */
public class Vocabulary {
	
	/** Path to the stop words definition file. */
	private static final String STOP_WORDS_PATH = "src/main/resources/hrvatski_stoprijeci.txt";
	
	/** {@code Map} of words mapped to the number of unique documents. */
	private Map<String, Integer> words = new HashMap<>();
	
	/** {@code List} of stop words. */
	private List<String> stopWords;
	
	/** Number of documents from which the vocabulary is constructed. */
	private int documentCount;
	
	/**
	 * Loads the stop words, as well as the vocabulary words.
	 * 
	 * @param directory The documents root directory.
	 * @throws IOException If an I/O error occurs.
	 */
	public void loadWords(String directory) throws IOException {
		stopWords = Files.readAllLines(Paths.get(STOP_WORDS_PATH));
		Files.walkFileTree(Paths.get(directory), new VocabularyVisitor());
	}
	
	/**
	 * Returns {@code true} if the vocabulary contains the given word.
	 * 
	 * @param word The given word.
	 * @return {@code true} if the vocabulary contains the given word,
	 * 			or {@code false} otherwise.
	 */
	public boolean contains(String word) {
		return words.containsKey(word);
	}
	
	/**
	 * Returns the number of words in the vocabulary.
	 * 
	 * @return The vocabulary size.
	 */
	public int getSize() {
		return words.size();
	}
	
	/**
	 * Returns the number of documents from which the vocabulary was constructed.
	 * 
	 * @return The document count.
	 */
	public int getDocumentCount() {
		return documentCount;
	}
	
	/**
	 * Returns the vocabulary {@code Map} containing words and the number of unique
	 * documents in which they could be found.
	 * 
	 * @return The vocabulary {@code Map}.
	 */
	public Map<String, Integer> getWords() {
		return words;
	}
	
	/**
	 * Returns the {@code List} of stop words.
	 * 
	 * @return The {@code List} of stop words.
	 */
	public List<String> getStopWords() {
		return stopWords;
	}
	
	/**
	 * File visitor which fills the vocabulary {@code Map} with words
	 * (and their document count).
	 * 
	 * @author Mate Gasparini
	 */
	private class VocabularyVisitor extends SimpleFileVisitor<Path> {
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
				throws IOException {
			Set<String> documentWords = new HashSet<>();
			
			List<String> lines = Files.readAllLines(file);
			for (String line : lines) {
				String[] parts = line.split("[^\\p{IsAlphabetic}]");
				for (String word : parts) {
					String wordLower = word.toLowerCase();
					if (!word.isEmpty() && !stopWords.contains(wordLower)) {
						documentWords.add(wordLower);
					}
				}
			}
			
			documentWords.forEach(word -> words.put(word, words.getOrDefault(word, 0) + 1));
			documentCount ++;
			return FileVisitResult.CONTINUE;
		}
	}
}
