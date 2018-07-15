package hr.fer.zemris.java.hw16.trazilica.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.hw16.trazilica.documents.Result;
import hr.fer.zemris.java.hw16.trazilica.documents.ResultsProvider;

/**
 * {@link ICommand} which executes the 'type' command by
 * writing the contents of the {@link Result} file with the index specified
 * by the (single) expected argument.<br>
 * It is therefore expected to first execute the {@link QueryCommand}
 * and then this command.
 * 
 * @author Mate Gasparini
 */
public class TypeCommand implements ICommand {
	
	@Override
	public void processCommand(String[] commandParts) {
		if (commandParts.length != 2) {
			System.out.println("Expected one argument after 'type' command.");
			return;
		}
		
		int index;
		try {
			index = Integer.parseInt(commandParts[1]);
		} catch (NumberFormatException ex) {
			System.out.println("Expected a result index after 'type' command.");
			return;
		}
		
		List<Result> results = ResultsProvider.getResults();
		if (results == null) {
			System.out.println("Expected 'query' command before calling 'type'.");
			return;
		}
		
		if (index < 0 || index >= results.size()) {
			System.out.println("Index is out of bounds [0, " + results.size() + ">.");
			return;
		}
		
		printFileContent(results.get(index).getFilePath());
	}
	
	/**
	 * Prints all lines from the file specified by the given path.
	 * 
	 * @param path The given path.
	 */
	private void printFileContent(Path path) {
		try {
			List<String> lines = Files.readAllLines(path);
			printLine();
			System.out.println("Dokument: " + path.toString());
			printLine();
			lines.forEach(System.out::println);
			printLine();
		} catch (IOException ex) {
			System.out.println("An I/O error occurred.");
		}
	}
	
	/**
	 * Prints a line of dashes (as specified in the homework).
	 */
	private void printLine() {
		for (int i = 0; i < 64; i ++) {
			System.out.print('-');
		}
		System.out.print('\n');
	}
}
