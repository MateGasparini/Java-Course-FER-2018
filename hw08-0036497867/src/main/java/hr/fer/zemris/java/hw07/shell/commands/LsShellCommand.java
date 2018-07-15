package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.argumentparser.ArgumentParser;

/**
 * Command which, when executed, lists the content of the specified directory.
 * 
 * @author Mate Gasparini
 */
public class LsShellCommand implements ShellCommand {
	
	private static final String NAME = "ls";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME + " [DIRECTORY]",
		"[DIRECTORY]	- path to the specified directory (mandatory)",
		"",
		"Lists the content of the specified directory (non-recursively)."
	);
	
	private static final SimpleDateFormat DATE_FORMAT =
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		if (size == 0) {
			env.writeln(NAME + ": expected arguments.");
		} else if (size == 1) {
			String directoryName = argumentList.get(0);
			
			File directory = env.getCurrentDirectory().resolve(directoryName).toFile();
			File[] content = directory.listFiles();
			
			if (content == null) {
				env.writeln(directoryName + ": invalid directory.");
			} else {
				try {
					for (File file : content) {
						env.writeln(getFileInfo(file));
					}
				} catch (IOException ex) {
					env.writeln(NAME + ": error occurred while listing.");
				}
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
	
	/**
	 * Returns a String containing all needed information about the given file.
	 * 
	 * @param file The given file.
	 * @return The corresponding String.
	 * @throws IOException If an I/O error occurs while getting
	 * 			the file's date of creation.
	 */
	private String getFileInfo(File file) throws IOException {
		StringBuilder builder = new StringBuilder();
		
		builder.append(file.isDirectory() ? D : MINUS);
		builder.append(file.canRead() ? R : MINUS);
		builder.append(file.canWrite() ? W : MINUS);
		builder.append(file.canExecute() ? X : MINUS).append(SPACE);
		
		builder.append(String.format(TEN_DIGITS, file.length())).append(SPACE);
		
		builder.append(
			DATE_FORMAT.format(
				new Date(
					Files.getFileAttributeView(
						file.toPath(), BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS
					).readAttributes().creationTime().toMillis()
				)
			)
		).append(SPACE);
		
		builder.append(file.getName());
		
		return builder.toString();
	}
	
	/* STRING CONSTANTS */
	private static final String D = "d";
	private static final String R = "r";
	private static final String W = "w";
	private static final String X = "x";
	private static final String MINUS = "-";
	private static final String SPACE = " ";
	private static final String TEN_DIGITS = "%10d";
}
