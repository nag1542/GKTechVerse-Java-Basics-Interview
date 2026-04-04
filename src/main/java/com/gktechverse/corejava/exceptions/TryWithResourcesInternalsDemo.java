package com.gktechverse.corejava.exceptions;

/**
 * Interview focus: why try-with-resources is safer than manual finally.
 */
public class TryWithResourcesInternalsDemo {

    static class DemoResource implements AutoCloseable {
        private final String name;
        private final boolean throwOnClose;

        DemoResource(String name, boolean throwOnClose) {
            this.name = name;
            this.throwOnClose = throwOnClose;
            System.out.println("Opened " + name);
        }

        void doWork() {
            System.out.println("Working with " + name);
        }

        @Override
        public void close() {
            System.out.println("Closing " + name);
            if (throwOnClose) {
                throw new RuntimeException(name + " close failed");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== try-with-resources: internal behavior and close ordering ===");

        try {
            preJava7FragileFinally();
        } catch (RuntimeException e) {
            System.err.println("Manual finally risk: stmt.close() interrupted conn.close().");
        }

        java7TryWithResources();
        suppressedExceptionMentalModel();
        System.out.println();
    }

    private static void preJava7FragileFinally() {
        System.out.println("Pre-Java 7 style (manual finally):");
        DemoResource conn = null;
        DemoResource stmt = null;
        try {
            conn = new DemoResource("Connection", false);
            stmt = new DemoResource("Statement", true);
            stmt.doWork();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static void java7TryWithResources() {
        try {
            System.out.println("Java 7+ try-with-resources style:");
            try (DemoResource conn = new DemoResource("Connection", false);
                 DemoResource stmt = new DemoResource("Statement", true)) {
                stmt.doWork();
            }
        } catch (RuntimeException e) {
            System.err.println("Caught: " + e.getMessage());
        }
    }

    private static void suppressedExceptionMentalModel() {
        try {
            System.out.println("Suppressed behavior (primary + close failure):");
            try (DemoResource conn = new DemoResource("Connection", false);
                 DemoResource stmt = new DemoResource("Statement", true)) {
                stmt.doWork();
                throw new RuntimeException("Simulated primary exception from try block");
            }
        } catch (RuntimeException e) {
            System.err.println("Primary exception: " + e.getMessage());
            if (e.getSuppressed().length > 0) {
                System.err.println("Suppressed close failure: " + e.getSuppressed()[0].getMessage());
            }
        }
    }
}
