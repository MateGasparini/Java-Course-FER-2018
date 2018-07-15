package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ArgumentParser;

/**
 * Command which, when executed, copies the file from the source path
 * to the destination path.
 * 
 * @author Mate Gasparini
 */
public class CopyShellCommand implements ShellCommand {
	
	private static final String NAME = "copy";
	private static final List<String> COMMAND_DESCRIPTION = new LinkedList<>();
	static {
		String[] commands = new String[] {
			"Usage: " + NAME + " [SOURCE] [DESTINATION]",
			"[SOURCE]	- path to the source file (mandatory)",
			"[DESTINATION]	- path to the destination file/folder (mandatory)",
			"",
			"Copies the source file content into the destination file/folder.",
			"If the destination exists and is a file, it will be overwritten.",
			"If the destination exists and is a folder, the source file will be copied into",
			"it.",
			"If the destination does not exist, it will be created as a file with copied",
			"source content."
		};
		
		for (String command : commands) {
			COMMAND_DESCRIPTION.add(command);
		}
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		if (size == 2) {
			String sourceName = argumentList.get(0);
			String destinationName = argumentList.get(1);
			
			if (sourceName.equals(destinationName)) {
				env.writeln(
					NAME + ": source and destination must not be equal."
				);
				return ShellStatus.CONTINUE;
			}
			
			File source = new File(sourceName);
			File destination = new File(destinationName);
			
			if (source.isFile()) {
				try {
					if (destination.isFile()) {
						if (allowedOverwriting(env, destination.toString())) {
							copy(source, destination);
							
							env.writeln(
								destination + " successfully overwritten."
							);
						}
					} else if (destination.isDirectory()) {
						copyToDirectory(source, destination);
						
						env.writeln(
							source + " successfully moved to " + destination
						);
					} else {
						copy(source, destination);
						
						env.writeln(destination + " successfully created.");
					}
				} catch (IOException ex) {
					env.writeln("An error occured while copying.");
				}
			} else {
				if (source.exists()) {
					env.writeln(source + ": not a file.");
				} else {
					env.writeln(source + ": file does not exist.");
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
	 * Asks the user if overwriting of the given file is allowed,
	 * and returns true if it is.
	 * 
	 * @param env The given environment through which
	 * 			the communication is made possible.
	 * @param path The path of the file which might be overwritten.
	 * @return {@code true} if the users allows overwriting,
	 * 			and {@code false} otherwise.
	 */
	private boolean allowedOverwriting(Environment env, String path) {
		env.writeln(path + ": file already exists.");
		env.writeln("After this operation it will be overwritten.");
		env.write("Do you want to continue? [Y/n] ");
		
		String input = env.readLine();
		if (input.toLowerCase().equals("y")) {
			return true;
		} else if (input.toLowerCase().equals("n")) {
			return false;
		}
		
		env.writeln("Invalid input. Aborting.");
		return false;
	}
	
	/**
	 * Copies the contents of the source file to the specified destination file-
	 * 
	 * @param source The given source file.
	 * @param destination The specified destination file.
	 * @throws IOException If an error occurred while reading or writing.
	 */
	private void copy(File source, File destination) throws IOException {
		try (BufferedInputStream in = new BufferedInputStream(
				new FileInputStream(source));
			BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(destination))) {
			
			byte[] buffer = new byte[4096];
			while (true) {
				int numberOfRead = in.read(buffer);
				
				if (numberOfRead < 1) {
					out.flush();
					break;
				}
				
				out.write(buffer, 0, numberOfRead);
			}
		} catch (IOException ex) {
			throw ex;
		}
	}
	
	/**
	 * Copies the contents of the given file to the specified directory.
	 * 
	 * @param source The given file.
	 * @param directory The specified directory.
	 * @throws IOException If an error occurred while reading or writing.
	 */
	private void copyToDirectory(File source, File directory) throws IOException {
		File destination = new File(directory.getPath(), source.getName());
		
		copy(source, destination);
	}
}
