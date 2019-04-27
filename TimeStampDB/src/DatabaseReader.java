import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
* @author SergiuBugneac
* @version 1.0
* @since 2019-04-26
*/
public class DatabaseReader {
	private ConnectionManager connectionManager;
	//logger DatabaseReader
	Logger logger = Logger.getLogger(DatabaseReader.class.getName());
	//constructor
	public DatabaseReader() {
		connectionManager = ConnectionManager.getConnectionManager();
	}

	public void readFromDB() {
		 // Setup the connection with the DB
		Connection connection = connectionManager.getConnection();
		if (connection != null) {
			ResultSet rs;
			try {
				// get some metadata from the database
				// Result set get the result of the SQL query
				rs = connection.createStatement().executeQuery("select * from timestamp");
				// ResultSet is initially before the first data set
				while (rs.next()) {
					logger.info(rs.getString("stampname"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
