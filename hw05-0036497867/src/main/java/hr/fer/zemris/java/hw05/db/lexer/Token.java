package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Class that represents a small portion of data that a lexer
 * extracts from the input data. It has a type and can have a value.
 * 
 * @author Mate Gasparini
 */
public class Token {
	
	/**
	 * Type of the token.
	 */
	private TokenType type;
	/**
	 * Value of the token. Can be null.
	 */
	private String value;
	
	/**
	 * Constructor specifying the type and the value of the token.
	 * 
	 * @param type The type of the token.
	 * @param value The value of the token. Can be null.
	 * @throws NullPointerException If {@code type} is {@code null}.
	 */
	public Token(TokenType type, String value) {
		if (type == null) {
			throw new NullPointerException("Token type cannot be null.");
		}
		
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
