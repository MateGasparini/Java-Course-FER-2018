package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.argumentparser.ArgumentParser;

/**
 * Command which, when executed, creates the appropriate directory structure
 * as specified by the given argument.
 * 
 * @author Mate Gasparini
 */
public class MkdirShellCommand implements ShellCommand {
	
	private static final String NAME = "mkdir";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME + " [DIRECTORY]",
		"[DIRECTORY]	- directory name (mandatory)",
		"",
		"Creates the appropriate directory structure specified by the given argument."
	);
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		if (size == 1) {
			File dir = env.getCurrentDirectory().resolve(argumentList.get(0)).toFile();
			
			if (dir.isDirectory()) {
				env.writeln(dir + ": directory structure already exists.");
			} else {
				if (dir.mkdirs()) {
					env.writeln("Directory successfully created.");
				} else {
					env.writeln("Directory not created.");
				}
			}
		} else {
			env.writeln(NAME + ": expected a single argument.");
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
