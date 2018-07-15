package hr.fer.zemris.java.hw16.trazilica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import hr.fer.zemris.java.hw16.trazilica.commands.ICommand;
import hr.fer.zemris.java.hw16.trazilica.commands.QueryCommand;
import hr.fer.zemris.java.hw16.trazilica.commands.ResultsCommand;
import hr.fer.zemris.java.hw16.trazilica.commands.TypeCommand;
import hr.fer.zemris.java.hw16.trazilica.documents.TFIDFCollection;
import hr.fer.zemris.java.hw16.trazilica.documents.TFIDFCollectionProvider;
import hr.fer.zemris.java.hw16.trazilica.vocabulary.Vocabulary;
import hr.fer.zemris.java.hw16.trazilica.vocabulary.VocabularyProvider;

/**
 * A console application which provides basic command execution used for
 * text analysis of the directory specified by the command line arguments.<br>
 * It starts by building a {@link Vocabulary} and the {@link TFIDFCollection}.
 * 
 * @author Mate Gasparini
 */
public class Konzola {
	
	/** Prompt for the user to enter the next command. */
	private static final String INPUT_PROMPT = "\nEnter command > ";
	
	/** Command used for application termination. */
	private static final String EXIT = "exit";
	
	/** {@code Map} containing command names mapped to their corresponding
	 * {@link ICommand} implementations. */
	private static final Map<String, ICommand> COMMANDS = Map.ofEntries(
		Map.entry("query", new QueryCommand()),
		Map.entry("type", new TypeCommand()),
		Map.entry("results", new ResultsCommand())
	);
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments. Expected a single argument - path to the
	 * 			articles root directory.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected one argument - path to articles directory.");
			return;
		}
		
		Vocabulary vocabulary = VocabularyProvider.getVocabulary();
		String articlesPath = args[0];
		try {
			vocabulary.loadWords(articlesPath);
			TFIDFCollectionProvider.getCollection().loadVectors(articlesPath);
		} catch (IOException ex) {
			System.out.println(articlesPath + ": invalid path to articles directory.");
			return;
		}
		System.out.println("Veličina rječnika je " + vocabulary.getSize() + " riječi.");
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				System.out.print(INPUT_PROMPT);
				String[] parts = reader.readLine().trim().split("\\s+");
				String command = parts[0];
				if (COMMANDS.containsKey(command)) {
					COMMANDS.get(command).processCommand(parts);
				} else if (command.equals(EXIT)) {
					return;
				} else {
					System.out.println("Nepoznata naredba.");
				}
			}
		} catch (IOException ex) {
			System.out.println("An I/O error occurred.");
		}
	}
}
