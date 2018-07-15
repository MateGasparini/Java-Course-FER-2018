package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;
import hr.fer.zemris.java.hw07.shell.parser.ArgumentParser;

/**
 * Command which, when executed, opens the specified file
 * and writes its content to the environment.
 * 
 * @author Mate Gasparini
 */
public class CatShellCommand implements ShellCommand {
	
	private static final String NAME = "cat";
	private static final List<String> COMMAND_DESCRIPTION = new LinkedList<>();
	static {
		String[] commands = new String[] {
			"Usage: " + NAME + " [FILEPATH] [CHARSET]",
			"[FILEPATH]	- path to the specified file (mandatory)",
			"[CHARSET]	- charset name that should be used to interpret chars from bytes",
			"		(optional)",
			"",
			"Opens the specified file and writes its content."
		};
		
		for (String command : commands) {
			COMMAND_DESCRIPTION.add(command);
		}
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		try {
			if (size == 0) {
				env.writeln(NAME + ": expected arguments.");
			} else if (size == 1) {
				Path path = Paths.get(argumentList.get(0));
				printFileContent(path, Charset.defaultCharset(), env);
			} else if (size == 2) {
				Path path = Paths.get(argumentList.get(0));
				printFileContent(path, Charset.forName(argumentList.get(1)), env);
			} else {
				env.writeln(NAME + ": too many arguments.");
			}
		} catch (IOException ex) {
			env.writeln(argumentList.get(0) + ": invalid file path.");
		} catch (IllegalCharsetNameException ex) {
			env.writeln(argumentList.get(1) + ": invalid charset name.");
		} catch (UnsupportedCharsetException ex) {
			env.writeln(argumentList.get(1) + ": unsupported charset.");
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
	 * Prints the content of the file with the given path
	 * to the given environment, by using the given charset.
	 * 
	 * @param path The given path.
	 * @param charset The given charset.
	 * @param env The given environment.
	 * @throws IOException If an error occured while reading the file.
	 */
	private void printFileContent(Path path, Charset charset, Environment env) throws IOException {
		/* The commented reader throws an exception when malformed
		 * input is read (the DEFAULT action).
		 * 
		 * The other one uses the REPLACE action so I've chosen it
		 * because it resembles the bash 'cat' command. */
		
//		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
		try (BufferedReader reader = new BufferedReader(
								new InputStreamReader(
								new FileInputStream(path.toFile()), charset))) {
			char[] buffer = new char[4096];
			
			while (true) {
				int numberOfRead = reader.read(buffer);
				
				if (numberOfRead < 1) {
					break;
				}
				
				env.write(String.copyValueOf(buffer, 0, numberOfRead));
			}
		} catch (IOException ex) {
			throw ex;
		}
	}
}
