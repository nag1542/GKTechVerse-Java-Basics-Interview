package com.gktechverse.corejava.exceptions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Interview focus: checked vs unchecked exceptions.
 * Shows compile-time checked exception and runtime exception handling.
 */
public class CheckedVsUncheckedDemo {
    public static void main(String[] args) {
        System.out.println("=== Checked vs Unchecked Demo ===");

        try (BufferedReader reader = new BufferedReader(new FileReader("missing-file.txt"))) {
            System.out.println(reader.readLine());
        } catch (IOException e) {
            System.out.println("Checked exception handled: " + e.getClass().getSimpleName());
        }

        try {
            int result = 10 / 0;
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Unchecked exception handled: " + e.getClass().getSimpleName());
        }
    }
}
