package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.argumentparser.ArgumentParser;

/**
 * Command which, when executed, deletes all content from the
 * specified directory (recursively.)
 * 
 * @author Mate Gasparini
 */
public class RmtreeShellCommand implements ShellCommand {
	
	private static final String NAME = "rmtree";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME + " [DIRECTORY]",
		"[DIRECTORY]	- path to the specified directory (mandatory)",
		"",
		"Deletes the specified directory's contents (recursively)."
	);
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		if (size == 1) {
			RemoveFileVisitor visitor = new RemoveFileVisitor();
			
			try {
				Path directory = env.getCurrentDirectory().resolve(argumentList.get(0));
				Files.walkFileTree(directory, visitor);
				env.writeln("Tree successfully removed.");
			} catch (IOException ex) {
				env.writeln(argumentList.get(0) + ": invalid argument.");
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
	
	/**
	 * Visitor that deletes every file and directory visited.
	 * 
	 * @author Mate Gašparini
	 */
	private class RemoveFileVisitor extends SimpleFileVisitor<Path> {
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			file.toFile().delete();
			
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			dir.toFile().delete();
			
			return FileVisitResult.CONTINUE;
		}
	}
}
