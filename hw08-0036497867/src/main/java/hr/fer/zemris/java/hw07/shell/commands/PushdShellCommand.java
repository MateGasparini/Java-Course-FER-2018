package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.argumentparser.ArgumentParser;

/**
 * Command which, when executed, pushes the current directory on the stack
 * and sets the current directory to the specified directory.
 * 
 * @author Mate Gasparini
 */
public class PushdShellCommand implements ShellCommand {
	
	private static final String NAME = "pushd";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME + " [DIRECTORY]",
		"[DIRECTORY]	- path to the specified directory (mandatory)",
		"",
		"Pushes the current directory on the stack and sets the current directory to the",
		"specified directory."
	);
	
	/**
	 * Key used for accessing the stack of directories.
	 */
	public static final String STACK = "cdstack";
	
	@SuppressWarnings("unchecked")
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		if (size == 1) {
			String directory = argumentList.get(0);
			
			Path path = env.getCurrentDirectory().resolve(directory);
			File file = path.toFile();
			
			if (file.exists()) {
				if (file.isDirectory()) {
					if (env.getSharedData(STACK) == null) {
						env.setSharedData(STACK, new Stack<>());
					}
					
					((Stack<Path>) env.getSharedData(STACK)).push(env.getCurrentDirectory());
					env.setCurrentDirectory(path);
				} else {
					env.writeln(directory + ": not a directory.");
				}
			} else {
				env.writeln(directory + ": does not exist.");
			}
		} else {
			env.writeln(NAME + ": expected 1 argument.");
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
