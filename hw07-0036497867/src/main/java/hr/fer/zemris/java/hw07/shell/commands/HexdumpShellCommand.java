package hr.fer.zemris.java.hw07.shell.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
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
 * and produces its hex-output (along with partial character conversion).
 * 
 * @author Mate Gasparini
 */
public class HexdumpShellCommand implements ShellCommand {
	
	private static final String NAME = "hexdump";
	private static final List<String> COMMAND_DESCRIPTION = new LinkedList<>();
	static {
		String[] commands = new String[] {
			"Usage: " + NAME + " [FILEPATH]",
			"[FILEPATH]	- path to the specified file (mandatory)",
			"",
			"Opens the specified file and produces its hex-output.",
			"Also, bytes that can be represented by standard characters are converted to",
			"characters and shown on the right (otherwise, a dot is written)."
		};
		
		for (String command : commands) {
			COMMAND_DESCRIPTION.add(command);
		}
	}
	
	/**
	 * The given environment used for communication with the user.
	 */
	private Environment environment;
	
	/**
	 * The current position (as in number of bytes).
	 */
	private int currentPosition;
	
	/**
	 * A helper variable specifying the number of bytes saved
	 * to the start of the buffer when there were not enough bytes
	 * to fill the current row.
	 */
	private int bufferOffset;
	
	private static final int BYTES_PER_LINE = 16;
	
	/**
	 * Lowest byte value that will be represented by a character.
	 */
	private static final int LOWEST_CHAR = 32;
	
	/**
	 * Highest byte value that will be represented by a character.
	 */
	private static final int HIGHEST_CHAR = 127;
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> argumentList = new ArgumentParser(arguments).getArguments();
		int size = argumentList.size();
		
		this.environment = env;
		
		if (size == 0) {
			environment.writeln(NAME + ": expected an argument.");
		} else if (size == 1) {
			String pathName = argumentList.get(0);
			
			try {
				printFileContent(Paths.get(pathName));
			} catch (IOException ex) {
				environment.writeln(pathName + ": invalid file path.");
			}
		} else {
			environment.writeln(NAME + ": too many arguments.");
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
	 * Prints the hex-content of the file specified by the given path.
	 * 
	 * @param path The given path.
	 * @throws IOException If an error occurred while reading from the file.
	 */
	private void printFileContent(Path path) throws IOException {
		try (BufferedInputStream stream = new BufferedInputStream(
				new FileInputStream(path.toFile()))) {
			currentPosition = 0;
			bufferOffset = 0;
			
			byte[] buffer = new byte[4096];
			while (true) {
				int numberOfRead = stream.read(buffer, bufferOffset, 4096-bufferOffset);
				
				if (numberOfRead < 1) {
					break;
				}
				
				printFromBuffer(buffer, numberOfRead);
			}
			printLastRowFromBuffer(buffer);
		} catch (IOException ex) {
			throw ex;
		}
	}
	
	/**
	 * Writes {@code length} number of bytes from the given buffer to the table.
	 * 
	 * @param buffer The given buffer.
	 * @param length Number of bytes to write from the given buffer.
	 */
	private void printFromBuffer(byte[] buffer, int length) {
		byte[] line = new byte[BYTES_PER_LINE];
		
		for (int i = 0; i < length; i ++, currentPosition ++) {
			int inLineIndex = i % BYTES_PER_LINE;
			
			if (inLineIndex == 0) {
				int remaining = length - i;
				
				if (remaining < BYTES_PER_LINE) {
					saveToBufferStart(buffer, i, length);
					bufferOffset = remaining;
					
					return;
				} else {
					printHexPosition();
				}
			}
			
			environment.write(byteToHex(buffer[i]));
			line[inLineIndex] = buffer[i];
			
			if (inLineIndex != 7) {
				environment.write(" ");
			} else {
				environment.write("|");
			}
			
			if (inLineIndex == BYTES_PER_LINE - 1) {
				environment.write("| ");
				printAsText(line, BYTES_PER_LINE);
				environment.writeln("");
			}
		}
		
		bufferOffset = 0;
	}
	
	/**
	 * Writes the last row from buffer
	 * (and fills with whitespaces where necessary).
	 * 
	 * @param buffer
	 */
	private void printLastRowFromBuffer(byte[] buffer) {
		if (bufferOffset == 0) {
			return;
		}
		
		printHexPosition();
		for (int i = 0; i < bufferOffset; i ++) {
			environment.write(byteToHex(buffer[i]));
			
			if (i != 7) {
				environment.write(" ");
			} else {
				environment.write("|");
			}
		}
		
		for (int i = bufferOffset; i < BYTES_PER_LINE; i ++) {
			environment.write("  ");
			
			if (i != 7) {
				environment.write(" ");
			} else {
				environment.write("|");
			}
		}
		
		environment.write("| ");
		printAsText(buffer, bufferOffset);
		environment.writeln("");
	}
	
	/**
	 * Writes the current position as a hex-number.
	 */
	private void printHexPosition() {
		environment.write(String.format("%08X: ", currentPosition));
	}
	
	/* HELPER METHODS */
	
	/**
	 * Converts the given byte to the corresponding hex-string.
	 * 
	 * @param b The given byte.
	 * @return The corresponding hex-string.
	 */
	private static String byteToHex(byte b) {
		int higher = (b & 0xFF) >>> 4; // Dividing by 16.
		int lower = (b & 0xFF) % 16;
		
		return String.valueOf(toHexDigit(higher))
			.concat(String.valueOf(toHexDigit(lower)));
	}
	
	/**
	 * Converts the given byte to the corresponding hex-character.
	 * 
	 * @param b The given byte.
	 * @return The corresponding hex-character.
	 */
	private static char toHexDigit(int b) {
		if (b < 10) {
			return (char) ('0' + b);
		} else if (b < 16) {
			return (char) ('A' + b - 10);
		} else {
			throw new IllegalArgumentException(b + ": not a valid byte.");
		}
	}
	
	/**
	 * Writes the textual representation of the line of bytes
	 * (with dots as replacement where needed).
	 * 
	 * @param line The given line of bytes.
	 * @param length The number of bytes in the line.
	 */
	private void printAsText(byte[] line, int length) {
		for (int i = 0; i < length; i ++) {
			byte current = line[i];
			
			if (current < LOWEST_CHAR || current > HIGHEST_CHAR) {
				environment.write(".");
			} else {
				environment.write(String.valueOf((char) current));
			}
		}
	}
	
	/**
	 * Copies {@code length} number of bytes of the given buffer
	 * starting at {@code current} position, to the start of the same buffer.
	 * 
	 * @param buffer The given buffer.
	 * @param current The starting position.
	 * @param length Number of bytes to copy.
	 */
	private void saveToBufferStart(byte[] buffer, int current, int length) {
		for (int i = current; i < length; i ++) {
			buffer[i - current] = buffer[i];
		}
	}
}
