package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing a piece of textual data.
 * 
 * @author Mate Gasparini
 */
public class TextNode extends Node {
	
	/**
	 * A read-only String property.
	 */
	private String text;
	
	/**
	 * Class constructor specifying the text property.
	 * 
	 * @param text The text property.
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Returns the text property.
	 * 
	 * @return The text property.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Returns the original text (escaped sequences included).
	 */
	@Override
	public String toString() {
		return text
				.replaceAll("(\\\\)", "\\\\$1")
				.replaceAll("([\\{][\\$])", "\\\\$1");
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
