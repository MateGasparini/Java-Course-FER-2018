package hr.fer.zemris.java.hw16.trazilica.commands;

import hr.fer.zemris.java.hw16.trazilica.Konzola;

/**
 * Interface which models executable commands used by the
 * {@link Konzola} console application.
 * 
 * @author Mate Gasparini
 */
public interface ICommand {
	
	/**
	 * Executes the command with arguments specified in the given string array.
	 * 
	 * @param commandParts String array containing parts of the command (split by whitespace).
	 * 			First element should be the command name and is mandatory.
	 */
	void processCommand(String[] commandParts);
}
