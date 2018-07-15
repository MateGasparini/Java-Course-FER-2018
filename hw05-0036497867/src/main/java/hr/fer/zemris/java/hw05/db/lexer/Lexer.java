package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Class which tokenizes the {@link StudentDB} program input.
 * 
 * @author Mate Gasparini
 */
public class Lexer {
	
	/**
	 * The input text which needs to be tokenized.
	 */
	private char[] data;
	/**
	 * The current token.
	 */
	private Token token;
	/**
	 * The index of the current character from the input text.
	 */
	private int currentIndex;
	
	/**
	 * Constructor specifying the input text which needs to be tokenized.
	 * 
	 * @param text String which needs to be tokenized.
	 * @throws NullPointerException If {@code text} is {@code null}.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException("Text must not be null.");
		}
		
		data = text.toCharArray();
	}
	
	/**
	 * Generates the next {@code Token} and returns it.
	 * 
	 * @return The next token from the input data.
	 * @throws LexerException If there was a problem tokenizing the data, or
	 * 			if this method was called after EOF has been already reached.
	 */
	public Token nextToken() {
		skipWhitespaces();
		
		if (currentIndex < data.length) {
			char current = data[currentIndex];
			
			if (current == '\"') {
				currentIndex ++;
				token = new Token(TokenType.STRING_LITERAL, readStringLiteral());
			} else if (followsLessOrEqualsOperator(current)) {
				token = new Token(TokenType.OPERATOR_LESS_OR_EQUALS, null);
				currentIndex += 2;
			} else if (followsLessOperator(current)) {
				token = new Token(TokenType.OPERATOR_LESS, null);
				currentIndex ++;
			} else if (followsGreaterOrEqualsOperator(current)) {
				token = new Token(TokenType.OPERATOR_GREATER_OR_EQUALS, null);
				currentIndex += 2;
			} else if (followsGreaterOperator(current)) {
				token = new Token(TokenType.OPERATOR_GREATER, null);
				currentIndex ++;
			} else if (followsEqualsOperator(current)) {
				token = new Token(TokenType.OPERATOR_EQUALS, null);
				currentIndex ++;
			} else if (followsNotEqualsOperator(current)) {
				token = new Token(TokenType.OPERATOR_NOT_EQUALS, null);
				currentIndex += 2;
			} else if (followsLikeOperator(current)) {
				token = new Token(TokenType.OPERATOR_LIKE, null);
				currentIndex += 4;
			} else if (followsAndOperator(current)) {
				token = new Token(TokenType.AND_OPERATOR, null);
				currentIndex += 3;
			} else {
				token = new Token(TokenType.ATTRIBUTE_NAME, readAttributeName());
			}
		} else if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
		} else {
			throw new LexerException("End of data has already been reached.");
		}
		
