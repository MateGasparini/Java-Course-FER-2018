package hr.fer.zemris.java.hw16.trazilica.vocabulary;

/**
 * <i>Singleton</i> which provides a {@link Vocabulary}.
 * 
 * @author Mate Gasparini
 */
public class VocabularyProvider {
	
	/** Instance of the vocabulary. */
	private static Vocabulary vocabulary = new Vocabulary();
	
	/**
	 * Private default constructor.
	 */
	private VocabularyProvider() {
	}
	
	/**
	 * Returns the {@link Vocabulary} instance.
	 * 
	 * @return The {@link Vocabulary} instance.
	 */
	public static Vocabulary getVocabulary() {
		return vocabulary;
	}
}
