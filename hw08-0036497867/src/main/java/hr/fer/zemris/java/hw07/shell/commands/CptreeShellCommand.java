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
 * Command which, when executed, recursively copies
 * the source directory tree to the destination directory.
 * 
 * @author Mate Gasparini
 */
public class CptreeShellCommand implements ShellCommand {
	
	private static final String NAME = "cptree";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME + " [SOURCE] [DESTINATION]",
		"[SOURCE]	- path to the specified source directory (mandatory)",
		"[DESTINATION]	- path to the specified destination directory (mandatory)",
		"",
		"Recursively copies the source directory contents to the destination."
	);
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		if (size == 2) {
			String sourceName = argumentList.get(0);
			String destinationName = argumentList.get(1);
			
			Path source = env.getCurrentDirectory().resolve(sourceName);
			Path destination = env.getCurrentDirectory().resolve(destinationName);
			Path destinationParent = destination.getParent();
			
			if (!Files.exists(source)) {
				env.writeln(sourceName + ": does not exist.");
			} else if (!Files.isDirectory(source)) {
				env.writeln(sourceName + ": is not a directory.");
			} else {
				try {
					if (Files.exists(destination)) {
						// Copy to destination.
						CopyFileVisitor visitor = new CopyFileVisitor(destination);
						Files.walkFileTree(source, visitor);
						env.writeln("Tree successfully copied to " + destination + ".");
					} else {
						if (destinationParent != null && Files.exists(destinationParent)) {
							// Copy to destination parent.
							Files.createDirectory(destination);
							CopyFileVisitor visitor = new CopyFileVisitor(destination);
							Files.walkFileTree(source, visitor);
							env.writeln("Tree successfully copied to " + destination + ".");
						} else {
							env.writeln(destinationName + ": does not exist.");
						}
					}
				} catch (IOException ex) {
					env.writeln(
						NAME + ": an error occurred while visiting the source tree."
					);
				}
			}
		} else {
			env.writeln(NAME + ": expected 2 arguments.");
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
	 * Visits some source directory tree, and copies
	 * all its content to the specified destination.
	 * 
	 * @author Mate Gasparini
	 */
	private class CopyFileVisitor extends SimpleFileVisitor<Path> {
		
		/**
		 * Path of the current destination directory.
		 */
		private Path currentDestination;
		
		/**
		 * Constructor specifying the destination directory.
		 * 
		 * @param destinationDirectory The specified destination directory.
		 */
		public CopyFileVisitor(Path destinationDirectory) {
			currentDestination = destinationDirectory;
		}
		
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			currentDestination = currentDestination.resolve(dir.getFileName());
			
			if (Files.exists(currentDestination)) {
				return FileVisitResult.SKIP_SUBTREE;
			}
			
			Files.createDirectory(currentDestination);
			
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			Files.copy(file, currentDestination.resolve(file.getFileName()));
			
			return FileVisitResult.CONTINUE;
		}
		
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			currentDestination = currentDestination.getParent();
			
			return FileVisitResult.CONTINUE;
		}
	}
}
