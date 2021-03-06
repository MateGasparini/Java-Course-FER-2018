package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Program that demonstrates the {@link SmartScriptEngine} execution
 * by launching the "brojPoziva.smscr" smart script.
 * 
 * @author Mate Gasparini
 */
public class Script3Demo {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		String documentBody = null;
		try {
			documentBody = ScriptDemoUtil.readFromDisk("src/main/resources/brojPoziva.smscr");
		} catch (IOException ex) {
			System.out.println("File does not exist or is not readable.");
			return;
		}
		Map<String, String> parameters = new HashMap<>();
		Map<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> cookies = new ArrayList<>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(
			System.out, parameters, persistentParameters, cookies);
		
		new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			rc
		).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}
}
