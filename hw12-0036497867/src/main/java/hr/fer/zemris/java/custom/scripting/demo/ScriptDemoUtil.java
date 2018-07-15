package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;

/**
 * Utility class used by the {@link SmartScriptEngine} demonstration programs.
 * 
 * @author Mate Gasparini
 */
public class ScriptDemoUtil {
	
	/**
	 * Reads the content from the file specified by the given file path
	 * and returns it as one String.
	 * 
	 * @param fileName The given file path.
	 * @return Contents of the file.
	 * @throws IOException If an I/O error occurs.
	 */
	public static String readFromDisk(String fileName) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		return String.join("\n", lines);
	}
}
