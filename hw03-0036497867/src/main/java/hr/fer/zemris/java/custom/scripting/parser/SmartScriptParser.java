package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Class that represents a parser which, parsing the lexer's tokens,
 * constructs a tree-like structure for the given input data.
 * 
 * @author Mate Gasparini
 */
public class SmartScriptParser {
	
	/**
	 * Lexer which generates tokens for this parser.
	 */
	private Lexer lexer;
	/**
	 * Node which, after parsing the data, should represent the input
	 * document.
	 */
	private DocumentNode documentNode;
	/**
	 * A stack used for easier DocumentNode tree construction.
	 */
	private ObjectStack stack;
	
	/**
	 * Constructor specifying the input data which needs to be parsed.
	 * 
	 * @param data String which needs to be parsed.
	 * @throws SmartScriptParserException If a problem occurred during parsing.
	 */
	public SmartScriptParser(String data) {
		lexer = new Lexer(data);
		
		try {
			parseData();
		} catch (LexerException ex) {
			throw new SmartScriptParserException(
				"Input data is not valid.", ex
			);
		}
	}
	
	/**
	 * If the data was parsed without problems, this method returns
	 * the resulting <code>DocumentNode</code> (from which a tree
	 * can be generated). Otherwise, it returns null.
	 * 
	 * @return The resulting document.
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
	
	/**
	 * Attempts to parse the data using the tokens returned by the lexer.
	 */
	private void parseData() {
		documentNode = new DocumentNode();
		stack = new ObjectStack();
		stack.push(documentNode);
		
		while (lexer.nextToken().getType() != TokenType.EOF) {
			Token token = lexer.getToken();
			
			if (token.getType() == TokenType.TEXT) {
				parseText();
			} else if (token.getType() == TokenType.TAG_OPENED) {
				lexer.setState(LexerState.TAG);
				parseTag();
			}
		}
		
		if (stack.size() > 1) {
			throw new SmartScriptParserException(
				"Opened non-empty tags must be closed."
			);
		}
	}
	
	/**
	 * Constructs a new <code>TextNode</code> from the text token,
	 * and adds it as a child to the node at the top of the stack.
	 */
	private void parseText() {
		TextNode textNode = new TextNode(
			(String) lexer.getToken().getValue()
		);
		
		((Node) stack.peek()).addChildNode(textNode);
	}
	
	/**
	 * Calls the corresponding method which will parse the specific tag token.
	 */
	private void parseTag() {
		Token token = lexer.nextToken();
		
		if (token.getType() == TokenType.EQUALS_SIGN) {
			parseEqualsTag();
		} else if (token.getType() == TokenType.VARIABLE_NAME) {
			String tagName = (String) token.getValue();
			
			if (tagName.toUpperCase().equals("FOR")) {
				parseForTag();
			} else if (tagName.toUpperCase().equals("END")) {
				parseEndTag();
			} else {
				throw new SmartScriptParserException(
					"Unsupported tag name."
				);
			}
		} else {
			throw new SmartScriptParserException(
				"Invalid tag name."
			);
		}
	}
	
	/**
	 * Constructs a new <code>EchoNode</code> from the parameter tokens,
	 * and adds it as a child to the node at the top of the stack.
	 */
	private void parseEqualsTag() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection() {
			@Override
			public Object[] toArray() {
				int size = size();
				/* Changed to array of Element references because
				 * of the EchoNode constructor parameter type */
				Object[] array = new Element[size];
				
				for (int i = 0; i < size; i ++) {
					array[i] = get(i);
				}
				
				return array;
			}
		};
		
		while (lexer.nextToken().getType() != TokenType.TAG_CLOSED) {
			if (lexer.getToken().getType() == TokenType.EOF) {
				throw new SmartScriptParserException(
					"Expected closing tag bound."
				);
			}
			
			elements.add(parseEqualsTagParameter());
		}
		
		lexer.setState(LexerState.TEXT);
		
