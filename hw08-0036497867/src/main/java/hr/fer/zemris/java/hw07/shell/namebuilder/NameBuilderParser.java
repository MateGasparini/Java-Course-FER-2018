package hr.fer.zemris.java.hw07.shell.namebuilder;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.namebuilder.lexer.Lexer;
import hr.fer.zemris.java.hw07.shell.namebuilder.lexer.Token;
import hr.fer.zemris.java.hw07.shell.namebuilder.lexer.TokenType;

/**
 * Class which parses the given text and, if valid, constructs
 * a {@code List} of corresponding name builders.
 * 
 * @author Mate Gasparini
 */
public class NameBuilderParser {
	
	/**
	 * The specified input text.
	 */
	private String data;
	
	/**
	 * Lexer used to parse tokens from the input text.
	 */
	private Lexer lexer;
	
	/**
	 * List of constructed corresponding name builders.
	 */
	private List<NameBuilder> nameBuilders = new LinkedList<>();
	
	/**
	 * Constructor specifying the input text.
	 * 
	 * @param data The specified input text.
	 */
	public NameBuilderParser(String data) {
		this.data = data;
		
		try {
			parseData();
		} catch (RuntimeException ex) {
			throw new NameBuilderParserException(ex.getMessage());
		}
	}
	
	/**
	 * Returns a {@code FinalNameBuilder} which is constructed using
	 * the constructed List of all name builders.
	 * 
	 * @return Name builder constructed using all constructed name builders.
	 */
	public NameBuilder getNameBuilder() {
		return new FinalNameBuilder(nameBuilders);
	}
	
	/**
	 * Parses the input data, or throws an appropriate {@code RuntimeException}.
	 * 
	 * @throws RuntimeException If the data is not parseable.
	 */
	private void parseData() {
		lexer = new Lexer(data);
		
		while (true) {
			Token token = lexer.nextToken();
			
			if (token.getType() == TokenType.END) {
				break;
			} else if (token.getType() == TokenType.LITERAL) {
				nameBuilders.add(new LiteralNameBuilder(token.getValue()));
			} else if (token.getType() == TokenType.SPECIAL) {
				nameBuilders.add(new SpecialNameBuilder(token.getValue()));
			}
		}
	}
}
