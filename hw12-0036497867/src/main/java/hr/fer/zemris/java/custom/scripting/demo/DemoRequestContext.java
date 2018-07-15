package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Program that demonstrates the functionality of the {@link RequestContext} class.<br>
 * Produces three textual files using different context configurations as specified
 * in the homework instructions.
 * 
 * @author Mate Gasparini
 */
public class DemoRequestContext {
	
	/**
	 * Main method which is called when the program starts.
	 * 
	 * @param args Command line arguments (not used).
	 * @throws IOException If an I/O error occurs.
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}
	
	/**
	 * Writes some sample text to some {@link RequestContext}.
	 * 
	 * @param filePath The given file path.
	 * @param encoding The given encoding (charset name).
	 * @throws IOException If an I/O error occurs.
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		
		RequestContext rc = new RequestContext(
			os, new HashMap<>(), new HashMap<>(), new ArrayList<>()
		);
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		
		os.close();
	}
	
	/**
	 * Writes some sample text to some {@link RequestContext}
	 * (which includes {@link RCCookie}s).
	 * 
	 * @param filePath The given file path.
	 * @param encoding The given encoding (charset name).
	 * @throws IOException If an I/O error occurs.
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		
		RequestContext rc = new RequestContext(
			os, new HashMap<>(), new HashMap<>(), new ArrayList<>()
		);
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1", "/"));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/"));
		
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		
		os.close();
	}
}
