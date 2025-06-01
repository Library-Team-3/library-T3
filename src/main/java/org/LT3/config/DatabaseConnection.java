package org.LT3.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.io.InputStream;
import java.util.Scanner;

public class DatabaseConnection {
    private static final Dotenv dotenv;
    private static final String DB_HOST;
    private static final String DB_PORT;
    private static final String DB_NAME;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    static {
        Dotenv tempDotenv = null;
        try {
            tempDotenv = Dotenv.load();
        } catch (Exception e) {
            System.out.println("Warning: .env file not found, using default database configuration");
        }
        dotenv = tempDotenv;

        DB_HOST = (dotenv != null && dotenv.get("DB_HOST") != null) ? dotenv.get("DB_HOST") : "localhost";
        DB_PORT = (dotenv != null && dotenv.get("DB_PORT") != null) ? dotenv.get("DB_PORT") : "3306";
        DB_NAME = (dotenv != null && dotenv.get("DB_NAME") != null) ? dotenv.get("DB_NAME") : "library";
        DB_USER = (dotenv != null && dotenv.get("DB_USER") != null) ? dotenv.get("DB_USER") : "root";
        DB_PASSWORD = (dotenv != null && dotenv.get("DB_PASSWORD") != null) ? dotenv.get("DB_PASSWORD") : "";
    }

    private static final String JDBC_URL = String.format(
            "jdbc:mysql://%s:%s/?allowMultiQueries=true&serverTimezone=UTC",
            DB_HOST, DB_PORT);
    private static final String DB_URL = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC",
            DB_HOST, DB_PORT, DB_NAME);

    private static volatile boolean initialized = false;

    public static Connection getConnection() throws Exception {
        if (!initialized) {
            synchronized (DatabaseConnection.class) {
                if (!initialized) {
                    if (!isDatabaseInitialized()) {
                        runSchemaSql();
                    } else {
                        System.out.println("Database already initialized, skipping schema execution");
                    }
                    initialized = true;
                }
            }
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static boolean isDatabaseInitialized() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as count FROM book")) {
                if (rs.next()) {
                    return rs.getInt("count") > 0;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
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

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            String[] queries = sql.split(";");
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    try {
                        stmt.execute(query);
                    } catch (SQLException e) {
                        System.err.println("Warning: SQL execution error: " + e.getMessage());
                    }
                }
            }
            System.out.println("Database schema initialized successfully");
        }
    }
}

