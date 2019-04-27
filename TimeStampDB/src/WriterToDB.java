import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * @author Admin
 *
 */
public class WriterToDB implements Runnable {

	private Queue<Date> dates;
	private ConnectionManager connectionManager;
	Logger logger = Logger.getLogger(DatabaseReader.class.getName());

	public WriterToDB(Queue<Date> dates) {
		super();
		this.dates = dates;
		connectionManager = ConnectionManager.getConnectionManager();
	}

	public void run() {
		while (true) {
			while (!dates.isEmpty()) {
				Connection connection = connectionManager.getConnection();
				if (connection != null) {
					Date currentInsertDate = dates.peek();
					if (currentInsertDate != null) {
						// write to database
						String sql = "INSERT INTO timestamp(stampname) VALUES (?)";
						try {
							PreparedStatement statement = connection.prepareStatement(sql);
							statement.setString(1, currentInsertDate.toString());
							statement.execute();
							dates.remove();
						} catch (SQLException e) {
							 
							try {
								System.out.println("Can't connect to the database ");
								Thread.sleep(5000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

					}
				}

			}
		}
	}

}
