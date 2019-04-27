import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
* @author SergiuBugneac
* @version 1.0
* @since 2019-04-26
*/

public class ConnectionManager {
	/**
	 * 
	 * Opens a connection with the database
	 * 
	 * @return Opened connection
	 * @throws SQLException           if the connection can not be opened
	 * @throws ClassNotFoundException if the driver cannot be found
	 */
	// Creating the connection
	private static String urlstring = "jdbc:mysql://localhost:3306/timestampdb";
	// get the driver for this database.
	private static String driverName = "com.mysql.cj.jdbc.Driver";
	private static String username = "root";
	private static String password = "root";
	private static Connection con;
	// Holds the singleton
	private static ConnectionManager INSTANCE;

	private ConnectionManager() {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName(driverName);
			initiateNewConnection();
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found.");
		}
	}

	private void initiateNewConnection() {
		try {
			// Setup the connection with the DB
			con = DriverManager.getConnection(urlstring, username, password);
		} catch (SQLException ex) {
			// log an exception
			System.out.println("Failed to create the database connection.");
		}
	}

	// method that construct a singleton,
	public static ConnectionManager getConnectionManager() {
		if (INSTANCE == null) {
			// initialization
			INSTANCE = new ConnectionManager();
		}
		return INSTANCE;
	}

	public Connection getConnection() {
		try {
			if (con.isValid(1)) {
				return con;
			}
			initiateNewConnection();
			if (con.isValid(1)) {
				return con;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
