import java.sql.SQLException;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

/**
 * 
 * The program implements an application of inserting 
 * date time stamps into Database each second.
 * When Database connection disappear, it reconnects after each 5 seconds 
 * and those previous values are inserted in Database
 * 
 * @author SergiuBugneac
 * @version 1.0
 * @since 2019-04-26
 */

public class MainAppDB {
	// logger MainAppDB
	static Logger logger = Logger.getLogger(MainAppDB.class.getSimpleName());

	public static void main(String[] argv) throws SQLException {
		// Queue for manipulating with time Stamp records
		Queue<Date> dates = new ConcurrentLinkedQueue<>();
		if (argv.length == 0) {
			logger.info("Writing time stamps to Database");
			// create an object of TimeGenerator and WriterToDB
			TimeGenerator threadPublishTime = new TimeGenerator(dates);
			WriterToDB threadWriteTimeToDB = new WriterToDB(dates);
			threadPublishTime.run();
			threadWriteTimeToDB.run();
		} else if (argv[0].equals("-p")) {
			// create an object of DatabaseReader
			DatabaseReader readFromDB = new DatabaseReader();
			readFromDB.readFromDB();
		}
	}
}
