package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which, when executed, lists all directory paths
 * that are currently on the stack.
 * 
 * @author Mate Gasparini
 */
public class ListdShellCommand implements ShellCommand {
	
	private static final String NAME = "listd";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME,
		"",
		"Lists all directory paths pushed on the stack."
	);
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData(PushdShellCommand.STACK);
			
			if (stack != null) {
				if (stack.isEmpty()) {
					env.writeln(NAME + ": stack is empty.");
				} else {
					Stack<Path> copy = new Stack<>();
					
					while (!stack.isEmpty()) {
						Path path = stack.pop();
						env.writeln(path.toString());
						copy.push(path);
					}
					
					while (!copy.isEmpty()) {
						stack.push(copy.pop());
					}
				}
			} else {
				env.writeln(NAME + ": stack is empty.");
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
