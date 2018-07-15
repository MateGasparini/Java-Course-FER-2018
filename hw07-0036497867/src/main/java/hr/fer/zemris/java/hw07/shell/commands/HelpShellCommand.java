package hr.fer.zemris.java.hw07.shell.commands;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ArgumentParser;

/**
 * Command which, when executed, lists the names of all supported commands
 * or prints the description for a specific command.
 * 
 * @author Mate Gasparini
 */
public class HelpShellCommand implements ShellCommand {
	
	private static final String NAME = "help";
	private static final List<String> COMMAND_DESCRIPTION = new LinkedList<>();
	static {
		String[] commands = new String[] {
			"Usage: " + NAME + " [COMMAND]",
			"[COMMAND]	- name of the specified command (optional)",
			"",
			"If no arguments are given, it lists names of all supported commands.",
			"If supported command name is given, it prints its description."
		};
		
		for (String command : commands) {
			COMMAND_DESCRIPTION.add(command);
		}
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		if (size == 0) {
			env.commands().forEach((k, v) -> env.writeln(k));
		} else if (size == 1) {
			String commandName = argumentList.get(0);
			
			if (env.commands().containsKey(commandName)) {
				ShellCommand command = env.commands().get(commandName);
				
				env.writeln(command.getCommandName());
				command.getCommandDescription().forEach(l -> env.writeln(l));
			} else {
				env.writeln(NAME + ": non-existent command '" + commandName + "'.");
			}
		} else {
			env.writeln(NAME + ": too many arguments.");
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
