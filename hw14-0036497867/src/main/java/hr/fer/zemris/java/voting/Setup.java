package hr.fer.zemris.java.voting;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * {@link ServletContextListener} which, when the web application starts,
 * loads the DB settings, initializes the DB connection pool
 * and initializes the tables.<br>
 * If the <i>Polls</i> table does not exist (or is empty), it is created
 * and filled with data, as well as the <i>PollOptions</i> table.<br>
 * 
 * @author Mate Gasparini
 */
@WebListener
public class Setup implements ServletContextListener {
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String connectionURL = null;
		try {
			connectionURL = SetupUtil.getConnectionURL(
				sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties")
			);
		} catch (IOException | IllegalArgumentException ex) {
			throw new RuntimeException("Error reading DB properties file.", ex);
		}
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException ex) {
			throw new RuntimeException("Error initializing connection pool.", ex);
		}
		cpds.setJdbcUrl(connectionURL);
		
		try {
			SetupUtil.initTables(cpds.getConnection());
		} catch (SQLException | IOException ex) {
			throw new RuntimeException("Error initializing tables.", ex);
		}
		
		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource)sce.getServletContext()
									.getAttribute("hr.fer.zemris.dbpool");
		if(cpds!=null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}