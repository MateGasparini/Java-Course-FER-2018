package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * {@link IWebWorker} that sets the context's temporary background color
 * parameter (using the context's persistent background color parameter
 * and some default color value) and dispatches the request to the
 * context's {@link IDispatcher} with the URL to the home page smart script.
 * 
 * @author Mate Gasparini
 */
public class Home implements IWebWorker {
	
	/** The default home page background color. */
	private static final String DEFAULT_BACKGROUND_COLOR = "7F7F7F";
	
	/** Context persistent parameters map's background color key. */
	private static final String PERSISTENT_BG_COLOR_KEY = "bgcolor";
	
	/** Context temporary parameters map's background color key. */
	private static final String TEMPORARY_BG_COLOR_KEY = "background";
	
	@Override
	public synchronized void processRequest(RequestContext context) throws Exception {
		String loadedColor = context.getPersistentParameter(PERSISTENT_BG_COLOR_KEY);
		context.setTemporaryParameter(
			TEMPORARY_BG_COLOR_KEY,
			loadedColor == null ? DEFAULT_BACKGROUND_COLOR : loadedColor
		);
		context.getDispatcher().dispatchRequest("/private/home.smscr");
	}
}
