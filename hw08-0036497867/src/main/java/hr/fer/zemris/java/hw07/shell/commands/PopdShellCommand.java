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
 * Command which, when executed, pops one directory from the stack
 * and sets it as the current directory.
 * 
 * @author Mate Gasparini
 */
public class PopdShellCommand implements ShellCommand {
	
	private static final String NAME = "popd";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME,
		"",
		"Pops one directory from the stack and sets it as the current directory."
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
					Path popped = stack.pop();
					
					if (popped.toFile().isDirectory()) {
						env.setCurrentDirectory(popped);
					} else {
						env.writeln(popped + ": directory does not exist anymore.");
					}
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
