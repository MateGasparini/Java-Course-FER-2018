package hr.fer.zemris.java.hw07.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.argumentparser.ArgumentParser;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilder;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderInformation;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderParser;
import hr.fer.zemris.java.hw07.shell.namebuilder.NameBuilderParserException;

/**
 * Command which, when executed, renames and moves files
 * using a specified mask for filtering and grouping.<br>
 * Actually contains multiple subcommands that allow the user
 * to see the results of execution before actual execution.
 * 
 * @author Mate Gasparini
 */
public class MassrenameShellCommand implements ShellCommand {
	
	private static final String NAME = "massrename";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME + " [SOURCE] [DESTINATION] [COMMAND] [MASK] [OTHER]",
		"[SOURCE]	- path to the specified source directory (mandatory)",
		"[DESTINATION]	- path to the specified destination directory (mandatory)",
		"[COMMAND]	- one of the following: filter, groups, show, execute (mandatory)",
		"[MASK]	- regular expression that selects files from the source directory",
		"		(mandatory)",
		"[RENAME]	- renaming expression (optional)",
		"",
		"Renames (as specified by the renaming expression) and moves files selected by",
		"the specified mask from the source directory to the destination directory.",
		"As this can be sometimes dangerous, there are subcommands that allow the user",
		"to gradually see the results of execution before actually executing."
	);
	
	/**
	 * The given environment used for communication with the user.
	 */
	private Environment env;
	
	/**
	 * Path of the specified source directory.
	 */
	private Path source;
	/**
	 * Path of the specified destination directory.
	 */
	private Path destination;
	/**
	 * Pattern compiled from the specified mask.
	 */
	private Pattern maskPattern;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		this.env = env;
		
		List<String> argumentList = new ArgumentParser(arguments, false).getArguments();
		int size = argumentList.size();
		
		if (size == 4 || size == 5) {
			String sourceName = argumentList.get(0);
			String destinationName = argumentList.get(1);
			String maskString = argumentList.get(3);
			
			try {
				source = Paths.get(sourceName);
				if (!source.toFile().isDirectory()) {
					env.writeln(sourceName + ": not a valid directory.");
					return ShellStatus.CONTINUE;
				}
				
				destination = Paths.get(destinationName);
				if (!destination.toFile().isDirectory()) {
					env.writeln(destinationName + ": not a valid directory.");
					return ShellStatus.CONTINUE;
				}
				
				maskPattern = Pattern.compile(maskString);
				
				String command = argumentList.get(2);
				if (command.equals(FILTER)) {
					filter().forEach(System.out::println);
				} else if (command.equals(GROUPS)) {
					groups();
				} else if (command.equals(SHOW)) {
					if (size == 5) {
						show(argumentList.get(4)).forEach(
							pair -> env.writeln(pair.showFormat())
						);
					} else {
						env.writeln(
							NAME + ": expected 5 arguments when using " + SHOW + "."
						);
					}
				} else if (command.equals(EXECUTE)) {
					if (size == 5) {
						execute(argumentList.get(4));
					} else {
						env.writeln(
							NAME + ": expected 5 arguments when using " + EXECUTE + "."
						);
					}
				} else {
					env.writeln(command + ": invalid subcommand.");
				}
			} catch (InvalidPathException ex) {
				env.writeln("Invalid directory path(s).");
			} catch (PatternSyntaxException ex) {
				env.writeln(maskString + ": invalid regex.");
			}
		} else {
			env.writeln(NAME + ": expected 4 or 5 arguments.");
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
	 * Filters the file names from the source directory using
	 * the specified mask and returns the result as a List.
	 * 
	 * @return A List of filtered file names.
	 */
	private List<String> filter() {
		List<String> filtered = new LinkedList<>();
		
		File[] children = source.toFile().listFiles();
		for (File child : children) {
			String fileName = child.getName();
			
			if (maskPattern.matcher(fileName).matches()) {
				filtered.add(fileName);
			}
		}
		
		return filtered;
	}
	
	/**
	 * Prints the groups for each of the filtered source directory
	 * file names.
	 */
	private void groups() {
		List<String> filtered = filter();
		
		for (String f : filtered) {
			Matcher matcher = maskPattern.matcher(f);
			if (!matcher.matches()) {
				continue;
			}
			
			printGroups(matcher);
		}
	}
	
	/**
	 * Builds a List of name pairs, each containing the original file name
	 * from the source directory and the new name of the file that should be
	 * moved to the destination directory.
	 * 
	 * @param expression The specified renaming expression.
	 * @return List of old and new file name pairs.
	 */
	private List<NamePair> show(String expression) {
		List<NamePair> oldNewNames = new LinkedList<>();
		
		try {
			NameBuilderParser parser = new NameBuilderParser(expression);
			NameBuilder builder = parser.getNameBuilder();
			
			File[] children = source.toFile().listFiles();
			for (File file : children) {
				Matcher matcher = maskPattern.matcher(file.getName());
				if (!matcher.matches()) {
					continue;
				}
				
				NameBuilderInfo info = new NameBuilderInformation(matcher);
				builder.execute(info);
				
				String oldName = file.getName();
				String newName = info.getStringBuilder().toString();
				
				oldNewNames.add(new NamePair(oldName, newName));
			}
		} catch (NameBuilderParserException ex) {
			env.writeln(ex.getMessage());
		}
		
		return oldNewNames;
	}
	
	/**
	 * Actually moves/renames the files as specified
	 * in the {@link MassrenameShellCommand#show(String)} method.<br>
	 * Also, writes the name changes to the environment.
	 * 
	 * @param expression The specified renaming expression.
	 */
	private void execute(String expression) {
		List<NamePair> oldNewNames = show(expression);
		
		for (NamePair pair : oldNewNames) {
			Path from = source.resolve(pair.oldName);
			Path to = destination.resolve(pair.newName);
			
			try {
				Files.move(from, to);
				env.writeln(from + ARROW + to);
			} catch (Exception ex) {
				env.writeln("Error moving: " + from + ARROW + to);
			}
		}
	}
	
	/**
	 * Writes all of the given matcher groups to the environment.
	 * 
	 * @param matcher The given matcher.
	 */
	private void printGroups(Matcher matcher) {
		env.write(matcher.group(0));
		for (int i = 0, count = matcher.groupCount(); i <= count; i ++) {
			env.write(String.format(" %d: %s", i, matcher.group(i)));
		}
		env.writeln("");
	}
	
	/**
	 * Class that represents a pair of old and new file name.
	 * 
	 * @author Mate Gasparini
	 */
	private static class NamePair {
		
		/**
		 * Original file name.
		 */
		private String oldName;
		/**
		 * Renamed file name.
		 */
		private String newName;
		
		/**
		 * Constructor specifiying the old and new name.
		 * 
		 * @param oldName The specified old name.
		 * @param newName The specified new name.
		 */
		public NamePair(String oldName, String newName) {
			this.oldName = oldName;
			this.newName = newName;
		}
		
		/**
		 * Returns a String which shows how the name will be changed
		 * for the given pair.
		 * 
		 * @return String containing the old name, an arrow and the new name.
		 */
		public String showFormat() {
			StringBuilder builder = new StringBuilder();
			
			builder.append(oldName);
			builder.append(ARROW);
			builder.append(newName);
			
			return builder.toString();
		}
	}
	
	/* STRING CONSTANTS */
	private static final String FILTER = "filter";
	private static final String GROUPS = "groups";
	private static final String SHOW = "show";
	private static final String EXECUTE = "execute";
	
	private static final String ARROW = " => ";
}
