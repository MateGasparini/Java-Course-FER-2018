package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * {@link IWebWorker} that checks if the background color context parameter is valid and,
 * if it is, sets the context persistent parameter to the same hex-value.<br>
 * Also, it generates HTML content to the context which gives info on color change and
 * provides a link back to the home page.
 * 
 * @author Mate Gasparini
 */
public class BgColorWorker implements IWebWorker {
	
	/** Context (persistent) parameters map's background color key. */
	private static final String BG_COLOR_KEY = "bgcolor";
	
	@Override
	public synchronized void processRequest(RequestContext context) throws Exception {
		String colorCode = context.getParameter(BG_COLOR_KEY);
		context.setMimeType("text/html");
		StringBuilder sb = new StringBuilder(
			"<html><head><title>Color change status</title></head><body>Homepage color "
		);
		if (colorCode.matches("[\\p{XDigit}]{6}")) {
			context.setPersistentParameter(BG_COLOR_KEY, colorCode);
			sb.append("changed successfully.");
		} else {
			sb.append("failed to change.");
		}
		sb.append("<br><a href=\"../index2.html\">Back to Homepage</a></body></html>");
		context.write(sb.toString());
	}
}
