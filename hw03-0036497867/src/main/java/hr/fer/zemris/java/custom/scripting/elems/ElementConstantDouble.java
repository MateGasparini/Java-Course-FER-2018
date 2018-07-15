package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element containing a single read-only constant double value.
 * 
 * @author Mate Gasparini
 */
public class ElementConstantDouble extends Element {
	
	/**
	 * A read-only value property.
	 */
	private double value;
	
	/**
	 * Class constructor specifying the value property.
	 * 
	 * @param value The value property.
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Returns the value property.
	 * 
	 * @return The value property.
	 */
	public double getValue() {
		return value;
	}
	
	/**
	 * Returns the textual representation of the constants.
	 * 
	 * @return The textual representation of the value property.
	 */
	public String asText() {
		return String.valueOf(value);
	}
}
