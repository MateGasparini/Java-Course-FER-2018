package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.lexer.Lexer;
import hr.fer.zemris.java.hw05.db.lexer.LexerException;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

/**
 * Class that represents a parser of some specified query statement
 * for the {@link StudentDB} program.
 * 
 * @author Mate Gasparini
 */
public class QueryParser {
	
	/**
	 * This parser's lexer which tokenizes the specified query.
	 */
	private Lexer lexer;
	/**
	 * A list of expressions generated from parsing the specified query.
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Constructor specifying the arguments of the query command.
	 * 
	 * @param query Everything entered after the query keyword.
	 */
	public QueryParser(String query) {
		lexer = new Lexer(query);
		expressions = new LinkedList<>();
		
		try {
			parse();
		} catch (LexerException ex) {
			throw new QueryParserException(ex.getMessage());
		}
	}
	
	/**
	 * Returns {@code true} if the query is of type:<br>
	 * <i>jmbag="xxxx"</i>,<br>
	 * i.e. it must have only one comparison, should be on attribute
	 * jmbag, and the operator must be equals.
	 * 
	 * @return {@code true} if the query is a direct one,
	 * 			or {@code false} otherwise.
	 */
	public boolean isDirectQuery() {
		if (expressions.size() == 1) {
			ConditionalExpression expression = expressions.get(0);
			
			return expression.getFieldGetter() == FieldValueGetters.JMBAG
				&& expression.getComparisonOperator() == ComparisonOperators.EQUALS;
		}
		
		return false;
	}
	
	/**
	 * Returns the direct query's JMBAG.
	 * 
	 * @return The string which was given in equality comparison
	 * 			in the direct query.
	 * @throws IllegalStateException If query is not direct.
	 */
	public String getQueriedJMBAG() {
		if (isDirectQuery()) {
			return expressions.get(0).getStringLiteral();
		}
		
		throw new IllegalStateException(
			"JMBAG cannot be requested from a non-direct query."
		);
	}
	
	/**
	 * Returns a {@code List} of all conditional expressions from the query.
	 * 
	 * @return Query's conditional expressions.
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}
	
	/**
	 * Method which parses the specified query and generates the list
	 * of corresponding expressions.
	 * 
	 * @throws QueryParserException If the specified query is not valid.
	 */
	private void parse() {
		while (true) {
			parseSingleQuery();
			
			Token token = lexer.nextToken();
			if (token.getType() == TokenType.AND_OPERATOR) {
				continue;
			} else if (token.getType() == TokenType.EOF) {
				break;
			} else {
				throw new QueryParserException(
					"End of query has already been reached."
				);
			}
		}
	}
	
	/**
	 * Parses a single query (until the end of the query or the
	 * AND operator is reached).
	 * 
	 * @throws QueryParserException If the specified query is not valid.
	 */
	private void parseSingleQuery() {
		Token token = lexer.nextToken();
		
		if (token.getType() == TokenType.ATTRIBUTE_NAME) {
			IFieldValueGetter fieldGetter = parseAttributeName(token);
			
			token = lexer.nextToken();
			IComparisonOperator comparisonOperator = parseComparisonOperator(token);
			
			token = lexer.nextToken();
			String stringLiteral = parseStringLiteral(token);
			
			ConditionalExpression expression = new ConditionalExpression(
					fieldGetter, stringLiteral, comparisonOperator
			);
			
			expressions.add(expression);
		} else {
			throw new QueryParserException("Expected attribute token.");
		}
	}
	
	/**
	 * Parses an attribute name token and returns the corresponding
	 * field value getter.
	 * 
	 * @param token The given token.
	 * @return The corresponding field value getter.
	 * @throws QueryParserException If the given token is not a valid
	 * 			attribute name.
	 */
	private IFieldValueGetter parseAttributeName(Token token) {
		String attribute = token.getValue();
		
		if (attribute.equals("jmbag")) {
			return FieldValueGetters.JMBAG;
		} else if (attribute.equals("firstName")) {
			return FieldValueGetters.FIRST_NAME;
		} else if (attribute.equals("lastName")) {
			return FieldValueGetters.LAST_NAME;
		}
		
		throw new QueryParserException("Invalid attribute name.");
	}
	
	/**
	 * Parses a comparison operator token and returns the corresponding
	 * comparison operator.
	 * 
	 * @param token The given token.
	 * @return The corresponding comparison operator.
	 * @throws QueryParserException If the given token is not a valid
	 * 			comparison operator.
	 */
	private IComparisonOperator parseComparisonOperator(Token token) {
		if (token.getType() == TokenType.OPERATOR_LESS) {
			return ComparisonOperators.LESS;
		} else if (token.getType() == TokenType.OPERATOR_LESS_OR_EQUALS) {
			return ComparisonOperators.LESS_OR_EQUALS;
		} else if (token.getType() == TokenType.OPERATOR_GREATER) {
			return ComparisonOperators.GREATER;
		} else if (token.getType() == TokenType.OPERATOR_GREATER_OR_EQUALS) {
			return ComparisonOperators.GREATER_OR_EQUALS;
		} else if (token.getType() == TokenType.OPERATOR_EQUALS) {
			return ComparisonOperators.EQUALS;
		} else if (token.getType() == TokenType.OPERATOR_NOT_EQUALS) {
			return ComparisonOperators.NOT_EQUALS;
		} else if (token.getType() == TokenType.OPERATOR_LIKE) {
			return ComparisonOperators.LIKE;
		}
		
		throw new QueryParserException("Unexpected non-operator token.");
	}
	
	/**
	 * Parses a string literal token and returns the corresponding
	 * string literal.
	 * 
	 * @param token The given token.
	 * @return The corresponding string literal.
	 * @throws QueryParserException If the given token is not a valid
	 * 			string literal.
	 */
	private String parseStringLiteral(Token token) {
		if (token.getType() == TokenType.STRING_LITERAL) {
			return token.getValue();
		}
		
		throw new QueryParserException("Unexpected non-literal token.");
	}
}
