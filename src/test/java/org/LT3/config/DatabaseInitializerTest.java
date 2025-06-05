package org.LT3.config;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class DatabaseInitializerTest {

    @Test
    void testInitializeSuccess() throws Exception {
        Connection mockConnection = mock(Connection.class);

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(mockConnection);

            DatabaseInitializer.initialize();

            verify(mockConnection, times(1)).close();
        }
    }

    @Test
    void testInitializeConnectionFailure() {
        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenThrow(new RuntimeException("Mock DB failure"));

            assertDoesNotThrow(DatabaseInitializer::initialize);
        }
    }

    @Test
    void testInitializeCloseFailure() throws Exception {
        Connection mockConnection = mock(Connection.class);
        doThrow(new SQLException("Mock close failure")).when(mockConnection).close();

        try (MockedStatic<DatabaseConnection> mocked = mockStatic(DatabaseConnection.class)) {
            mocked.when(DatabaseConnection::getConnection).thenReturn(mockConnection);

            assertDoesNotThrow(DatabaseInitializer::initialize);
        }
    }
}
