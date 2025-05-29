package org.LT3.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.io.InputStream;
import java.util.Scanner;

public class DatabaseConnection {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String DB_HOST = dotenv.get("DB_HOST");
    private static final String DB_PORT = dotenv.get("DB_PORT");
    private static final String DB_NAME = dotenv.get("DB_NAME");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    private static final String JDBC_URL = String.format(
            "jdbc:mysql://%s:%s/?allowMultiQueries=true&serverTimezone=UTC",
            DB_HOST, DB_PORT);
    private static final String DB_URL = String.format("jdbc:mysql://%s:%s/%s?serverTimezone=UTC",
            DB_HOST, DB_PORT, DB_NAME);

    private static boolean initialized = false;

    public static Connection getConnection() throws Exception {
        if (!initialized) {
            runSchemaSql();
            initialized = true;
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

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
                Statement stmt = conn.createStatement()) {

            String[] queries = sql.split(";");
            for (String query : queries) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query);
                }
            }
        }
    }
}
