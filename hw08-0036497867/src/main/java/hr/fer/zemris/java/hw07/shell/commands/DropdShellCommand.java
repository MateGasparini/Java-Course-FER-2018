package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which, when executed, pops one directory from the stack.
 * 
 * @author Mate Gasparini
 */
public class DropdShellCommand implements ShellCommand {
	
	private static final String NAME = "dropd";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME,
		"",
		"Pops one directory from the stack."
	);
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData(PushdShellCommand.STACK);
			
			if (stack == null) {
				env.writeln(NAME + ": stack is empty.");
			} else {
				try {
					stack.pop();
				} catch (EmptyStackException ex) {
					env.writeln(NAME + ": stack is empty.");
				}
			}
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
