package hr.fer.zemris.java.hw16.trazilica.documents;

import static java.lang.Math.log10;

import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.hw16.trazilica.vocabulary.Vocabulary;
import hr.fer.zemris.java.hw16.trazilica.vocabulary.VocabularyProvider;

/**
 * <i>Singleton</i> containing a {@code Map} which represents a
 * vector of IDF (<i>inverse document frequency</i>) values for words
 * from the {@link Vocabulary}.
 * 
 * @author Mate Gasparini
 */
public class IDFVector {
	
	/** Single instance of the IDF {@code Map}. */
	private static Map<String, Double> values;
	
	/**
	 * Private default constructor.
	 */
	private IDFVector() {
	}
	
	/**
	 * Returns the instance of the IDF {@code Map}.<br>
	 * If it has not yet been created, it is initialized
	 * using the {@link Vocabulary} stored in memory.
	 * 
	 * @return The IDF {@code Map}.
	 */
	public static Map<String, Double> getValues() {
		if (values == null) {
			initIDF();
		}
		return values;
	}
	
	/**
	 * Initializes the IDF {@code Map} with the {@link Vocabulary} stored in memory.
	 */
	private static void initIDF() {
		values = new HashMap<>();
		Vocabulary vocabulary = VocabularyProvider.getVocabulary();
		int numberOfDocuments = vocabulary.getDocumentCount();
		vocabulary.getWords().forEach((word, documentsPerWord) -> values.put(
			word, log10((double) numberOfDocuments / documentsPerWord))
		);
	}
}
