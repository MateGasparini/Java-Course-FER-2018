package hr.fer.zemris.java.hw03.prob1;

/**
 * Class that represents a small portion of data that a lexer
 * extracts from the input data. It has a type and can have a value.
 * 
 * @author Mate Gasparini
 */
public class Token {
	
	/**
	 * Value of the token. Can be null.
	 */
	private Object value;
	/**
	 * Type of the token, as specified in <code>TokenType</code>.
	 */
	private TokenType type;
	
	/**
	 * Constructor specifying the type and the value of the token.
	 * 
	 * @param type The type of the token.
	 * @param value The value of the token. Can be null.
	 * @throws NullPointerException If <code>type</code> is <code>null</code>.
	 */
	public Token(TokenType type, Object value) {
		if (type == null) {
			throw new NullPointerException("Token type cannot be null.");
		}
		
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the token's value.
	 * 
	 * @return The token's value.
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Returns the token's type.
	 * 
	 * @return The token's type.
	 */
	public TokenType getType() {
		return type;
	}
}
