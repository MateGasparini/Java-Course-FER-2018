package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IDispatcher;
import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * {@link IWebWorker} that acquires two context parameters (a and b),
 * parses their values to integers (or uses some default values) and
 * calculates the sum (a + b).<br>
 * All three values are then stored as context temporary parameters
 * and dispatches the request to the context's {@link IDispatcher}
 * with the URL to the calc smart script.
 * 
 * @author Mate Gasparini
 */
public class SumWorker implements IWebWorker {
	
	/** Key for the first parameter (a). */
	private static final String A = "a";
	
	/** Key for the second parameter (b). */
	private static final String B = "b";
	
	/** Key for storing the sum. */
	private static final String SUM = "zbroj";
	
	/** Default a values. */
	private static final int DEFAULT_A = 1;
	
	/** Default b value. */
	private static final int DEFAULT_B = 2;
	
	@Override
	public synchronized void processRequest(RequestContext context) throws Exception {
		int a = DEFAULT_A;
		int b = DEFAULT_B;
		
		String aValue = context.getParameter(A);
		if (aValue != null) {
			try {
				a = Integer.parseInt(aValue);
			} catch (NumberFormatException ignorable) {}
		}
		
		String bValue = context.getParameter(B);
		if (bValue != null) {
			try {
				b = Integer.parseInt(bValue);
			} catch (NumberFormatException ignorable) {}
		}
		
		String aActual = String.valueOf(a);
		context.setTemporaryParameter(A, aActual);
		
		String bActual = String.valueOf(b);
		context.setTemporaryParameter(B, bActual);
		
		String sum = String.valueOf(a + b);
		context.setTemporaryParameter(SUM, sum);
		
		context.getDispatcher().dispatchRequest("/private/calc.smscr");
	}
}
