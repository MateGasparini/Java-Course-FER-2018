package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which, when executed, writes the current directory
 * as an absolute path to the given environment.
 * 
 * @author Mate Gasparini
 */
public class PwdShellCommand implements ShellCommand {
	
	private static final String NAME = "pwd";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME,
		"",
		"Writes the current directory as an absolute path."
	);
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			env.writeln(env.getCurrentDirectory().toString());
		} else {
			env.writeln(NAME + ": takes no arguments.");
		}
		
		return ShellStatus.CONTINUE;
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
