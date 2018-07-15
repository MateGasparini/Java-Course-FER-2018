package hr.fer.zemris.java.hw07.shell.lexer;

/**
 * Lexer for the {@code ArgumentParser}.
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
		} else if (followsWhitespace()) {
			token = new Token(TokenType.WHITESPACE, null);
			currentIndex ++;
			skipWhitespaces();
		} else if (followsQuotes()) {
			currentIndex ++;
			token = new Token(TokenType.QUOTED, readQuoted());
		} else {
			token = new Token(TokenType.NON_QUOTED, readNonQuoted());
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
	 * Returns true if quotes follow.
	 * 
	 * @return {@code true} if a quotes follow, and {@code false} otherwise.
	 */
	private boolean followsQuotes() {
		return data[currentIndex] == '"';
	}
	
	/**
	 * Returns true if a backslash follows.
	 * 
	 * @return {@code true} if a backslash follows, and {@code false} otherwise.
	 */
	private boolean followsBackslash() {
		return data[currentIndex] == '\\';
	}
	
	/**
	 * Returns the content of the quoted text.
	 * 
	 * @return The text inside quotes.
	 */
	private String readQuoted() {
		StringBuilder builder = new StringBuilder();
		
		while (true) {
			if (endReached()) {
				throw new RuntimeException(
					"expected closing quotes before end of input."
				);
			}else if (followsQuotes()) {
				currentIndex ++;
				break;
			} else if (followsBackslash()) {
				currentIndex ++;
				
				if (endReached()) {
					throw new RuntimeException(
						"expected an escaped character before end of input."
					);
				} else if (followsQuotes()) {
					builder.append('\"');
				} else if (followsBackslash()) {
					builder.append('\\');
				} else {
					throw new RuntimeException(
						"'" + data[currentIndex] + "' is not a valid escaped character."
					);
				}
			} else {
				builder.append(data[currentIndex]);
			}
			
			currentIndex ++;
		}
		
		return builder.toString();
	}
	
	/**
	 * Returns a non-quoted word.
	 * 
	 * @return A non-quoted word.
	 */
	private String readNonQuoted() {
		StringBuilder builder = new StringBuilder();
		
		while (true) {
			if (endReached() || followsQuotes() || followsWhitespace()) {
				break;
			} else {
				builder.append(data[currentIndex]);
			}
			
			currentIndex ++;
		}
		
		return builder.toString();
	}
}
