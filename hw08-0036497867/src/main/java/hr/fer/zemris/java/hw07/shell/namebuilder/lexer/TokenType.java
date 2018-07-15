package hr.fer.zemris.java.hw07.shell.namebuilder.lexer;

/**
 * Enumeration containing all types of tokens.
 * 
 * @author Mate Gasparini
 */
public enum TokenType {
	
	/**
	 * An ordinary non-tagged text token.
	 */
	LITERAL,
	
	/**
	 * A tag token.
	 */
	SPECIAL,
	
	/**
	 * End of input.
	 */
	END
}
