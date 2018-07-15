package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class which is used to execute a {@link DocumentNode} tree
 * that was generated during the parsing of some <i>smart script</i>.
 * 
 * @author Mate Gasparini
 */
public class SmartScriptEngine {
	
	/** Specified parsed document tree. */
	private DocumentNode documentNode;
	
	/** Specified request context. */
	private RequestContext requestContext;
	
	/** Multistack used during the execution. */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/** Node visitor used for executing all parts of the document tree. */
	private INodeVisitor visitor = new INodeVisitor() {
		
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException ignorable) {}
		}
		
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String name = node.getVariable().getName();
			multistack.push(name, new ValueWrapper(getValue(node.getStartExpression())));
			Object end = getValue(node.getEndExpression());
			Object step = getValue(node.getStepExpression());
			
			while (true) {
				ValueWrapper current = multistack.peek(name);
				if (current.numCompare(end) > 0) {
					break;
				}
				visitChildren(node);
				current.add(step);
			}
			
			multistack.pop(name);
		}
		
		@Override
		public void visitEchoNode(EchoNode node) {
			ObjectStack temporaryStack = new ObjectStack();
			Element[] elements = node.getElements();
			for (Element element : elements) {
				if (element instanceof ElementConstantInteger) {
					temporaryStack.push(((ElementConstantInteger) element).getValue());
				} else if (element instanceof ElementConstantDouble) {
					temporaryStack.push(((ElementConstantDouble) element).getValue());
				} else if (element instanceof ElementString) {
					temporaryStack.push(((ElementString) element).getValue());
				} else if (element instanceof ElementVariable) {
					String name = ((ElementVariable) element).getName();
					temporaryStack.push(multistack.peek(name).getValue());
				} else if (element instanceof ElementOperator) {
					String operator = ((ElementOperator) element).getSymbol();
					Operations.calculate(operator, temporaryStack);
				} else if (element instanceof ElementFunction) {
					String function = ((ElementFunction) element).getName();
					Functions.calculate(function, temporaryStack, requestContext);
				}
			}
			
			try {
				writeReversedStack(temporaryStack);
			} catch (IOException ignorable) {}
		}
		
		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
		}
		
		/**
		 * Visits all children nodes of the given node.
		 * 
		 * @param node The given node.
		 */
		private void visitChildren(Node node) {
			int numberOfChildren = node.numberOfChildren();
			for (int i = 0; i < numberOfChildren; i ++) {
				node.getChild(i).accept(this);
			}
		}
		
		/**
		 * Returns the value of the given element.
		 * 
		 * @param element The given element.
		 * @return The value stored in the given element.
		 * @throws IllegalArgumentException If the given element does not have a value.
		 */
		private Object getValue(Element element) {
			if (element instanceof ElementConstantInteger) {
				return ((ElementConstantInteger) element).getValue();
			} else if (element instanceof ElementConstantDouble) {
				return ((ElementConstantDouble) element).getValue();
			} else if (element instanceof ElementString) {
				return ((ElementString) element).getValue();
			}
			throw new IllegalArgumentException("Given element does not have a value.");
		}
		
		/**
		 * Recursive method used for writing the contents of the given stack
		 * in reverse-stack order (FIFO).
		 * 
		 * @param stack The given stack.
		 * @throws IOException If an I/O error occurs.
		 */
		private void writeReversedStack(ObjectStack stack) throws IOException {
			if (stack.isEmpty()) return;
			Object popped = stack.pop();
			writeReversedStack(stack);
			requestContext.write(String.valueOf(popped));
		}
	};
	
	/**
	 * Constructor specifying the parsed document tree and the request context.
	 * 
	 * @param documentNode The specified parsed document tree.
	 * @param requestContext The specified request context.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Executes the specified <i>smart script</i>.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
