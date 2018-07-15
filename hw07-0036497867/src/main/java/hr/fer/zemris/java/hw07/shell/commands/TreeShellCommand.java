package hr.fer.zemris.java.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which, when executed, writes a tree structure for the directory
 * specified in the argument.
 * 
 * @author Mate Gasparini
 */
public class TreeShellCommand implements ShellCommand {
	
	private static final String NAME = "tree";
	private static final List<String> COMMAND_DESCRIPTION = new LinkedList<>();
	static {
		String[] commands = new String[] {
			"Usage: " + NAME + " [DIRECTORY]",
			"[DIRECTORY]	- path to the specified directory (mandatory)",
			"",
			"Writes a tree structure for the specified directory (recursively)."
		};
		
		for (String command : commands) {
			COMMAND_DESCRIPTION.add(command);
		}
	}
	
	/**
	 * Environment used for writing to the user.
	 */
	private Environment env;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		this.env = env;
		
		if (arguments.isEmpty()) {
			env.writeln(NAME + ": expected an argument.");
		} else {
			MyFileVisitor visitor = new MyFileVisitor();
			
			try {
				Files.walkFileTree(Paths.get(arguments), visitor);
			} catch (IOException e) {
				env.writeln(arguments + ": invalid argument.");
			}
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
	 * A file visitor used for printing the appropriate tree structure
	 * of the directory.
	 * 
	 * @author Mate Gasparini
	 */
	private class MyFileVisitor extends SimpleFileVisitor<Path> {
		
		/**
		 * Current visited directories depth.
		 */
		private int depth;
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			depth ++;
			printOneFile(dir);
			
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			printOneFile(file);
			
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			depth --;
			
			return FileVisitResult.CONTINUE;
		}
		
		/**
		 * Prints the appropriate number of whitespaces and the name of the given file.
		 * 
		 * @param file The given file.
		 */
		private void printOneFile(Path file) {
			for (int i = 0; i < depth; i ++) {
				env.write(DOUBLE_SPACE);
			}
			env.writeln(file.getFileName().toString());
		}
		
		/* STRING CONSTANTS */
		private static final String DOUBLE_SPACE = "  ";
	}
}
