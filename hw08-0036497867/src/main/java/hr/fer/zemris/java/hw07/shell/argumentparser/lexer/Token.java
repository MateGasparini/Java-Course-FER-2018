package hr.fer.zemris.java.hw07.shell.argumentparser.lexer;

/**
 * Class representing a token for the {@code Lexer}.
 * 
 * @author Mate Gasparini
 */
public class Token {
	
	/**
	 * Type of the token.
	 */
	private TokenType type;
	
	/**
	 * Value of the token.
	 */
	private String value;
	
	/**
	 * Constructor specifying the token's type and value.
	 * 
	 * @param type The specified type.
	 * @param value The specified value.
	 */
	public Token(TokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the token's type.
	 * 
	 * @return The token's type.
	 */
	public TokenType getType() {
		return type;
	}
	
	/**
	 * Returns the token's value.
	 * 
	 * @return The token's value.
	 */
	public String getValue() {
		return value;
	}
}
