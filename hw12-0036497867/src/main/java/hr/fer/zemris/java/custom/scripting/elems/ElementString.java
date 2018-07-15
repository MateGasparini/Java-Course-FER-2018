package hr.fer.zemris.java.custom.scripting.elems;

/**
* Element containing a single read-only String reference.
* 
* @author Mate Gasparini
*/
public class ElementString extends Element {
	
	/**
	 * A read-only value property.
	 */
	private String value;
	
	/**
	 * Class constructor specifying the value property.
	 * 
	 * @param value The value property.
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Returns the value property.
	 * 
	 * @return The value property.
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Returns the original String (escape sequences included).
	 * 
	 * @return The textual representation of the String.
	 */
	@Override
	public String asText() {
		return "\"" 
				+ value
				.replaceAll("(\\\\)", "\\\\$1")
				.replaceAll("(\\\")", "\\\\$1")
				+ "\"";
	}
}
