import java.sql.SQLException;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class MainAppDB {
	static Logger logger = Logger.getLogger(MainAppDB.class.getSimpleName());

	public static void main(String[] argv) throws SQLException {

		Queue<Date> dates = new ConcurrentLinkedQueue<>();
		if (argv[0].equals("0")) {
			logger.info("Writing timeStamp to Database");
			TimeGenerator threadPublishTime = new TimeGenerator(dates);
			WriterToDB threadWriteTimeToDB = new WriterToDB(dates);
			threadPublishTime.run();
			threadWriteTimeToDB.run();
		} else if (argv[1].equals("-p")) {
			DatabaseReader readFromDB = new DatabaseReader();
			readFromDB.readFromDB();

		}
	}

}
