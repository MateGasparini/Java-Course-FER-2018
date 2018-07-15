package hr.fer.zemris.java.hw16.trazilica.documents;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing a <i>term frequency-inverse document frequency</i> vector
 * mapped by words.
 * 
 * @author Mate Gasparini
 */
public class TFIDFVector {
	
	/** {@code Map} containing words mapped to TFIDF values. */
	private Map<String, Double> values;
	
	/**
	 * Calculates the TFIDF values by using the given TF {@code Map}
	 * and the {@link IDFVector} stored in memory.
	 * 
	 * @param tf The given TF {@code Map}.
	 */
	public void calculate(Map<String, Integer> tf) {
		Map<String, Double> idf = IDFVector.getValues();
		values = new HashMap<>();
		for (Map.Entry<String, Integer> entry : tf.entrySet()) {
			String word = entry.getKey();
			Integer tfValue = entry.getValue();
			Double idfValue = idf.get(word);
			values.put(word, tfValue * idfValue);
		}
	}
	
	/**
	 * Returns the vector norm.
	 * 
	 * @return The norm of the vector.
	 */
	public double norm() {
		double norm = 0.0;
		for (Map.Entry<String, Double> entry : values.entrySet()) {
			double value = entry.getValue();
			norm += value * value;
		}
		norm = Math.sqrt(norm);
		return norm;
	}
	
	/**
	 * Returns the cosine of the angle between this and the given {@link TFIDFVector}.
	 * 
	 * @param other The given vector.
	 * @return The cosine of the angle between the two vectors.
	 */
	public double cosAngle(TFIDFVector other) {
		double numerator = 0.0;
		for (Map.Entry<String, Double> entry : values.entrySet()) {
			String word = entry.getKey();
			double value1 = entry.getValue();
			Double value2 = other.values.get(word);
			if (value2 == null) continue;
			numerator += value1 * value2;
		}
		double denominator = this.norm() * other.norm();
		return numerator / denominator;
	}
}
