package hr.fer.zemris.java.hw07.shell.argumentparser;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.argumentparser.lexer.Lexer;
import hr.fer.zemris.java.hw07.shell.argumentparser.lexer.Token;
import hr.fer.zemris.java.hw07.shell.argumentparser.lexer.TokenType;

/**
 * Class which parses the given text and, if valid, constructs
 * a {@code List} of corresponding arguments.
 * 
 * @author Mate Gasparini
 */
public class ArgumentParser {
	
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
		this(data, true);
	}
	
	/**
	 * Constructor specifying the input data and if escaping is allowed.<br>
	 * It tries to parse the data and, if it is possible,
	 * generates the list of arguments. Otherwise, it throws
	 * an {@code ArgumentParserException}.
	 * 
	 * @param data The input data.
	 * @param escapingAllowed If true, lexer will be able to escape certain
	 * 			characters.
	 * @throws ArgumentParserException If the input data is not parseable.
	 */
	public ArgumentParser(String data, boolean escapingAllowed) {
		try {
			parseData(data, escapingAllowed);
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
	 * @param data The input data.
	 * @param escapingAllowed If true, lexer will be able to escape certain
	 * 			characters.
	 * @throws RuntimeException If the data is not parseable.
	 */
	private void parseData(String data, boolean escapingAllowed) {
		lexer = new Lexer(data.trim(), escapingAllowed);
		
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