		return token;
	}
	
	/**
	 * Returns true if the given character is a whitespace character.
	 * 
	 * @param c The given character.
	 * @return {@code true} if the given character is a space or a tab,
	 * 			and {@code false} otherwise.
	 */
	private boolean isWhitespace(char c) {
		return c == ' ' || c == '\t';
	}
	
	/**
	 * Moves the {@code currentIndex} until either the end of the input text
	 * is reached, or until a non-whitespace character is found (as specified
	 * by the {@code isWhitespace} method).
	 */
	private void skipWhitespaces() {
		while (currentIndex < data.length) {
			if (isWhitespace(data[currentIndex])) {
				currentIndex ++;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Returns true if some of the simple logical operators is following.<br>
	 * These are: "<", ">", "=", "<=", ">=", "!=".
	 * 
	 * @param c The current character.
	 * @return {@code true} if an operator is following,
	 * 			or {@code false} otherwise.
	 */
	private boolean followsSomeSymbolOperator(char c) {
		return followsLessOperator(c)
			|| followsGreaterOperator(c)
			|| followsEqualsOperator(c)
			|| followsLessOrEqualsOperator(c)
			|| followsGreaterOrEqualsOperator(c)
			|| followsNotEqualsOperator(c);
	}
	
	/**
	 * Returns true if the "<" operator is following.
	 * 
	 * @param c The current character.
	 * @return {@code true} if the "<" operator is following,
	 * 			or {@code false} otherwise.
	 */
	private boolean followsLessOperator(char c) {
		return c == '<';
	}
	
	/**
	 * Returns true if the "<=" operator is following.
	 * 
	 * @param c The current character.
	 * @return {@code true} if the "<=" operator is following,
	 * 			or {@code false} otherwise.
	 */
	private boolean followsLessOrEqualsOperator(char c) {
		return c == '<'
			&& currentIndex + 1 < data.length
			&& data[currentIndex + 1] == '=';
	}
	
	/**
	 * Returns true if the ">" operator is following.
	 * 
	 * @param c The current character.
	 * @return {@code true} if the ">" operator is following,
	 * 			or {@code false} otherwise.
	 */
	private boolean followsGreaterOperator(char c) {
		return c == '>';
	}
	
	/**
	 * Returns true if the ">=" operator is following.
	 * 
	 * @param c The current character.
	 * @return {@code true} if the ">=" operator is following,
	 * 			or {@code false} otherwise.
	 */
	private boolean followsGreaterOrEqualsOperator(char c) {
		return c == '>'
			&& currentIndex + 1 < data.length
			&& data[currentIndex + 1] == '=';
	}
	
	/**
	 * Returns true if the "=" operator is following.
	 * 
	 * @param c The current character.
	 * @return {@code true} if the "=" operator is following,
	 * 			or {@code false} otherwise.
	 */
	private boolean followsEqualsOperator(char c) {
		return c == '=';
	}
	
	/**
	 * Returns true if the "!=" operator is following.
	 * 
	 * @param c The current character.
	 * @return {@code true} if the "!=" operator is following,
	 * 			or {@code false} otherwise.
	 */
	private boolean followsNotEqualsOperator(char c) {
		return c == '!'
			&& currentIndex + 1 < data.length
			&& data[currentIndex + 1] == '=';
	}
	
	/**
	 * Returns true if the "LIKE" operator is following.
	 * 
	 * @param c The current character.
	 * @return {@code true} if the "LIKE" operator is following,
	 * 			or {@code false} otherwise.
	 */
	private boolean followsLikeOperator(char c) {
		return c == 'L'
			&& currentIndex + 3 < data.length
			&& data[currentIndex + 1] == 'I'
			&& data[currentIndex + 2] == 'K'
			&& data[currentIndex + 3] == 'E';
	}
	
	/**
	 * Returns true if the special "AND" operator is following.<br>
	 * This method is case-insensitive.
	 * 
	 * @param c The current character.
	 * @return {@code true} if the "AND" operator is following,
	 * 			or {@code false} otherwise.
	 */
	private boolean followsAndOperator(char c) {
		if (currentIndex + 2 < data.length) {
			return c == 'A' || c == 'a'
				&& data[currentIndex + 1] == 'N' || data[currentIndex + 1] == 'n'
				&& data[currentIndex + 2] == 'D' || data[currentIndex + 2] == 'd';
		}
		
		return false;
	}
	
	/**
	 * Reads the string literal from the input text (until the closing
	 * quotes are reached).
	 * 
	 * @return The read string literal.
	 * @throws LexerException If the end of the input text is reached
	 * 			before the closing quotes.
	 */
	private String readStringLiteral() {
		StringBuilder builder = new StringBuilder();
		
		while (true) {
			if (currentIndex == data.length) {
				throw new LexerException(
					"Expected closing quotes."
				);
			}
			
			char current = data[currentIndex];
			
			if (current == '\"') {
				currentIndex ++;
				break;
			}
			
			builder.append(current);
			currentIndex ++;
		}
		
		return builder.toString();
	}
	
	/**
	 * Reads the attribute name from the input text (until a whitespace
	 * or the end of the text input is reached).
	 * 
	 * @return The read attribute name.
	 */
	private String readAttributeName() {
		StringBuilder builder = new StringBuilder();
		
		while (true) {
			if (currentIndex == data.length) {
				break;
			}
			
			char current = data[currentIndex];
			
			if (followsSomeSymbolOperator(current) || isWhitespace(current)) {
				break;
			}
			
			builder.append(current);
			currentIndex ++;
		}
		
		return builder.toString();
	}
}
