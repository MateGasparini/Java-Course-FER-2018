package hr.fer.zemris.java.hw16.trazilica.documents;

import java.nio.file.Path;
import java.text.DecimalFormat;

/**
 * Class which represents a 'query' command result.<br>
 * It contains two read-only attributes: file path of some document
 * and its similarity value to the query.
 * 
 * @author Mate Gasparini
 */
public class Result {
	
	/** Similarity value of the document and the query vector. */
	private double similarity;
	
	/** Path of the document. */
	private Path filePath;
	
	/**
	 * Constructor specifying the similarity value and the document file path.
	 * 
	 * @param similarity The specified similarity value.
	 * @param filePath The specified document file path.
	 */
	public Result(double similarity, Path filePath) {
		this.similarity = similarity;
		this.filePath = filePath.toAbsolutePath();
	}
	
	/**
	 * Returns the similarity value between the document and the query vector.
	 * 
	 * @return The similarity value.
	 */
	public double getSimilarity() {
		return similarity;
	}
	
	/**
	 * Returns the file path of the document.
	 * 
	 * @return The file path of the document.
	 */
	public Path getFilePath() {
		return filePath;
	}
	
	@Override
	public String toString() {
		DecimalFormat formatter = new DecimalFormat("#.####");
		return "(" + formatter.format(similarity) + ") " + filePath.toString();
	}
}
