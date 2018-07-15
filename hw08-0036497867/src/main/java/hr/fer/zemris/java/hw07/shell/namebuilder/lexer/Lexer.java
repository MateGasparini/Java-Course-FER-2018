package hr.fer.zemris.java.hw07.shell.namebuilder.lexer;

/**
 * Lexer for the {@code NameBuilderParser}.
 * 
 * @author Mate Gasparini
 */
public class Lexer {
	
	/**
	 * Current token.
	 */
	private Token token;
	
	/**
	 * Data which needs to be tokenized.
	 */
	private char[] data;
	
	/**
	 * Current position in data.
	 */
	private int currentIndex;
	
	/**
	 * Constructor specifying the input data.
	 * 
	 * @param text The specified input data.
	 */
	public Lexer(String text) {
		data = text.toCharArray();
	}
	
	/**
	 * Returns the current token.
	 * 
	 * @return The current token.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Generates and returns the next token.
	 * 
	 * @return The next token.
	 */
	public Token nextToken() {
		if (endReached()) {
			token = new Token(TokenType.END, null);
		} else if (followsTagOpened()) {
			currentIndex += 2;
			token = new Token(TokenType.SPECIAL, readSpecial());
		} else {
			token = new Token(TokenType.LITERAL, readLiteral());
		}
		
		return token;
	}
	
	/**
	 * Returns true if the end is reached.
	 * 
	 * @return {@code true} if the end is reached, and {@code false} otherwise.
	 */
	private boolean endReached() {
		return currentIndex >= data.length;
	}
	
	/**
	 * Returns true if a whitespace follows.
	 * 
	 * @return {@code true} if a whitespace follows, and {@code false} otherwise.
	 */
	private boolean followsWhitespace() {
		return isWhitespace(data[currentIndex]);
	}
	
	/**
	 * Returns true if the given character is a whitespace.
	 * 
	 * @param c The given character.
	 * @return {@code true} if the given character is a whitespace,
	 * 			and {@code false} otherwise.
	 */
	private boolean isWhitespace(char c) {
		return c == ' ' || c == '\t';
	}
	
	/**
	 * Skips all whitespaces until end or non-whitespace is reached.
	 */
	private void skipWhitespaces() {
		while (true) {
			if (endReached()) {
				break;
			}
			
			if (followsWhitespace()) {
				currentIndex ++;
				continue;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Returns true if a tag opener follows.
	 * 
	 * @return {@code true} if a tag opener follows, and {@code false} otherwise.
	 */
	private boolean followsTagOpened() {
		return data[currentIndex] == '$'
			&& currentIndex + 1 < data.length && data[currentIndex + 1] == '{';
	}
	
	/**
	 * Returns true if a tag closer follows.
	 * 
	 * @return {@code true} if a tag closer follows, and {@code false} otherwise.
	 */
	private boolean followsTagClosed() {
		return data[currentIndex] == '}';
	}
	
	/**
	 * Returns true if a digit follows.
	 * 
	 * @return {@code true} if a digit follows, and {@code false} otherwise.
	 */
	private boolean followsDigit() {
		return data[currentIndex] >= '0' && data[currentIndex] <= '9';
	}
	
	/**
	 * Returns true if a comma follows.
	 * 
	 * @return {@code true} if a comma follows, and {@code false} otherwise.
	 */
	private boolean followsComma() {
		return data[currentIndex] == ',';
	}
	
	/**
	 * Returns the content of the text inside a tag.
	 * 
	 * @return The contents of the following tag.
	 */
	private String readSpecial() {
		StringBuilder builder = new StringBuilder();
		
		skipWhitespaces();
		
		if (endReached()) {
			throw new RuntimeException(
				"expected closing tag before end of input."
			);
		} else if (followsTagClosed()) {
			throw new RuntimeException(
				"expected a digit before closing tag."
			);
		} else if (followsDigit()) {
			while (followsDigit()) {
				builder.append(data[currentIndex ++]);
			}
			
			skipWhitespaces();
			
			if (followsTagClosed()) {
				currentIndex ++;
			} else if (followsDigit()) {
				throw new RuntimeException(
					"Whitespaces before '" + data[currentIndex] + "' not allowed."
				);
			} else if (followsComma()) {
				currentIndex ++;
				builder.append(",");
				
				skipWhitespaces();
				
				if (followsDigit()) {
					while (followsDigit()) {
						builder.append(data[currentIndex ++]);
					}
				} else {
					throw new RuntimeException(
						"Expected digits after comma."
					);
				}
				
				skipWhitespaces();
				
				if (followsTagClosed()) {
					currentIndex ++;
				} else {
					throw new RuntimeException(
						"Only two numbers (separated by a comma) allowed."
					);
				}
			}
		} else {
			throw new RuntimeException(
					data[currentIndex] + ": unexpected token."
			);
		}
		
		return builder.toString();
	}
	
	/**
	 * Returns an ordinary String literal (everything except a tag).
	 * 
	 * @return An ordinary String literal.
	 */
	private String readLiteral() {
		StringBuilder builder = new StringBuilder();
		
		while (true) {
			if (endReached() || followsTagOpened()) {
				break;
			} else {
				builder.append(data[currentIndex ++]);
			}
		}
		
		return builder.toString();
	}
}
