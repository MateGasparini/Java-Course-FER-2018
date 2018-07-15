package hr.fer.zemris.java.hw07.shell.commands;

import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.ShellCommand;
import hr.fer.zemris.java.hw07.shell.ShellStatus;

/**
 * Command which, when executed, writes the character currently used
 * as the specified symbol, or overwrites it with a new value.
 * 
 * @author Mate Gasparini
 */
public class SymbolShellCommand implements ShellCommand {
	
	private static final String NAME = "symbol";
	private static final List<String> COMMAND_DESCRIPTION = Arrays.asList(
		"Usage: " + NAME + " [SYMBOL] [NEW]",
		"[SYMBOL]	- name of the specified symbol (mandatory)",
		"[NEW]		- new value for the symbol (optional)",
		"",
		"Writes the current value of the specified symbol or overwrites it with the new",
		"value."
	);
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			env.writeln(NAME + ": expected arguments.");
		} else {
			String[] parts = arguments.split("\\s+");
			
			if (parts.length == 1) {
				String symbol = parts[0];
				
				if (symbol.equals(PROMPT)) {
					env.writeln(String.format(
						SYMBOL_FOR, PROMPT, env.getPromptSymbol()));
				} else if (symbol.equals(MORELINES)) {
					env.writeln(String.format(
						SYMBOL_FOR, MORELINES, env.getMorelinesSymbol()));
				} else if (symbol.equals(MULTILINE)) {
					env.writeln(String.format(
						SYMBOL_FOR, MULTILINE, env.getMultilineSymbol()));
				} else {
					env.writeln(NAME + ": invalid argument.");
				}
			} else if (parts.length == 2) {
				String symbol = parts[0];
				String rest = parts[1];
				
				if (rest.length() != 1) {
					env.writeln(NAME + ": expected a single character.");
				}
				Character newSymbol = rest.charAt(0);
				
				if (symbol.equals(PROMPT)) {
					env.writeln(String.format(
						SYMBOL_CHANGED, PROMPT, env.getPromptSymbol(), newSymbol));
					env.setPromptSymbol(newSymbol);
				} else if (symbol.equals(MORELINES)) {
					env.writeln(String.format(
						SYMBOL_CHANGED, MORELINES, env.getMorelinesSymbol(), newSymbol));
					env.setMorelinesSymbol(newSymbol);
				} else if (symbol.equals(MULTILINE)) {
					env.writeln(String.format(
						SYMBOL_CHANGED, MULTILINE, env.getMultilineSymbol(), newSymbol));
					env.setMultilineSymbol(newSymbol);
				} else {
					env.writeln(NAME + ": invalid argument.");
				}
			} else {
				env.writeln(NAME + ": too many arguments.");
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
	
	/* STRING CONSTANTS */
	private static final String PROMPT = "PROMPT";
	private static final String MORELINES = "MORELINES";
	private static final String MULTILINE = "MULTILINE";
	private static final String SYMBOL_FOR =
			"Symbol for %s is '%c'";
	private static final String SYMBOL_CHANGED =
			"Symbol for %s changed from '%c' to '%c'";
}