		((Node) stack.peek()).addChildNode(new EchoNode(
				(Element[]) elements.toArray())
		);
	}
	
	/**
	 * Constructs a new <code>ForLoopNode</code> from the parameter tokens,
	 * adds it as a child to the node at the top of the stack, and then
	 * pushes itself on the stack.
	 */
	private void parseForTag() {
		Token token = lexer.nextToken();
		
		if (token.getType() != TokenType.VARIABLE_NAME) {
			throw new SmartScriptParserException(
				"First parameter of the FOR tag must be a variable."
			);
		}
		
		ElementVariable variable = new ElementVariable(
			(String) token.getValue()
		);
		
		lexer.nextToken();
		Element startExpression = parseForTagParameter();
		
		lexer.nextToken();
		Element endExpression = parseForTagParameter();
		
		Element stepExpression = null;
		if (lexer.nextToken().getType() == TokenType.TAG_CLOSED) {
			// OK, there are two parameters.
		} else {
			stepExpression = parseForTagParameter();
			
			if (lexer.nextToken().getType() == TokenType.TAG_CLOSED) {
				// OK, there are three parameters.
			} else {
				throw new SmartScriptParserException(
					"Too many arguments in FOR tag."
				);
			}
		}
		
		lexer.setState(LexerState.TEXT);
		
		ForLoopNode forLoopNode = new ForLoopNode(
			variable, startExpression, endExpression, stepExpression
		);
		
		((Node) stack.peek()).addChildNode(forLoopNode);
		stack.push(forLoopNode);
	}
	
	/**
	 * Pops the top <code>Node</code> from the stack.
	 * 
	 * @throws SmartScriptParserException If there are more END tags
	 * 			than there are FOR tags.
	 */
	private void parseEndTag() {
		if (lexer.nextToken().getType() == TokenType.EOF) {
			throw new SmartScriptParserException(
				"Expected closing tag bound."
			);
		}
		
		if (lexer.getToken().getType() != TokenType.TAG_CLOSED) {
			throw new SmartScriptParserException(
				"END tag does not require any arguments."
			);
		}
		
		lexer.setState(LexerState.TEXT);
		
		// Should not throw EmptyStackException (because of DocumentNode).
		stack.pop();
		
		if (stack.size() == 0) {
			throw new SmartScriptParserException(
				"There are more END tags than opened non-empty tags."
			);
		}
	}
	
	/**
	 * Constructs a corresponding (expected) <code>Element</code>
	 * from the <code>EchoNode</code> parameter tokens, and returns it.
	 * 
	 * @return One of the expected elements.
	 */
	private Element parseEqualsTagParameter() {
		Token token = lexer.getToken();
		
		if (token.getType() == TokenType.EOF) {
			throw new SmartScriptParserException(
				"Expected closing tag bound."
			);
		}
		
		if (token.getType() == TokenType.VARIABLE_NAME) {
			return new ElementVariable(
				(String) token.getValue()
			);
		} else if (token.getType() == TokenType.INTEGER_CONSTANT) {
			return new ElementConstantInteger(
				(int) token.getValue()
			);
		} else if (token.getType() == TokenType.DOUBLE_CONSTANT) {
			return new ElementConstantDouble(
				(double) token.getValue()
			);
		} else if (token.getType() == TokenType.STRING) {
			return new ElementString(
				(String) token.getValue()
			);
		} else if (token.getType() == TokenType.FUNCTION_NAME) {
			return new ElementFunction(
				(String) token.getValue()
			);
		} else if (token.getType() == TokenType.OPERATOR) {
			return new ElementOperator(
				String.valueOf(token.getValue())
			);
		}
		
		throw new SmartScriptParserException(
			"Unexpected token."
		);
	}
	
	/**
	 * Constructs a corresponding (expected) <code>Element</code>
	 * from the <code>ForLoopNode</code> parameter tokens, and returns it.
	 * 
	 * @return One of the expected elements.
	 */
	private Element parseForTagParameter() {
		Token token = lexer.getToken();
		
		if (token.getType() == TokenType.VARIABLE_NAME) {
			return new ElementVariable(
				(String) token.getValue()
			);
		} else if (token.getType() == TokenType.INTEGER_CONSTANT) {
			return new ElementConstantInteger(
				(int) token.getValue()
			);
		} else if (token.getType() == TokenType.DOUBLE_CONSTANT) {
			return new ElementConstantDouble(
				(double) token.getValue()
			);
		} else if (token.getType() == TokenType.STRING) {
			return new ElementString(
				(String) token.getValue()
			);
		} else {
			throw new SmartScriptParserException(
				"All non-first FOR tag parameters must be variables, numbers or strings."
			);
		}
	}
}
