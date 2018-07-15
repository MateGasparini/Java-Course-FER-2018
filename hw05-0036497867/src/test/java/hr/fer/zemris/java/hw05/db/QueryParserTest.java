package hr.fer.zemris.java.hw05.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testing class for the {@code QueryParser} class.
 * 
 * @author Mate Gasparini
 */
public class QueryParserTest {
	
	@Test
	public void directQueryTest() {
		QueryParser parser = new QueryParser("  jmbag    =  \"0123456789\"  ");
		
		assertTrue(parser.isDirectQuery());
		assertEquals("0123456789", parser.getQueriedJMBAG());
		assertEquals(1, parser.getQuery().size());
	}
	
	@Test
	public void directQueryWithoutWhitespacesTest() {
		QueryParser parser = new QueryParser("jmbag=\"0123456789\"");
		
		assertTrue(parser.isDirectQuery());
		assertEquals("0123456789", parser.getQueriedJMBAG());
		assertEquals(1, parser.getQuery().size());
	}
	
	@Test
	public void complexQueryTest() {
		QueryParser parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		
		assertFalse(parser.isDirectQuery());
		assertEquals(2, parser.getQuery().size());
	}
	
	@Test(expected=IllegalStateException.class)
	public void complexQueryExceptionThrownTest() {
		QueryParser parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		
		parser.getQueriedJMBAG();
	}
}
