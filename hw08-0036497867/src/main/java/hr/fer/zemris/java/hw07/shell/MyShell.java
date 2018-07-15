package hr.fer.zemris.java.hw07.shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw07.shell.argumentparser.ArgumentParserException;
import hr.fer.zemris.java.hw07.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.CptreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.RmtreeShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw07.shell.commands.TreeShellCommand;

/**
 * Program which simulates a command-line. It supports all commands from
 * the {@code hr.fer.zemris.java.hw07.shell.commands} package.<br>
 * The user is repeatedly prompted for input and, if the given input is
 * a valid command, the command is executed.<br>
 * It is terminated using the {@code ExitShellCommand}, or if a
 * {@link ShellIOException} occured.
 * 
 * @author Mate Gasparini
 */
public class MyShell {
	
	/**
	 * Welcome message shown when the program starts.
	 */
	private static final String WELCOME = "Welcome to MyShell v %s%n";
	/**
	 * Version of the program.
	 */
	private static final String VERSION = "1.0";
	
	/**
	 * Reader from stdin.
	 */
	private static BufferedReader reader =
			new BufferedReader(new InputStreamReader(System.in));
	/**
	 * The environment used in this program.
	 */
	private static Environment environment;
	
	/**
	 * Map of all commands sorted by their names.
	 */
	private static SortedMap<String, ShellCommand> commands = new TreeMap<>();
	static {
		commands.put("cat", new CatShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("cptree", new CptreeShellCommand());
		commands.put("dropd", new DropdShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("massrename", new MassrenameShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("pwd", new PwdShellCommand());
		commands.put("rmtree", new RmtreeShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
	}
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		System.out.format(WELCOME, VERSION);
		
		environment = new EnvironmentImpl();
		while (true) {
			String line = readOneOrMoreLines().trim();
			
			String[] parts = line.split(BY_WHITESPACE, 2);
			String commandName = parts[0].trim();
			
			String arguments = null;
			if (parts.length > 1) {
				arguments = parts[1].trim();
			} else {
				arguments = EMPTY;
			}
			
			ShellCommand command = commands.get(commandName);
			if (command == null) {
				environment.writeln(commandName + ": unknown command.");
				continue;
			}
			
			try {
				ShellStatus status = command.executeCommand(environment, arguments);
				if (status == ShellStatus.TERMINATE) {
					break;
				}
			} catch (ArgumentParserException ex) {
				System.out.println(arguments + ": " + ex.getMessage());
			}
		}
	}
	
	/**
	 * Prompts the user and returns its (whole, possibly multilined) input.
	 * 
	 * @return The users' input.
	 */
	private static String readOneOrMoreLines() {
		environment.write(environment.getPromptSymbol() + SPACE);
		
		StringBuilder builder = new StringBuilder();
		
		while (true) {
			String line = environment.readLine();
			
			if (line.endsWith(environment.getMorelinesSymbol().toString())) {
				builder.append(line, 0, line.length() - 1);
				environment.write(environment.getMultilineSymbol() + SPACE);
			} else {
				builder.append(line);
				break;
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * Environment implementation used for this program. It reads
	 * from the stdin and writes to stdout.
	 * 
	 * @author Mate Gasparini
	 */
	private static class EnvironmentImpl implements Environment {
		
		/**
		 * Symbol displayed at the start of some non-first line
		 * of multilined input.
		 */
		private Character multilineSymbol = '|';
		/**
		 * Symbol displayed at the start of the input.
		 */
		private Character promptSymbol = '>';
		/**
		 * Symbol which allows the user to break the command in parts,
		 * each in a new line.
		 */
		private Character morelinesSymbol = '\\';
		
		/**
		 * Normalized absolute current directory path.
		 */
		private Path currentDirectory = Paths.get(".").toAbsolutePath().normalize();
		
		/**
		 * Stores the data shared across different commands.
		 */
		private Map<String, Object> sharedData = new HashMap<>();
		
		@Override
		public String readLine() throws ShellIOException {
			try {
				return reader.readLine();
			} catch (Exception e) {
				throw new ShellIOException(
					"An error occured while trying to read a line."
				);
			}
		}
		
		@Override
		public void write(String text) throws ShellIOException {
			try {
				System.out.print(text);
			} catch (Exception ex) {
				throw new ShellIOException(
					"An error occured while trying to write."
				);
			}
		}
		
		@Override
		public void writeln(String text) throws ShellIOException {
			try {
				System.out.println(text);
			} catch (Exception ex) {
				throw new ShellIOException(
					"An error occured while trying to write a line."
				);
			}
		}
		
		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}
		
		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}
		
		@Override
		public void setMultilineSymbol(Character symbol) {
			multilineSymbol = symbol;
		}
		
		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}
		
		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;
		}
		
		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}
		
		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelinesSymbol = symbol;
		}

		@Override
		public Path getCurrentDirectory() {
			return currentDirectory;
		}
		
		/**
		 * @throws IllegalArgumentException If the given path is invalid.
		 */
		@Override
		public void setCurrentDirectory(Path path) {
			if (path.toFile().exists()) {
				if (path.toFile().isDirectory()) {
					currentDirectory = path.toAbsolutePath().normalize();
				} else {
					throw new IllegalArgumentException(
						path + ": not a directory."
					);
				}
			} else {
				throw new IllegalArgumentException(
					path + ": path does not exist."
				);
			}
		}
		
		@Override
		public Object getSharedData(String key) {
			return sharedData.get(key);
		}
		
		@Override
		public void setSharedData(String key, Object value) {
			sharedData.put(key, value);
		}
	}
	
	/* STRING CONSTANTS */
	private static final String EMPTY = "";
	private static final String SPACE = " ";
	private static final String BY_WHITESPACE = "\\s+";
}
