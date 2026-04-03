package com.gktechverse.corejava.exceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

/**
 * Interview focus: multi-catch, finally behavior, and exception chaining.
 */
public class MultiCatchFinallyAndChainingDemo {

    static class ServiceException extends RuntimeException {
        public ServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class DataException extends RuntimeException {
        public DataException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class ConfigException extends RuntimeException {
        public ConfigException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static class FakeDbConnection {
        private final String name;

        FakeDbConnection(String name) {
            this.name = name;
            System.out.println("Opened DB connection: " + name);
        }

        void query() throws SQLException {
            throw new SQLException("Syntax error near FROM");
        }

        void close() throws SQLException {
            System.out.println("Closing DB connection: " + name);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Multi-catch, finally, and exception chaining ===");

        runMultiCatchExample();
        runFinallyRethrowExample();
        runExceptionChainingExample();
        System.out.println();
    }

    private static void runMultiCatchExample() {
        System.out.println("-- Multi-catch example --");
        try {
            try {
                riskyOperation();
            } catch (IOException | SQLException | TimeoutException e) {
                System.err.println("Operation failed: " + e.getClass().getSimpleName() + " -> " + e.getMessage());
                throw new ServiceException("Failed to complete service operation", e);
            }
        } catch (ServiceException e) {
            System.err.println("Wrapped as ServiceException with cause: " + e.getCause().getClass().getSimpleName());
        }
    }

    private static void runFinallyRethrowExample() {
        System.out.println("-- finally executes even when catch rethrows --");
        FakeDbConnection conn = null;

        try {
            conn = new FakeDbConnection("orders-db");
            conn.query();
        } catch (SQLException e) {
            try {
                throw new DataException("Query failed", e);
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException closeEx) {
                        System.err.println("Close failed: " + closeEx.getMessage());
                    }
                }
            }
        } catch (DataException e) {
            System.err.println("Rethrown DataException: " + e.getMessage() + " | cause=" + e.getCause().getClass().getSimpleName());
        }
    }

    private static void runExceptionChainingExample() {
        System.out.println("-- Exception chaining (preserve root cause) --");
        try {
            loadConfig(Path.of("missing-app-config.yml"));
        } catch (ConfigException e) {
            System.err.println("Config exception message: " + e.getMessage());
            System.err.println("Root cause: " + e.getCause().getClass().getSimpleName() + " -> " + e.getCause().getMessage());
        }
    }

    private static void riskyOperation() throws IOException, SQLException, TimeoutException {
        throw new TimeoutException("payment partner did not respond in 3s");
    }

    private static void loadConfig(Path path) {
        try {
            Files.readString(path);
        } catch (IOException e) {
            throw new ConfigException("Cannot load config", e);
        }
    }
}
