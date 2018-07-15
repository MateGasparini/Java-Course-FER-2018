package hr.fer.zemris.java.hw16.trazilica.documents;

/**
 * <i>Singleton</i> which contains a {@link TFIDFCollection}.
 * 
 * @author Mate Gasparini
 */
public class TFIDFCollectionProvider {
	
	/** Single instance of the collection. */
	private static TFIDFCollection collection;
	
	/**
	 * Private default constructor.
	 */
	private TFIDFCollectionProvider() {
	}
	
	/**
	 * Returns the collection instance (and initializes it if {@code null}).
	 * 
	 * @return The {@link TFIDFCollection} instance.
	 */
	public static TFIDFCollection getCollection() {
		if (collection == null) {
			collection = new TFIDFCollection();
		}
		return collection;
	}
}
