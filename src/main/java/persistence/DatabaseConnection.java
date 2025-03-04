package persistence;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER;



    static {

        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                System.err.println("Sorry, unable to find db.properties");
                throw new IllegalStateException("Unable to find db.properties");
            }
            prop.load(input);
            URL = prop.getProperty("db.url");
            USER = prop.getProperty("db.user");
            PASSWORD = prop.getProperty("db.password");
            DRIVER = prop.getProperty("db.driver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);  // Load the driver first
            return DriverManager.getConnection(URL, USER, PASSWORD);  // Only need URL, user, password
        } catch (ClassNotFoundException e) {
            throw new SQLException("H2 JDBC Driver not found.", e);
        }
    }
}
