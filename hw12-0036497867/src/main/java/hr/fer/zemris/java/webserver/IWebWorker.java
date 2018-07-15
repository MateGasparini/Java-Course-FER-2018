package hr.fer.zemris.java.webserver;

/**
 * Interface implemented by all web worker classes.<br>
 * It is used to process some request using some specific implementation.
 * 
 * @author Mate Gasparini
 */
public interface IWebWorker {
	
	/**
	 * Process the request specified by the given context in some specific way.<br>
	 * Should be explicitly synchronized in specific implementations,
	 * as multiple threads can call it at the same time.
	 * 
	 * @param context The given request context.
	 * @throws Exception If some type of error (e.g. I/O) occurs.
	 */
	public void processRequest(RequestContext context) throws Exception;
}
