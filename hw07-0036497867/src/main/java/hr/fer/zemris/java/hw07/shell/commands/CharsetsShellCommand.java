package hr.fer.zemris.java.hw07.shell.commands;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which, when executed, lists the names of all supported charsets.
 * 
 * @author Mate Gasparini
 */
public class CharsetsShellCommand implements ShellCommand {
	
	private static final String NAME = "charsets";
	private static final List<String> COMMAND_DESCRIPTION = new LinkedList<>();
	static {
		String[] commands = new String[] {
			"Usage: " + NAME,
			"",
			"Lists the names of supported charsets for this Java platform."
		};
		
		for (String command : commands) {
			COMMAND_DESCRIPTION.add(command);
		}
	}
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			env.writeln(NAME + ": takes no arguments.");
		} else {
			SortedMap<String, Charset> charsets = Charset.availableCharsets();
			
			for (String charsetName : charsets.keySet()) {
				env.writeln(charsetName);
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
}
