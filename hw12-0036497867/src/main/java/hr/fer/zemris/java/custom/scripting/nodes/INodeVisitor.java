package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * <i>Visitor</i> interface used to traverse different {@link Node} objects.
 * 
 * @author Mate Gasparini
 */
public interface INodeVisitor {
	
	/**
	 * Visits the given text node.
	 * 
	 * @param node The given text node.
	 */
	public void visitTextNode(TextNode node);
	
	/**
	 * Visits the given for loop node.
	 * 
	 * @param node The given for loop node.
	 */
	public void visitForLoopNode(ForLoopNode node);
	
	/**
	 * Visits the given echo node.
	 * 
	 * @param node The given eho node.
	 */
	public void visitEchoNode(EchoNode node);
	
	/**
	 * Visits the given document node.
	 * 
	 * @param node The given document node.
	 */
	public void visitDocumentNode(DocumentNode node);
}
