package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ArgumentParser;

/**
 * Command which, when executed, creates the appropriate directory structure
 * as specified by the given argument.
 * 
 * @author Mate Gasparini
 */
public class MkdirShellCommand implements ShellCommand {
	
	private static final String NAME = "mkdir";
	private static final List<String> COMMAND_DESCRIPTION = new LinkedList<>();
	static {
		String[] commands = new String[] {
			"Usage: " + NAME + " [DIRECTORY]",
			"[DIRECTORY]	- directory name (mandatory)",
			"",
			"Creates the appropriate directory structure specified by the given argument."
		};
		
		for (String command : commands) {
			COMMAND_DESCRIPTION.add(command);
		}
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		if (size == 1) {
			File dir = new File(arguments);
			
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
