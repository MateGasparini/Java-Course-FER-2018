package hr.fer.zemris.java.custom.scripting.elems;

/**
* Element containing a single read-only symbol String reference.
* 
* @author Mate Gasparini
*/
public class ElementOperator extends Element {
	
	/**
	 * A read-only symbol property.
	 */
	private String symbol;
	
	/**
	 * Class constructor specifying the symbol property.
	 * 
	 * @param symbol The symbol property.
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Returns the symbol property.
	 * 
	 * @return The symbol property.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * Returns the textual representation of the operator.
	 * 
	 * @return The textual representation of the symbol property.
	 */
	@Override
	public String asText() {
		return symbol;
	}
}
