package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Node representing an entire document.
 * 
 * @author Mate Gasparini
 */
public class DocumentNode extends Node {
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
