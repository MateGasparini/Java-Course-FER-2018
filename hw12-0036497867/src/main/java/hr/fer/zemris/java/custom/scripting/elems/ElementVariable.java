package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element containing a single read-only variable name.
 * 
 * @author Mate Gasparini
 */
public class ElementVariable extends Element {
	
	/**
	 * A read-only name property.
	 */
	private String name;
	
	/**
	 * Class constructor specifying the name property.
	 * 
	 * @param name The name property.
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the name property.
	 * 
	 * @return The name property.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the textual representation of the variable.
	 * 
	 * @return The textual representation of the name property.
	 */
	@Override
	public String asText() {
		return name;
	}
}
