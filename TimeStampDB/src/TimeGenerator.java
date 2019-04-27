import java.util.Date;
import java.util.Queue;
import java.util.logging.Logger;

/**
 * 
 * @author SergiuBugneac
 * @version 1.0
 * @since 2019-04-26
 */

public class TimeGenerator implements Runnable {

	/**
	 * Creating Thread using Runnable interface
	 * 
	 * @see java.lang.Thread#run()
	 */
	// logger TimeGenerator
	Logger logger = Logger.getLogger(TimeGenerator.class.getName());
	private Queue<Date> dates;

	// constructor
	public TimeGenerator(Queue<Date> dates) {
		super();
		this.dates = dates;
	}

	@Override
	public void run() {
		// while + sleep
		while (true) {
			// generating time stamp each second and adding to Queue
			dates.add(new Date());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.info("Finishing execution");
				break;
			}
		}
	}

}
