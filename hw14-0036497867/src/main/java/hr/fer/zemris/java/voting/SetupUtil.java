package hr.fer.zemris.java.voting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Utility class used by the {@link Setup} class.<br>
 * It contains public methods for parsing the DB settings file and
 * getting the defined connection URL, as well as for initializing
 * all DB tables.
 * 
 * @author Mate Gasparini
 */
public class SetupUtil {
	
	/**
	 * Private default constructor.
	 */
	private SetupUtil() {
	}
	
	/**
	 * Parses the specified .properties file and returns a
	 * connection URL in the form:<br>
	 * <i>jdbc:derby://HOST:PORT/NAME;user=USER;password=PASSWORD</i>.
	 * 
	 * @param propertiesPath Path to the specified .properties file.
	 * @return The parsed connection URL.
	 * @throws IOException If an I/O error occurs.
	 * @throws IllegalArgumentException If some property not set.
	 */
	public static String getConnectionURL(String propertiesPath)
			throws IOException, IllegalArgumentException {
		Properties dbProperties = new Properties();
		dbProperties.load(Files.newInputStream(Paths.get(propertiesPath)));
		
		String host = checkProperty(dbProperties, "host");
		String port = checkProperty(dbProperties, "port");
		String name = checkProperty(dbProperties, "name");
		String user = checkProperty(dbProperties, "user");
		String password = checkProperty(dbProperties, "password");
		
		return "jdbc:derby://"+host+":"+port+"/"+name+";user="+user+";password="+password;
	}
	
	/**
	 * If the <i>Polls</i> table is not created (or is empty), it is created
	 * and populated with data, as well as the <i>PollOptions</i> table.
	 * 
	 * @param connection The given connection to the DB.
	 * @throws SQLException If a database access error occurs.
	 * @throws IOException If an I/O error occurs.
	 */
	public static void initTables(Connection connection) throws SQLException, IOException {
		if (createTable(connection, "Polls")) {
			createTable(connection, "PollOptions");
			populateTables(connection);
		} else if (createTable(connection, "PollOptions")) {
			// Do nothing as it is extremely unlikely to happen.
			// Recommended by M. Cupic.
		}
	}
	
	/**
	 * Checks if the specified property in the given {@link Properties} {@code Map}
	 * is set (is not {@code null}) and returns its value.
	 * 
	 * @param dbProperties The given {@link Properties} {@code Map}.
	 * @param property The specified property.
	 * @return The corresponding property value.
	 * @throws IllegalArgumentException If the specified property is not set.
	 */
	private static String checkProperty(Properties dbProperties, String property)
			throws IllegalArgumentException {
		String value = dbProperties.getProperty(property);
		if (value == null) {
			throw new IllegalArgumentException("Property '"+property+"' not set.");
		}
		return value;
	}
	
	/**
	 * Creates the table specified by the given table name if it does not exist
	 * and returns information about whether the table needs to be populated.
	 * 
	 * @param connection The given connection to the DB.
	 * @param tableName The given table name.
	 * @return {@code true} the table was created or is empty,
	 * 			{@code false} if it already existed and was populated with data.
	 * @throws SQLException If a database access error occurs.
	 * @throws IOException If an I/O error occurs.
	 */
	private static boolean createTable(Connection connection, String tableName)
			throws SQLException, IOException {
		DatabaseMetaData metaData = connection.getMetaData();
		try (ResultSet tables = metaData.getTables(
				null, null, tableName.toUpperCase(), null)) {
			if (tables.next()) {
				// Table exists.
				return isTableEmpty(connection, tableName);
			} else {
				// Table does not exist.
				String sql = readResourceAsString(
						"create_table_"+tableName.toLowerCase()+".txt");
				try (PreparedStatement pst = connection.prepareStatement(sql)) {
					pst.executeUpdate();
					return true;
				}
			}
		}
	}
	
	/**
	 * Returns {@code true} if the table specified by the given table name
	 * contains less than 2 rows.
	 * 
	 * @param connection The given connection to the DB.
	 * @param tableName The given table name.
	 * @return {@code true} if the specified table contains less than 2 rows,
	 * 			and {@code false} if it contains 2 or more rows or if does not exist.
	 * @throws SQLException If a database access error occurs.
	 */
	private static boolean isTableEmpty(Connection connection, String tableName)
			throws SQLException {
		String sql = "SELECT COUNT(*) FROM "+tableName;
		try (ResultSet rset = connection.prepareStatement(sql).executeQuery()) {
			if (rset != null && rset.next()) {
				return rset.getLong(1) < 2;
			}
		}
		return false;
	}
	
	/**
	 * Populates the <i>Polls</i> and <i>PollOptions</i> tables using
	 * the data from the <i>polls_*.txt</i> resources.
	 * 
	 * @param connection The given connection to the DB.
	 * @throws IOException If an I/O error occurs.
	 * @throws SQLException If a database access error occurs.
	 */
	private static void populateTables(Connection connection)
			throws IOException, SQLException {
		populateTable(connection, "polls_bendovi.txt");
		populateTable(connection, "polls_stranice.txt");
	}
	
	/**
	 * Populates the table specified in the given resource.
	 * 
	 * @param connection The given connection to the DB.
	 * @param resource Name of the textual resource used for acquiring SQL code.
	 * @throws IOException If an I/O error occurs.
	 * @throws SQLException If a database access error occurs.
	 */
	private static void populateTable(Connection connection, String resource)
			throws IOException, SQLException {
		List<String> lines = readResource(resource);
		try (PreparedStatement pst = connection.prepareStatement(
				lines.get(0), Statement.RETURN_GENERATED_KEYS)) {
			pst.executeUpdate();
			try (ResultSet rset = pst.getGeneratedKeys()) {
				long pollID;
				if (rset != null && rset.next()) {
					pollID = rset.getLong(1);
				} else {
					throw new RuntimeException("Poll ID could not be generated.");
				}
				
				for (int i = 1, size = lines.size(); i < size; i ++) {
					String line = lines.get(i);
					try (PreparedStatement ps = connection.prepareStatement(line)) {
						ps.setLong(1, pollID);
						ps.executeUpdate();
					}
				}
			}
		}
	}
	
	/**
	 * Reads the specified resource as one String.<br>
	 * Each new line is replaced with a single space character.
	 * 
	 * @param resource Name of the specified textual resource.
	 * @return String content of the specified textual resource.
	 * @throws IOException If an I/O error occurs.
	 */
	private static String readResourceAsString(String resource) throws IOException {
		return String.join(" ", readResource(resource));
	}
	
	/**
	 * Reads the specified resource as a {@code List} of Strings using the
	 * {@link ClassLoader#getResourceAsStream(String)} method.
	 * 
	 * @param resource Name of the specified textual resource. 
	 * @return {@code List} of String lines from the specified textual resource.
	 * @throws IOException If an I/O error occurs.
	 */
	private static List<String> readResource(String resource) throws IOException {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				SetupUtil.class.getClassLoader().getResourceAsStream(resource)))) {
			lines = br.lines().collect(Collectors.toList());
		}
		return lines;
	}
}
