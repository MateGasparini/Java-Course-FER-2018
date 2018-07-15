package hr.fer.zemris.java.hw16.trazilica.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.java.hw16.trazilica.documents.IDFVector;
import hr.fer.zemris.java.hw16.trazilica.documents.Result;
import hr.fer.zemris.java.hw16.trazilica.documents.ResultsProvider;
import hr.fer.zemris.java.hw16.trazilica.documents.TFIDFCollectionProvider;
import hr.fer.zemris.java.hw16.trazilica.documents.TFIDFVector;
import hr.fer.zemris.java.hw16.trazilica.vocabulary.Vocabulary;
import hr.fer.zemris.java.hw16.trazilica.vocabulary.VocabularyProvider;

/**
 * {@link ICommand} which executes the 'query' command by
 * making a {@code List} of (10 or less) {@link Result}s and writes
 * it to standard output.
 * 
 * @author Mate Gasparini
 */
public class QueryCommand implements ICommand {
	
	/** Maximum size of the {@code List} of results. */
	private static final int MAX_RESULTS = 10;
	
	@Override
	public void processCommand(String[] commandParts) {
		if (commandParts.length < 2) {
			System.out.println("Expected arguments after 'query' command.");
			return;
		}
		
		List<String> words = removeStopWords(commandParts);
		System.out.println("Query is: " + queryToString(words));
		if (words.isEmpty()) {
			return;
		}
		
		System.out.println("Najboljih 10 rezultata:");
		List<Result> results = getResults(words);
		for (int i = 0, size = results.size(); i < size; i ++) {
			System.out.format("[%2d] %s\n", i, results.get(i).toString());
		}
		ResultsProvider.setResults(results);
	}
	
	/**
	 * Returns a {@code List} of {@link Result}s by using the given
	 * {@code List} of query words as a document and comparing it to
	 * already existing document vectors in memory.
	 * 
	 * @param words The given {@code List} of query words.
	 * @return The calculated {@code List} of {@link Result}s.
	 */
	private List<Result> getResults(List<String> words) {
		List<Result> results = new ArrayList<>();
		
		TFIDFVector queryVector = getQueryVector(words);
		Map<Path, TFIDFVector> vectors = TFIDFCollectionProvider.getCollection().getVectors();
		for (Map.Entry<Path, TFIDFVector> entry : vectors.entrySet()) {
			TFIDFVector documentVector = entry.getValue();
			double similarity = queryVector.cosAngle(documentVector);
			if (similarity > 0.0) {
				results.add(new Result(similarity, entry.getKey()));
			}
		}
		Collections.sort(results, Comparator.comparingDouble(Result::getSimilarity).reversed());
		int resultSize = results.size();
		if (resultSize > MAX_RESULTS) {
			results.subList(MAX_RESULTS, resultSize).clear();
		}
		
		return results;
	}
	
	/**
	 * Constructs the {@link TFIDFVector} from the given {@code List} of words
	 * and the {@link IDFVector} in memory, and returns it.
	 * 
	 * @param words The given {@code List} of words.
	 * @return The calculated {@link TFIDFVector}.
	 */
	private TFIDFVector getQueryVector(List<String> words) {
		Map<String, Integer> tf = new HashMap<>();
		for (String word : words) {
			tf.put(word, tf.getOrDefault(word, 0) + 1);
		}
		TFIDFVector vector = new TFIDFVector();
		vector.calculate(tf);
		return vector;
	}
	
	/**
	 * Removes all non-vocabulary words from the given array of words
	 * and returns it as a {@code List} of all remaining (lower case) words.
	 * 
	 * @param parts The given array of query words.
	 * @return {@code List} containing all words from the given array that were also found
	 * 			in the {@link Vocabulary}.
	 */
	private List<String> removeStopWords(String[] parts) {
		List<String> words = new ArrayList<>();
		Set<String> vocabularyWords = VocabularyProvider.getVocabulary().getWords().keySet();
		for (int i = 1; i < parts.length; i ++) {
			String word = parts[i].toLowerCase();
			String wordLower = word.toLowerCase();
			if (vocabularyWords.contains(wordLower)) {
				words.add(wordLower);
			}
		}
		return words;
	}
	
	/**
	 * Transforms the given {@code List} of query words and returns it
	 * as a String (as specified in the homework).
	 * 
	 * @param words The given {@code List} of query words.
	 * @return String containing all words separated by commas and in brackets.
	 */
	private String queryToString(List<String> words) {
		if (words.isEmpty()) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder("[");
		for (String word : words) {
			sb.append(word).append(", ");
		}
		sb.setLength(sb.length() - 2);
		sb.append("]");
		return sb.toString();
	}
}
