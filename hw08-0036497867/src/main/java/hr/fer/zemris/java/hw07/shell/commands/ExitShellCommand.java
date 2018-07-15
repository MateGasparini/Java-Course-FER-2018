package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which, when executed, returns a {@code ShellStatus} which causes
 * the program to terminate.
 * 
 * @author Mate Gasparini
 */
public class ExitShellCommand implements ShellCommand {
	
	private static final String NAME = "exit";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME + " [IGNORED]",
		"[IGNORED]	- will be ignored (optional)",
		"",
		"Terminates the current session and exits."
	);
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}
	
	@Override
	public String getCommandName() {
		return NAME;
	}
	
	@Override
	public List<String> getCommandDescription() {
		return COMMAND_DESCRIPTION;
	}
}
