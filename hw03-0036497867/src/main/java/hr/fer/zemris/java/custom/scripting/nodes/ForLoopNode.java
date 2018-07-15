package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Node representing a single for-loop construct.
 * 
 * @author Mate Gasparini
 */
public class ForLoopNode extends Node {
	
	/**
	 * A read-only ElementVariable property.
	 */
	private ElementVariable variable;
	/**
	 * A read-only Element property.
	 */
	private Element startExpression;
	/**
	 * A read-only Element property.
	 */
	private Element endExpression;
	/**
	 * A read-only Element property.
	 */
	private Element stepExpression;
	
	/**
	 * Class constructor specifying the needed properties.
	 * 
	 * @param variable The variable property.
	 * @param startExpression The startExpression property.
	 * @param endExpression The endExpression property.
	 * @param stepExpression The stepExpression property.
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression,
						Element endExpression, Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Returns the variable property.
	 * 
	 * @return The variable property.
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Returns the startExpression property.
	 * 
	 * @return The startExpression property.
	 */
	public Element getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Returns the endExpression property.
	 * 
	 * @return The endExpression property.
	 */
	public Element getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Returns the stepExpression property.
	 * 
	 * @return The stepExpression property.
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	/**
	 * Returns something similar to the original tag.<br>
	 * E.g. <b>"{$ FOR variable start end (step) $}".</b>
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("{$ FOR ")
				.append(variable.asText()).append(' ')
				.append(startExpression.asText()).append(' ')
				.append(endExpression.asText()).append(' ');
		
		if (stepExpression != null) {
			builder.append(stepExpression.asText()).append(' ');
		}
		
		builder.append("$}");
		
		return builder.toString();
	}
}
