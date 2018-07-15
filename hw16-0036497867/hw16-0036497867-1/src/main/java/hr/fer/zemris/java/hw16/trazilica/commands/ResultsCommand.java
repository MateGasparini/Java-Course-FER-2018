package hr.fer.zemris.java.hw16.trazilica.commands;

import java.util.List;

import hr.fer.zemris.java.hw16.trazilica.documents.Result;
import hr.fer.zemris.java.hw16.trazilica.documents.ResultsProvider;

/**
 * {@link ICommand} which executes the 'results' command by
 * printing the last 'query' command {@link Result}s on the standard output.<br>
 * It is therefore expected to first execute the {@link QueryCommand}
 * and then this command.<br>
 * It does not expect any arguments.
 * 
 * @author Mate Gasparini
 */
public class ResultsCommand implements ICommand {
	
	@Override
	public void processCommand(String[] commandParts) {
		if (commandParts.length > 1) {
			System.out.println("Unexpected arguments after 'results' command.");
			return;
		}
		
		List<Result> results = ResultsProvider.getResults();
		if (results == null) {
			System.out.println("Expected 'query' command before calling 'results'.");
			return;
		}
		for (int i = 0, size = results.size(); i < size; i ++) {
			System.out.format("[%2d] %s\n", i, results.get(i).toString());
		}
	}
}
