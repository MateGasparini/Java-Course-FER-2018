package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Test class for the <code>Lexer</code> class from the same package.
 * 
 * @author Mate Gasparini
 */
public class LexerTest {
	
	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull(lexer.nextToken());
	}
	
	@Test(expected=NullPointerException.class)
	public void testNullInput() {
		new Lexer(null);
	}
	
	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testGetReturnsLastNext() {
		Lexer lexer = new Lexer("");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken());
		assertEquals(token, lexer.getToken());
	}
	
	@Test(expected=LexerException.class)
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");
		
		lexer.nextToken();
		lexer.nextToken();
	}
	
	@Test
	public void testTextContent() {
		Lexer lexer = new Lexer("\tOvo je tekst. \n");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		assertEquals("\tOvo je tekst. \n", lexer.getToken().getValue());
	}
	
	@Test
	public void testTagBoundsContent() {
		Lexer lexer = new Lexer("{$ \t $}");
		
		assertEquals(TokenType.TAG_OPENED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.TAG_CLOSED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TEXT);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testEqualsContent() {
		Lexer lexer = new Lexer("={$ = $}");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		assertEquals("=", lexer.getToken().getValue());
		assertEquals(TokenType.TAG_OPENED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.EQUALS_SIGN, lexer.nextToken().getType());
		assertEquals(TokenType.TAG_CLOSED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TEXT);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testVariableContent() {
		Lexer lexer = new Lexer("var{$ var $}");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		assertEquals("var", lexer.getToken().getValue());
		assertEquals(TokenType.TAG_OPENED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.VARIABLE_NAME, lexer.nextToken().getType());
		assertEquals("var", lexer.getToken().getValue());
		assertEquals(TokenType.TAG_CLOSED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TEXT);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testFunctionContent() {
		Lexer lexer = new Lexer("@fun{$ @fun $}");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		assertEquals("@fun", lexer.getToken().getValue());
		assertEquals(TokenType.TAG_OPENED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.FUNCTION_NAME, lexer.nextToken().getType());
		assertEquals("fun", lexer.getToken().getValue());
		assertEquals(TokenType.TAG_CLOSED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TEXT);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testOperatorContent() {
		Lexer lexer = new Lexer("*/{$ */ $}");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		assertEquals("*/", lexer.getToken().getValue());
		assertEquals(TokenType.TAG_OPENED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals('*', lexer.getToken().getValue());
		assertEquals(TokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals('/', lexer.getToken().getValue());
		assertEquals(TokenType.TAG_CLOSED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TEXT);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testIntegerContent() {
		Lexer lexer = new Lexer("555{$ 555 $}");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		assertEquals("555", lexer.getToken().getValue());
		assertEquals(TokenType.TAG_OPENED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.INTEGER_CONSTANT, lexer.nextToken().getType());
		assertEquals(555, lexer.getToken().getValue());
		assertEquals(TokenType.TAG_CLOSED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TEXT);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testDoubleContent() {
		Lexer lexer = new Lexer("1.1{$ 1.2 1. 0.9 $}");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		assertEquals("1.1", lexer.getToken().getValue());
		assertEquals(TokenType.TAG_OPENED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.DOUBLE_CONSTANT, lexer.nextToken().getType());
		assertEquals(1.2, lexer.getToken().getValue());
		assertEquals(TokenType.DOUBLE_CONSTANT, lexer.nextToken().getType());
		assertEquals(1.0, lexer.getToken().getValue());
		assertEquals(TokenType.DOUBLE_CONSTANT, lexer.nextToken().getType());
		assertEquals(0.9, lexer.getToken().getValue());
		assertEquals(TokenType.TAG_CLOSED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TEXT);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testStringContent() {
		Lexer lexer = new Lexer("\"a\"a\\\\a {$ \"ovo\\\\je \\\"string.\" $}");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		assertEquals("\"a\"a\\a ", lexer.getToken().getValue());
		assertEquals(TokenType.TAG_OPENED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TAG);
		
		assertEquals(TokenType.STRING, lexer.nextToken().getType());
		assertEquals("ovo\\je \"string.", lexer.getToken().getValue());
		assertEquals(TokenType.TAG_CLOSED, lexer.nextToken().getType());
		
		lexer.setState(LexerState.TEXT);
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testTextEscape() {
		Lexer lexer = new Lexer("\\{$ \t $}");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType());
		assertEquals("{$ \t $}", lexer.getToken().getValue());
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
}
