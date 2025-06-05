package org.LT3.config;

import java.sql.Connection;

public class DatabaseInitializer {
    public static void initialize() {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            System.out.println("Database initialized successfully");
        } catch (Exception e) {
            System.err.println("Error while initializing database: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Database connection closed");
                } catch (Exception e) {
                    System.err.println("Error while closing connection: " + e.getMessage());
                }
            }
        }
    }
}