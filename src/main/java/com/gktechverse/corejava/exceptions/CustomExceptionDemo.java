package com.gktechverse.corejava.exceptions;

/**
 * Interview focus: custom exception creation.
 * Defines and throws a user-defined checked exception.
 */
public class CustomExceptionDemo {
    static class InvalidAgeException extends Exception {
        public InvalidAgeException(String message) {
            super(message);
        }
    }

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 18) {
            throw new InvalidAgeException("Age must be 18+ for this action.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Custom Exception Demo ===");
        try {
            validateAge(16);
        } catch (InvalidAgeException e) {
            System.out.println("Custom exception caught: " + e.getMessage());
        }
    }
}
