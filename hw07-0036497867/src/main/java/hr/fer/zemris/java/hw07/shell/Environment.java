package hr.fer.zemris.java.hw07.shell;

import java.util.SortedMap;

/**
 * Interface which allows commands to communicate with the user.<br>
 * It also provides methods for modifying the shell special symbols,
 * as well as getting the map of all commands.
 * 
 * @author Mate Gasparini
 */
public interface Environment {
	
	/**
	 * Reads a line from the user.
	 * 
	 * @return The user's input.
	 * @throws ShellIOException If there was problem reading the user's input.
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes a String to the user.
	 * 
	 * @param text The given String.
	 * @throws ShellIOException If there was problem writing to the user.
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes a line to the user.
	 * 
	 * @param text The given String.
	 * @throws ShellIOException If there was problem writing to the user.
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Returns a sorted map of all commands.
	 * 
	 * @return A sorted map of all commands.
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Returns the symbol for multilined commands.
	 * 
	 * @return The multiline symbol.
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the multiline symbol to the specified symbol.
	 * 
	 * @param symbol The specified symbol.
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Returns the symbol for user prompting.
	 * 
	 * @return The prompt symbol.
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the prompt symbol to the specified symbol.
	 * 
	 * @param symbol The specified symbol.
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Returns the symbol for breaking the line.
	 * 
	 * @return The more lines symbol.
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the more lines symbol to the specified symbol.
	 * 
	 * @param symbol The specified symbol.
	 */
	void setMorelinesSymbol(Character symbol);
}
