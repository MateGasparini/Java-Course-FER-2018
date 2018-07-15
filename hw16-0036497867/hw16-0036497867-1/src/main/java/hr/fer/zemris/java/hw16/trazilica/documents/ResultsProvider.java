package hr.fer.zemris.java.hw16.trazilica.documents;

import java.util.List;

/**
 * Provides a {@code List} of {@link Result}s
 * and methods for setting and retrieving.
 * 
 * @author Mate Gasparini
 */
public class ResultsProvider {
	
	/** Reference the {@code List} of {@link Result}s. */
	private static List<Result> results;
	
	/**
	 * Private default constructor.
	 */
	private ResultsProvider() {
	}
	
	/**
	 * Returns the {@code List} of {@link Result}s.
	 * 
	 * @return The {@code List} of {@link Result}s.
	 */
	public static List<Result> getResults() {
		return results;
	}
	
	/**
	 * Sets the results to the given {@code List} of {@link Result}s.
	 * 
	 * @param results The given {@code List} of {@link Result}s.
	 */
	public static void setResults(List<Result> results) {
		ResultsProvider.results = results;
	}
}
