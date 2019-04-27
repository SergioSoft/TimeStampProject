import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static String urlstring = "jdbc:mysql://localhost:3306/timestampdb";
	private static String driverName = "com.mysql.cj.jdbc.Driver";
	private static String username = "root";
	private static String password = "root";
	private static Connection con;
	private static String url;

	private static ConnectionManager INSTANCE;

	private ConnectionManager() {
		try {
			Class.forName(driverName);
			initiateNewConnection();
		} catch (ClassNotFoundException ex) {
			System.out.println("Driver not found.");
		}
	}

	private void initiateNewConnection() {
		try {
			con = DriverManager.getConnection(urlstring, username, password);
		} catch (SQLException ex) {
			System.out.println("Failed to create the database connection.");
		}
	}

	public static ConnectionManager getConnectionManager() {
		if (INSTANCE == null) {
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
