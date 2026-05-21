package queue.system;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/queue_system";
    private static final String USER = "root"; 
    private static final String PASS = "root"; // Your MySQL password

    public static Connection getConnection() throws Exception {
        // Load the driver you added to Eclipse
        Class.forName("com.mysql.cj.jdbc.Driver"); 
        return DriverManager.getConnection(URL, USER, PASS);
    }
}