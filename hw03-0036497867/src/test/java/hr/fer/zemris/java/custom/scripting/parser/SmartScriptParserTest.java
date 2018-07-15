package hr.fer.zemris.java.custom.scripting.parser;

import static hr.fer.zemris.java.hw03.SmartScriptTester.createOriginalDocumentBody;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;

/**
 * Test class for the <code>SmartScriptParser</code> class.
 * 
 * @author Mate Gasparini
 */
public class SmartScriptParserTest {
	
	@Test
	public void sameDocumentTest() {
		testParserForGivenDocument("document1.txt");
	}
	
	@Test
	public void sameTextOnlyTest() {
		testParserForGivenDocument("textonly.txt");
	}
	
	@Test
	public void sameForOnlyTest() {
		testParserForGivenDocument("foronly.txt");
	}
	
	@Test
	public void sameEchoOnlyTest() {
		testParserForGivenDocument("echoonly.txt");
	}
	
	@Test
	public void sameNestedForTest() {
		testParserForGivenDocument("nestedfor.txt");
	}
	
	/**
	 * Tests the parser on the document specified by the given filepath.
	 * 
	 * @param filepath The given filepath.
	 */
	private void testParserForGivenDocument(String filepath) {
		String docBody = loader(filepath);
		
		assertNotNull("There was a problem loading the file.", docBody);
		
		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		
		String originalDocumentBody = createOriginalDocumentBody(document);
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		assertEqualDocuments(document, document2);
	}
	
	/**
	 * Recursively asserts that the given nodes are of the same type
	 * and have the same number of children nodes.
	 * 
	 * @param node First given node.
	 * @param node2 Second given node.
	 */
	private void assertEqualDocuments(Node node, Node node2) {
		assertEquals(node.getClass(), node2.getClass());
		assertEquals(node.numberOfChildren(), node2.numberOfChildren());
		
		int numberOfChildren = node.numberOfChildren();
		for (int i = 0; i < numberOfChildren; i ++) {
			assertEqualDocuments(node.getChild(i), node2.getChild(i));
		}
	}
	
	/**
	 * Takes a filepath and returns the content of the file as a string.
	 * 
	 * @param filename Name of the file.
	 * @return String filled with file content or
	 * 			null if a problem was encountered.
	 */
	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		try (InputStream is = this.getClass()
				.getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			
			while (true) {
				int read = is.read(buffer);
				if (read < 1) break;
				bos.write(buffer, 0, read);
			}
			
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException | NullPointerException ex) {
			return null;
		}
	}
}
