package hr.fer.zemris.java.hw07.shell.lexer;

/**
 * Enumeration containing all types of tokens.
 * 
 * @author Mate Gasparini
 */
public enum TokenType {
	
	/**
	 * Token inside quotes.
	 */
	QUOTED,
	
	/**
	 * Token outside quotes (a simple word).
	 */
	NON_QUOTED,
	
	/**
	 * A space or a tab character.
	 */
	WHITESPACE,
	
	/**
	 * End of input.
	 */
	END
}
