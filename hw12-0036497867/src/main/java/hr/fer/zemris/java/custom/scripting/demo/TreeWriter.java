package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Simple program which expects one command line argument - path to a valid
 * smart script (.smscr) file, parses it using the {@link SmartScriptParser}.
 * From the generated {@link DocumentNode}, an approximation of the original
 * file content is reproduced and written to the standard output.
 * 
 * @author Mate Gasparini
 */
public class TreeWriter {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Expected only 1 argument,
	 * 			which must be a smart script file name.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected 1 argument - smart script file name");
			return;
		}
		
		String fileName = args[0];
		if (!fileName.toLowerCase().endsWith(".smscr")) {
			System.out.println(
				"The given file type is not supported. Expected a '.smscr' file extension."
			);
			return;
		}
		
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException ex) {
			System.out.println("File does not exist or is not readable.");
			return;
		}
		
		try {
			SmartScriptParser parser = new SmartScriptParser(docBody);
			WriterVisitor visitor = new WriterVisitor();
			parser.getDocumentNode().accept(visitor);
		} catch (SmartScriptParserException ex) {
			System.out.println("File not parseable.\n" + ex.getMessage());
		}
	}
	
	/**
	 * {@link INodeVisitor} implementation which visits the specified tree of nodes,
	 * and for each node prints the original smart script content to the stdout.
	 * 
	 * @author Mate Gasparini
	 */
	private static class WriterVisitor implements INodeVisitor {
		
		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node);
		}
		
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node);
			visitChildren(node);
			System.out.print("{$ END $}");
		}
		
		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node);
		}
		
		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
		}
		
		/**
		 * Visits all children nodes of the given node (if there are any).
		 * 
		 * @param node The given node.
		 */
		private void visitChildren(Node node) {
			int numberOfChildren = node.numberOfChildren();
			for (int i = 0; i < numberOfChildren; i ++) {
				node.getChild(i).accept(this);
			}
		}
	}
}
