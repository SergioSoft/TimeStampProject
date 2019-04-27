import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Queue;
import java.util.logging.Logger;

/**
 *
 * 
 * @author SergiuBugneac
 * @version 1.0
 * @since 2019-04-26
 * 
 */
public class WriterToDB implements Runnable {

	/**
	 * Creating Thread using Runnable interface
	 * 
	 * @see java.lang.Thread#run()
	 */

	private Queue<Date> dates;
	private ConnectionManager connectionManager;
	Logger logger = Logger.getLogger(DatabaseReader.class.getName());

	// constructor
	public WriterToDB(Queue<Date> dates) {
		super();
		this.dates = dates;
		connectionManager = ConnectionManager.getConnectionManager();
	}
	public void run() {
		while (true) {
			try {
				// Setup the connection with the DB
				Connection connection = connectionManager.getConnection();
				while (!dates.isEmpty()) {
					if (connection != null) {
						Date currentInsertDate = dates.peek();
						if (currentInsertDate != null) {
							// Inserting time stamp data using SQL query
							String sql = "INSERT INTO timestamp(stampname) VALUES (?)";
							PreparedStatement statement = connection.prepareStatement(sql);
							statement.setString(1, currentInsertDate.toString());
							statement.execute();
							// removes time stamps from the queue
							dates.remove();
						}
					}
				}
			} catch (SQLException e) {
				// If connection to Database stops after 5 seconds it reconnects
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
}
