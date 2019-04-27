import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class TimeGenerator implements Runnable {
	Logger logger = Logger.getLogger(TimeGenerator.class.getName());
	private Queue<Date> dates;
	
	public TimeGenerator(Queue<Date> dates) {
		super();
		this.dates = dates;
	}

	@Override
	public void run() {
		while (true) {
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
