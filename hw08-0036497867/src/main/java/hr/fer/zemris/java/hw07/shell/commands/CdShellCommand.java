package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.InvalidPathException;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.argumentparser.ArgumentParser;

/**
 * Command which, when executed, changes the current directory
 * to the specified directory.
 * 
 * @author Mate Gasparini
 */
public class CdShellCommand implements ShellCommand {
	
	private static final String NAME = "cd";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME + " [DIRECTORY]",
		"[DIRECTORY]	- path to the specified directory (mandatory)",
		"",
		"Changes the current directory to the specified directory."
	);
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		if (size == 1) {
			String directory = argumentList.get(0);
			
			try {
				env.setCurrentDirectory(env.getCurrentDirectory().resolve(directory));
			} catch (InvalidPathException ex) {
				env.writeln(directory + ": invalid path.");
			} catch (IllegalArgumentException ex) {
				env.writeln(ex.getMessage());
			}
		} else {
			env.writeln(NAME + ": expected 1 argument.");
		}
		
		return null;
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
