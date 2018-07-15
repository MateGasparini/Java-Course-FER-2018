package hr.fer.zemris.java.hw13.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * {@link ServletContextListener} which stores the context initialization timestamp
 * in the servlet context attribute {@code TIMESTAMP_ATTRIBUTE}.
 * 
 * @author Mate Gasparini
 */
@WebListener
public class ServletListener implements ServletContextListener {
	
	/** Timestamp servlet context attribute name. */
	public static final String TIMESTAMP_ATTRIBUTE = "startTimestamp";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute(TIMESTAMP_ATTRIBUTE, System.currentTimeMillis());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// Do nothing.
	}
}
