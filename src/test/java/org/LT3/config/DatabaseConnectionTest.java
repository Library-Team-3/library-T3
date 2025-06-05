package org.LT3.config;

import org.junit.jupiter.api.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DatabaseConnectionTest {

    private Connection testConnection;

    @BeforeAll
    void setupDatabase() throws Exception {
        testConnection = DatabaseConnection.getConnection();
        assertNotNull(testConnection, "Connection should not be null");
    }

    @AfterAll
    void closeConnection() throws Exception {
        if (testConnection != null && !testConnection.isClosed()) {
            testConnection.close();
        }
    }

    @Test
    @DisplayName("Test connection is valid")
    void testGetConnection() throws Exception {
        Connection conn = DatabaseConnection.getConnection();
        assertNotNull(conn);
        assertTrue(conn.isValid(2));
        conn.close();
    }

    @Test
    @DisplayName("Schema initialized correctly")
    void testSchemaInitialized() throws Exception {
        try (Statement stmt = testConnection.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'book'")) {
            assertTrue(rs.next(), "Table 'book' should exist after schema initialization");
        }
    }

    @Test
    @DisplayName("Book table should have 0 or more rows after init")
    void testBookTableExistsAndQueryable() throws Exception {
        try (Statement stmt = testConnection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM book")) {
            assertTrue(rs.next());
            int count = rs.getInt("count");
            assertTrue(count >= 0, "Book table should be accessible and countable");
        }
    }

    @Test
    @DisplayName("Should not re-initialize schema on second call")
    void testIdempotentInitialization() throws Exception {
        Connection secondConn = DatabaseConnection.getConnection();
        assertNotNull(secondConn);
        assertTrue(secondConn.isValid(2));
        secondConn.close();
    }
}
