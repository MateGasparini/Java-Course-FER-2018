package hr.fer.zemris.java.hw07.shell.parser;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.lexer.Lexer;
import hr.fer.zemris.java.hw07.shell.lexer.Token;
import hr.fer.zemris.java.hw07.shell.lexer.TokenType;

/**
 * Class which parses the given text and, if valid, constructs
 * a {@code List} of corresponding arguments.
 * 
 * @author Mate Gasparini
 */
public class ArgumentParser {
	
	/**
	 * The input data.
	 */
	private String data;
	
	/**
	 * Lexer used to parse tokens from the input data.
	 */
	private Lexer lexer;
	
	/**
	 * List of parsed arguments.
	 */
	private List<String> arguments = new LinkedList<>();
	
	/**
	 * Constructor specifying the input data.<br>
	 * It tries to parse the data and, if it is possible,
	 * generates the list of arguments. Otherwise, it throws
	 * an {@code ArgumentParserException}.
	 * 
	 * @param data The input data.
	 * @throws ArgumentParserException If the input data is not parseable.
	 */
	public ArgumentParser(String data) {
		this.data = data;
		try {
			parseData();
		} catch (RuntimeException ex) {
			throw new ArgumentParserException(ex.getMessage());
		}
	}
	
	/**
	 * Returns the generated list of arguments.
	 * 
	 * @return The list of arguments.
	 */
	public List<String> getArguments() {
		return arguments;
	}
	
	/**
	 * Parses the input data, or throws an appropriate {@code RuntimeException}.
	 * 
	 * @throws RuntimeException If the data is not parseable.
	 */
	private void parseData() {
		lexer = new Lexer(data.trim());
		
		while (true) {
			Token token = lexer.nextToken();
			
			if (token.getType() == TokenType.END) {
				break;
			} else if (token.getType() == TokenType.QUOTED) {
				String argument = token.getValue();
				
				token = lexer.nextToken();
				if (token.getType() == TokenType.END) {
					arguments.add(argument);
					break;
				} else if (token.getType() == TokenType.WHITESPACE) {
					arguments.add(argument);
				} else {
					throw new ArgumentParserException(
						"closing quotes must be followed by whitespace or end of input."
					);
				}
			} else if (token.getType() == TokenType.NON_QUOTED) {
				arguments.add(token.getValue());
			}
		}
	}
}
