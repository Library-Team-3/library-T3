package org.LT3.config;

import java.sql.*;
import java.io.InputStream;
import java.util.Scanner;

public class DatabaseConnection {
    private static final String DB_HOST = System.getenv("DB_HOST");
    private static final String DB_PORT = System.getenv("DB_PORT");
    private static final String DB_NAME = System.getenv("DB_NAME");
    private static final String DB_USER = System.getenv("DB_USER");
    private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

    private static final String JDBC_URL = String.format(
            "jdbc:mysql://%s:%s/?allowMultiQueries=true&serverTimezone=UTC",
            DB_HOST, DB_PORT);
    private static final String DB_URL = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC",
            DB_HOST, DB_PORT, DB_NAME);

    private static boolean initialized = false;

    public static Connection getConnection() throws Exception {
        if (!initialized) {
            System.out.println("Initializing database...");
            runSchemaSql();
            initialized = true;
            System.out.println("Database successfully initialized!");
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static void runSchemaSql() throws Exception {
        InputStream is = DatabaseConnection.class.getClassLoader().getResourceAsStream("schema.sql");
        if (is == null) {
            throw new RuntimeException("schema.sql not found in resources!");
        }

        StringBuilder sqlBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(is)) {
            while (scanner.hasNextLine()) {
                sqlBuilder.append(scanner.nextLine()).append("\n");
            }
        }

        String sql = sqlBuilder.toString();
        System.out.println("Executing SQL script...");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            String[] queries = sql.split(";");
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    try {
                        stmt.execute(query);
                        System.out.println(
                                "Executed query: " + query.substring(0, Math.min(50, query.length())) + "...");
                    } catch (SQLException e) {
                        System.err.println("Error executing query: " + query);
                        throw e;
                    }
                }
            }
        }
    }
}

