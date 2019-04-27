
import java.sql.SQLException;
import org.junit.Test;

public class TestApp {

	@Test
	public void testMainWithParameter() throws SQLException {
		System.out.println("TESTING with parameter");
		MainAppDB.main(new String[] { "-p" });

	}

	@Test
	public void testMainWithoutParameter() throws SQLException {
		System.out.println("TESTING without parameter");
		MainAppDB.main(new String[] {});

	}

}
