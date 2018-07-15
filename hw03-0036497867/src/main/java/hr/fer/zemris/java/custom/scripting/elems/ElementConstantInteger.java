package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element containing a single read-only constant integer value.
 * 
 * @author Mate Gasparini
 */
public class ElementConstantInteger extends Element {
	
	/**
	 * A read-only value property.
	 */
	private int value;
	
	/**
	 * Class constructor specifying the value property.
	 * 
	 * @param value The value property.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Returns the value property.
	 * 
	 * @return The value property.
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns the textual representation of the constant.
	 * 
	 * @return The textual representation of the value property.
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
