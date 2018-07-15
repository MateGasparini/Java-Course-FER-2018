package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Class that represents a lexer which returns tokens based on the
 * input data (as specified in the second part of the homework).
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
	 * State specifying whether text or tags are being read.
	 */
	private LexerState state;
	
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
		state = LexerState.TEXT;
	}
	
	/**
	 * Generates the next <code>Token</code> and returns it.
	 * 
	 * @return The next token from the input data.
	 * @throws LexerException If there was a problem tokenizing the data, or
	 * 			if this method was called after EOF has been already reached.
	 */
	public Token nextToken() {
		if (currentIndex < data.length) {
			if (state == LexerState.TEXT) {
				tokenizeText();
			} else {
				tokenizeTag();
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
	 * Returns true if the given character is an operator (as defined in the
	 * homework assignment).
	 * 
	 * @param c The given character.
	 * @return <code>true</code> if the given character is an operator, and
	 * 			<code>false</code> otherwise.
	 */
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
	}
	
	/**
	 * Returns a valid escaped character, which is either the given character,
	 * or a new one (e.g. '\n', '\r', '\t').
	 * 
	 * @param c The given character (whose predecessor is a backslash).
	 * @return A valid escaped character.
	 * @throws If the given character cannot be escaped.
	 */
	private char generateValidEscape(char c) {
		if (state == LexerState.TEXT) {
			if (c == '\\' || c == '{') {
				return c;
			}
		} else {
			if (c == '\\' || c == '\"') {
				return c;
			} else if (c == 'n') {
				return '\n';
			} else if (c == 'r') {
				return '\r';
			} else if (c == 't') {
				return '\t';
			}
		}
		
		throw new LexerException(
			"Invalid escape sequence: \\" + c + "."
		);
	}
	
	/**
	 * Returns true if the following data contains a sequence representing
	 * a tag opening.
	 * 
	 * @return <code>true</code> if the following data is a tag opening, and
	 * 			<code>false</code> otherwise.
	 */
	private boolean followsValidTagOpening() {
		return data[currentIndex] == '{'
				&& currentIndex + 1 < data.length
				&& data[currentIndex + 1] == '$';
	}
	
	/**
	 * Returns true if the following data contains a sequence representing
	 * a tag closure.
	 * 
	 * @return <code>true</code> if the following data is a tag closure, and
	 * 			<code>false</code> otherwise.
	 */
	private boolean followsValidTagClosure() {
		return data[currentIndex] == '$'
				&& currentIndex + 1 < data.length
				&& data[currentIndex + 1] == '}';
	}
	
	/**
	 * Returns true if the following data contains a minus sign followed by
	 * a digit.
	 * 
	 * @return <code>true</code> if the following data is a negative number,
	 * 			and <code>false</code> otherwise.
	 */
	private boolean followsNegativeNumber() {
		return data[currentIndex] == '-'
				&& currentIndex + 1 < data.length
				&& Character.isDigit(data[currentIndex + 1]);
	}
	
	/**
	 * Generates either a tag opening token, or a text token.
	 */
	private void tokenizeText() {
		if (followsValidTagOpening()) {
			currentIndex += 2;
			
			token = new Token(TokenType.TAG_OPENED, null);
		} else {
			generateTextToken();
		}
	}
	
	/**
	 * Generates the corresponding token from the tag.
	 */
	private void tokenizeTag() {
		skipIgnorable();
		
		char current = data[currentIndex];
		
		if (followsValidTagClosure()) {
			currentIndex += 2;
			
			token = new Token(TokenType.TAG_CLOSED, null);
		} else if (current == '=') {
			token = new Token(TokenType.EQUALS_SIGN, null);
			
			currentIndex ++;
		} else if (current == '@') {
			currentIndex ++;
			
			token = new Token(
				TokenType.FUNCTION_NAME, getValidName()
			);
		} else if (isOperator(current)) {
			if (followsNegativeNumber()) {
				createNumberToken(getValidNumber());
			} else {
				token = new Token(TokenType.OPERATOR, current);
				
				currentIndex ++;
			}
		} else if (Character.isDigit(current)) {
			createNumberToken(getValidNumber());
		} else if (Character.isLetter(current)) {
			token = new Token(TokenType.VARIABLE_NAME, getValidName());
		} else if (current == '\"') {
			currentIndex ++;
			
			token = new Token(TokenType.STRING, getValidString());
		} else {
			throw new LexerException("Invalid character: " + current);
		}
	}
	
	/**
	 * Generates a basic text token (including specified escape characters).
	 */
	private void generateTextToken() {
		StringBuilder builder = new StringBuilder();
		
		while (currentIndex < data.length) {
			char current = data[currentIndex];
			
			if (followsValidTagOpening()) {
				break;
			} else if (current == '\\') {
				currentIndex ++;
				
				if (currentIndex < data.length) {
					current = data[currentIndex];
					
					try {
						builder.append(generateValidEscape(current));
					} catch (LexerException ex) {
						throw new LexerException(
							ex.getMessage()
						);
					}
				} else {
					throw new LexerException(
						"Empty escape sequence not allowed."
					);
				}
			} else {
				builder.append(current);
			}
			
			currentIndex ++;
		}
		
		token = new Token(TokenType.TEXT, builder.toString());
	}
	
	/**
	 * Returns a String that starts with a letter followed by letters,
	 * digits or underscores.
	 * 
	 * @return A String representing a valid name.
	 */
	private String getValidName() {
		StringBuilder builder = new StringBuilder();
		
		if (currentIndex < data.length &&
				!Character.isLetter(data[currentIndex])) {
			throw new LexerException(
				"Invalid character: " + data[currentIndex] + "."
			);
		} else {
			builder.append(data[currentIndex ++]);
		}
		
		while (currentIndex < data.length) {
			char current = data[currentIndex];
			
			if (Character.isLetterOrDigit(current) || current == '_') {
				builder.append(current);
			} else {
				break;
			}
			
			currentIndex ++;
		}
		
		return builder.toString();
	}
	
	/**
	 * Generates an Integer (from the following digits in the data)
	 * or a Double (from the following digits and a dot in the data).
	 * 
	 * @return A Number representing a valid Integer/Double.
	 */
	private Number getValidNumber() {
		StringBuilder builder = new StringBuilder();
		
		if (data[currentIndex] == '-') {
			builder.append('-');
		}
		
		boolean isDouble = false;
		
		while (currentIndex < data.length) {
			char current = data[currentIndex];
			
			if (Character.isDigit(current)) {
				builder.append(current);
			} else if (current == '.') {
				builder.append(current);
				isDouble = true;
			} else {
				break;
			}
			
			currentIndex ++;
		}
		
		String numberString = builder.toString();
		Number validNumber;
		
		try {
			if (isDouble) {
				validNumber = Double.parseDouble(numberString);
			} else {
				validNumber = Integer.parseInt(numberString);
			}
		} catch (NumberFormatException ex) {
			throw new LexerException("Invalid number: " + numberString);
		}
		
		return validNumber;
	}
	
	/**
	 * Returns a String from the data by taking all the characters between
	 * the quotes (including valid escape sequences).
	 * 
	 * @return A valid String.
	 */
	private String getValidString() {
		StringBuilder builder = new StringBuilder();
		
		while (currentIndex < data.length) {
			char current = data[currentIndex];
			
			// String is valid only if it ends with quotes.
			if (current == '\"') {
				currentIndex ++;
				return builder.toString();
			} else if (current == '\\') {
				currentIndex ++;
				
				if (currentIndex < data.length) {
					current = data[currentIndex];
					
					try {
						builder.append(generateValidEscape(current));
					} catch (LexerException ex) {
						throw new LexerException(
							ex.getMessage()
						);
					}
				} else {
					throw new LexerException(
						"Empty escape sequence not allowed."
					);
				}
			} else {
				builder.append(current);
			}
			
			currentIndex ++;
		}
		
		throw new LexerException("Invalid String. Expected \" at the end.");
	}
	
	/**
	 * Takes the generated Number, and generates the corresponding
	 * token (either an Integer constant, or a Double constant).
	 * 
	 * @param number Number from which a token will be generated.
	 */
	private void createNumberToken(Number number) {
		if (number instanceof Integer) {
			token = new Token(TokenType.INTEGER_CONSTANT, number);
		} else if (number instanceof Double) {
			token = new Token(TokenType.DOUBLE_CONSTANT, number);
		} else {
			throw new LexerException(
				"Only Integer and Double constants are allowed."
			);
		}
	}
}
