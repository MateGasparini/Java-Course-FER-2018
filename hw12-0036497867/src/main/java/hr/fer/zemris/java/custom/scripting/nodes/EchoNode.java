package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Node representing a command which generates some textual output dynamically.
 * 
 * @author Mate Gasparini
 */
public class EchoNode extends Node {
	
	/**
	 * A read-only Element array property.
	 */
	private Element[] elements;
	
	/**
	 * Class constructor specifying the elements property.
	 * 
	 * @param elements The elements property.
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}
	
	/**
	 * Returns the elements property.
	 * 
	 * @return The elements property.
	 */
	public Element[] getElements() {
		return elements;
	}
	
	/**
	 * Returns something similar to the original tag.<br>
	 * E.g. <b>"{$= elements[0] element[1] element[2] (...) $}".</b>
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("{$= ");
		
		for (int i = 0, length = elements.length; i < length; i ++) {
			builder.append(elements[i].asText()).append(' ');
		}
		
		builder.append("$}");
		
		return builder.toString();
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
