package app.IMAS.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	 private static final DatabaseConnection databaseConnection = new DatabaseConnection();
	    private final String DRIVER = "com.mysql.jdbc.Driver";
	    private final String URL = "jdbc:mysql://localhost:3306/mgstore";
	    private final String USER = "root";
	    private final String PASSWORD = "";

	    public Connection createConnection() {
	        Connection connection = null;

	        try {
	            Class.forName(DRIVER);
	            connection = DriverManager.getConnection(URL, USER, PASSWORD);
	        } catch (SQLException | ClassNotFoundException e) {
	            e.getMessage();
	        }

	        return connection;
	    }

	    public static Connection getConnection() {
	        return databaseConnection.createConnection();
	    }

}
