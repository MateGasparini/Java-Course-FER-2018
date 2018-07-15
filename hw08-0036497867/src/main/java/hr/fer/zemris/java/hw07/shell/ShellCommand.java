package hr.fer.zemris.java.hw07.shell;

import java.util.List;

/**
 * Interface for all shell commands.<br>
 * It specifies that each command them must have a name, a description
 * and an execution method.
 * 
 * @author Mate Gasparini
 */
public interface ShellCommand {
	
	/**
	 * Method which is called when the command with the given arguments
	 * needs to be executed in the given environment.
	 * 
	 * @param env The given environment.
	 * @param arguments The given arguments.
	 * @return The status after execution.
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Returns the name of the command.
	 * 
	 * @return The name of the command.
	 */
	String getCommandName();
	
	/**
	 * Returns a {@code List} of all lines of the command's description.
	 * 
	 * @return The description of the command.
	 */
	List<String> getCommandDescription();
}
