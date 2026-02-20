package com.gktechverse.corejava.exceptions;

/**
 * Interview focus: try-catch-finally flow.
 * Finally block executes whether exception occurs or not.
 */
public class TryCatchFinallyDemo {
    public static void main(String[] args) {
        System.out.println("=== Try-Catch-Finally Demo ===");

        try {
            String text = null;
            System.out.println(text.length());
        } catch (NullPointerException e) {
            System.out.println("Caught exception: " + e.getClass().getSimpleName());
        } finally {
            System.out.println("Finally block always runs.");
        }
    }
}
