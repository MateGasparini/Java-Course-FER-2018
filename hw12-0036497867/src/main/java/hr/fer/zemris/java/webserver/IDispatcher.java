package hr.fer.zemris.java.webserver;

/**
 * Interface used to execute some {@code IDispatcher} client worker's procedure
 * specified by some given URL path.<br>
 * 
 * @author Mate Gasparini
 */
public interface IDispatcher {
	
	/**
	 * Dispatches the request specified by the given URL path.
	 * 
	 * @param urlPath The given URL path.
	 * @throws Exception If some type of error (e.g. I/O) occurs.
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
