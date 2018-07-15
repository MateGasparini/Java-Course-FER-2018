package hr.fer.zemris.java.voting.dao.sql;

import java.sql.Connection;

/**
 * Stores SQL database connections into a {@link ThreadLocal} object
 * (actually a {@code Map} whose key is an identificator of the thread which is
 * currently connected to the database).
 * 
 * @author Mate Gasparini
 */
public class SQLConnectionProvider {
	
	/** Thread-local {@link Connection}, different for each thread. */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Set up connection for the current thread (or remove it from the {@code Map}
	 * if the given connection is {@code null}).
	 * 
	 * @param con Connection to the database.
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Get the connection which the current thread (the caller) can use.
	 * 
	 * @return Connection to the database.
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}