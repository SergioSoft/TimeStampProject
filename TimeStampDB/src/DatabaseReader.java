import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseReader {
	private ConnectionManager connectionManager;
	Logger logger = Logger.getLogger(DatabaseReader.class.getName());

	public DatabaseReader() {
		connectionManager = ConnectionManager.getConnectionManager();
	}

	public void readFromDB() {
		Connection connection = connectionManager.getConnection();
		if (connection != null) {
			ResultSet rs;
			try {
				rs = connection.createStatement().executeQuery("select * from timestamp");
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
