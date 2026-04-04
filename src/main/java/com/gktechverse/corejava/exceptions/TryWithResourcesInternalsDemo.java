package com.gktechverse.corejava.exceptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Interview focus: pre-Java 7 manual close vs Java 7 try-with-resources using the same known I/O APIs.
 */
public class TryWithResourcesInternalsDemo {

    /**
     * Custom writer only for teaching: simulate close failure so we can observe close-order behavior.
     */
    static class FailingPrintWriter extends PrintWriter {
        FailingPrintWriter(File file) throws IOException {
            super(file);
        }

        @Override
        public void close() {
            super.close();
            throw new RuntimeException("PrintWriter close failed");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== try-with-resources internals (BufferedReader + PrintWriter) ===");

        seedInputFile();

        try {
            preJava7FragileFinally();
        } catch (RuntimeException e) {
            System.err.println("Pre-Java 7 issue: writer close threw, reader close was skipped.");
        }

        java7TryWithResources();
        compilerFlowMentalModel();
        System.out.println();
    }

    private static void seedInputFile() {
        try (PrintWriter seedWriter = new PrintWriter(new File("test.txt"))) {
            seedWriter.println("Try-with-resources copied this line.");
        } catch (IOException e) {
            System.err.println("Unable to create sample input file: " + e.getMessage());
        }
    }

    private static void preJava7FragileFinally() {
        System.out.println("Pre-Java 7 (manual finally):");

        BufferedReader br = null;
        FailingPrintWriter writer = null;

        try {
            br = new BufferedReader(new FileReader("test.txt"));
            writer = new FailingPrintWriter(new File("output_pre_java7.txt"));

            String line = br.readLine();
            writer.println(line);
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        } finally {
            // If writer.close() throws, br.close() line below is never reached.
            if (writer != null) {
                writer.close();
            }
            // Resource-leak risk in pre-Java7 manual close style.
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("Reader close failed: " + e.getMessage());
                }
            }
        }
    }

    private static void java7TryWithResources() {
        System.out.println("Java 7+ try-with-resources:");

        try (BufferedReader br = new BufferedReader(new FileReader("test.txt"));
             FailingPrintWriter writer = new FailingPrintWriter(new File("output_java7.txt"))) {

            String line = br.readLine();
            writer.println(line);
            System.out.println("Copied first line from test.txt to output_java7.txt");

        } catch (IOException e) {
            System.err.println("I/O handling failed: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Close failure captured by try-with-resources: " + e.getMessage());
            if (e.getSuppressed().length > 0) {
                System.err.println("Suppressed exceptions count: " + e.getSuppressed().length);
            }
        }
    }

    private static void compilerFlowMentalModel() {
        System.out.println("Compiler mental model:");
        System.out.println("- Resources close automatically in reverse order (writer then reader).");
        System.out.println("- If close throws, Java still attempts closing remaining resources.");
        System.out.println("- Additional close failures are attached as suppressed exceptions.");
    }
}
