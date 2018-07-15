package hr.fer.zemris.java.hw03.prob1;

/**
 * Class that represents a lexer with basic tokenization functionality.
 * 
 * @author Mate Gasparini
 */
public class Lexer {
	
	/**
	 * Data which needs to be tokenized.
	 */
	private char[] data;
	/**
	 * Current token.
	 */
	private Token token;
	/**
	 * Index of the current data character.
	 */
	private int currentIndex;
	/**
	 * State specifying if escaping is allowed or not.
	 */
	private LexerState state;
	
	/**
	 * Symbol which changes the state.
	 */
	public static final char CHANGE_STATE_SYMBOL = '#';
	
	/**
	 * Constructor specifying the input text which needs to be tokenized.
	 * 
	 * @param text String which needs to be tokenized.
	 * @throws NullPointerException If the input text is null.
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException("Text must not be null.");
		}
		
		data = text.toCharArray();
		state = LexerState.BASIC;
	}
	
	/**
	 * Generates the next <code>Token</code> and returns it.
	 * 
	 * @return The next token from the input data.
	 * @throws LexerException If there was a problem tokenizing the data, or
	 * 			if this method was called after EOF has been already reached.
	 */
	public Token nextToken() {
		skipIgnorable();
		
		if (currentIndex < data.length) {
			char current = data[currentIndex];
			
			if (state == LexerState.BASIC) {
				if (Character.isLetter(current) || current == '\\') {
					token = new Token(TokenType.WORD, generateWordValue());
				} else if (Character.isDigit(current)) {
					token = new Token(TokenType.NUMBER, generateNumberValue());
				} else {
					token = new Token(TokenType.SYMBOL, generateSymbolValue());
				}
			} else {
				if (current == CHANGE_STATE_SYMBOL) {
					token = new Token(TokenType.SYMBOL, generateSymbolValue());
				} else {
					token = new Token(TokenType.WORD, generateWordExtended());
				}
			}
		} else if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			currentIndex ++;
		} else {
			throw new LexerException("There are no tokens after EOF.");
		}
		
		return token;
	}
	
	/**
	 * Returns the current <code>Token</code> (does not generate a new one).
	 * 
	 * @return The current token.
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Sets this lexer's state to the given state.
	 * 
	 * @param state The given state.
	 * @throws NullPointerException If the given state is null.
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException("State must not be null.");
		}
		
		this.state = state;
	}
	
	/**
	 * Skips all ignorable characters (as specified in
	 * the <code>isIgnorable</code> method).
	 */
	private void skipIgnorable() {
		while (currentIndex < data.length) {
			char current = data[currentIndex];
			
			if (isIgnorable(current)) {
				currentIndex ++;
				continue;
			} else {
				break;
			}
		}
	}
	
	/**
	 * Returns true if the given character is ignorable (as defined in the
	 * homework assignment).
	 * 
	 * @param c The given character.
	 * @return <code>true</code> if the given character is ignorable, and
	 * 			<code>false</code> otherwise.
	 */
	private boolean isIgnorable(char c) {
		return c == '\r' || c == '\n' || c == '\t' || c == ' ';
	}
	
	/**
	 * Finds the next <code>WORD</code> in the input data,
	 * and returns it.
	 * 
	 * @return The next <code>WORD</code>.
	 * @throws LexerException If there is an invalid escape sequence.
	 */
	private String generateWordValue() {
		StringBuilder builder = new StringBuilder();
		
		while (currentIndex < data.length) {
			if (data[currentIndex] == '\\') {
				if (currentIndex < data.length - 2 &&
						(Character.isDigit(data[currentIndex + 1]) ||
						data[currentIndex + 1] == '\\')) {
					builder.append(data[++ currentIndex]);
				} else {
					throw new LexerException("Invalid escape sequence.");
				}
			} else if (Character.isLetter(data[currentIndex])) {
				builder.append(data[currentIndex]);
			} else {
				break;
			}
			
			currentIndex ++;
		}
		
		return builder.toString();
	}
	
	/**
	 * Finds the next <code>NUMBER</code> in the input data,
	 * and returns it.
	 * 
	 * @return The next <code>NUMBER</code>.
	 * @throws LexerException If the number cannot be represented
	 * 			with <code>Long</code>.
	 */
	private Long generateNumberValue() {
		StringBuilder builder = new StringBuilder();
		
		while (currentIndex < data.length) {
			if (Character.isDigit(data[currentIndex])) {
				builder.append(data[currentIndex ++]);
			} else {
				break;
			}
		}
		
		String numberString = builder.toString();
		
		try {
			return Long.parseLong(numberString);
		} catch (NumberFormatException ex) {
			throw new LexerException(
				"Cannot parse " + numberString + " to Long."
			);
		}
	}
	
	/**
	 * Finds the next <code>SYMBOL</code> in the input data, and
	 * returns it.
	 * 
	 * @return The next <code>SYMBOL</code>.
	 */
	private Character generateSymbolValue() {
		return data[currentIndex ++];
	}
	
	/**
	 * Finds the next <code>WORD</code> in the input data,
	 * and returns it. This method is called in the <code>EXTENDED</code>
	 * state, so it returns strings specified for it.
	 * 
	 * @return The next <code>WORD</code>
	 */
	private String generateWordExtended() {
		StringBuilder builder = new StringBuilder();
		
		while (currentIndex < data.length) {
			if (isIgnorable(data[currentIndex]) ||
					data[currentIndex] == CHANGE_STATE_SYMBOL) {
				break;
			}
			builder.append(data[currentIndex ++]);
		}
		
		return builder.toString();
	}
}
