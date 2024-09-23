package org.app.Tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseC extends DataFile{

    private static databaseC instance; // Changed to DatabaseC to match class name
    private Connection connection;

    // Private constructor to prevent instantiation
    private databaseC() throws SQLException {
        connect();
    }

    // Method to establish the database connection
    private void connect() throws SQLException {
        try {
            String url = DataFile.url();
            String username = DataFile.user();
            String password = DataFile.password();

            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            throw e; // Re-throw the SQLException
        } catch (Exception e) {
            System.out.println("Error loading database properties: " + e.getMessage());
            throw new SQLException(e);
        }
    }

    // Method to get the database connection
    public Connection getConnection() {
        return connection;
    }

    // Singleton method to get the instance of DatabaseC
    public static databaseC getInstance() throws SQLException {
        if (instance == null) {
            instance = new databaseC(); // Changed to DatabaseC
        } else if (instance.getConnection().isClosed()) {
            instance.connect(); // Reuse the existing instance and reconnect
        }

        return instance;
    }



    // Method to close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("connection closed!");
            }
        } catch (SQLException e) {
            System.out.println("Error closing database connection: " + e.getMessage());
        }
    }
}
