package com.gktechverse.corejava.exceptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Interview focus: practical options for checked exceptions.
 */
public class CheckedExceptionHandlingOptionsDemo {

    public static void main(String[] args) {
        System.out.println("=== Checked Exceptions: 3 practical handling options ===");

        String missingPath = "missing-config.txt";

        readFileHandleHere(missingPath);

        try {
            readFileDeclareToCaller(missingPath);
        } catch (IOException e) {
            System.err.println("Caller handled declared IOException: " + e.getMessage());
        }

        try {
            readFileWrapUnchecked(missingPath);
        } catch (RuntimeException e) {
            System.err.println("Unchecked wrapper exception: " + e.getMessage());
        }

        System.out.println();
    }

    private static void readFileHandleHere(String path) {
        try {
            String content = Files.readString(Path.of(path));
            System.out.println(content);
        } catch (IOException e) {
            System.err.println("Handled locally - file not found/readable: " + e.getMessage());
        }
    }

    private static void readFileDeclareToCaller(String path) throws IOException {
        String content = Files.readString(Path.of(path));
        System.out.println(content);
    }

    private static void readFileWrapUnchecked(String path) {
        try {
            Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read config file", e);
        }
    }
}
