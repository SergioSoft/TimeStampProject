import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainAppDB {

    public static void main(String[] argv) throws SQLException {
    	// Create a Scanner object(input from console)
    	Scanner myObject = new Scanner(System.in); 
        System.out.println("Press ENTER to begin inserting timestamp to DB each second OR put -p to view from DB: ");
        String consoleInput = myObject.nextLine();
       
        if (consoleInput.isEmpty()) {
            writeToDatabaseEachSecond();
        } else if (consoleInput.equals("-p")) {          
        	readFromDatabase();
        }
    }
    Queue<Date> dates = new ConcurrentLinkedQueue<>();

    private Thread threadPublishTime = new Thread(new Runnable() {
        public void run() {
            while (true){
                dates.add(new Date());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    });

    private Thread threadWriteTimeToDB = new Thread(new Runnable() {
        public void run() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("Where is your MySQL JDBC Driver?");
                e.printStackTrace();
                return;
            }

            while(true) {
                try 
                (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/timestampdb","root","root")){  
                	 System.out.println("connected to database timestampdb");
                	 
                    while (true) {           	
                        Date currentInsertDate = dates.peek();          
                        	if (currentInsertDate != null) {
                            //write to database                               
                        		String sql = "INSERT INTO timestamp(stampname) VALUES (?)";
                        		PreparedStatement statement= connection.prepareStatement(sql);
                        			statement.setString(1, currentInsertDate.toString());;
                        			statement.execute();                    		
                        			dates.remove();                       		
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Can't connect to the database ");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        System.out.println("Process finished");
                    }
                }
            }
        }
    });
    private static void writeToDatabaseEachSecond() {
        MainAppDB main = new MainAppDB();
        main.threadPublishTime.start();
        main.threadWriteTimeToDB.start();
    }

    private static void readFromDatabase(){
             try 
             (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/timestampdb","root","root")){   
            	 // read from database
            	 ResultSet rs = connection.createStatement().executeQuery("select * from timestamp");
            	 		while (rs.next()) {
            	 			System.out.println(rs.getString("stampname"));           	 			
			    			}    	 	     	
             	} catch (SQLException e) {
             		System.out.println("Can't connect to the database ");       	 
             	}
    	 	}
    	 }
