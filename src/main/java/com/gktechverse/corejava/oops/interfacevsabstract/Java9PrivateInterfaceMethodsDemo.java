package com.gktechverse.corejava.oops.interfacevsabstract;

/**
 * Interview focus: Java 9 private methods in interfaces.
 * Demonstrates default method reusing a private helper.
 */
public class Java9PrivateInterfaceMethodsDemo {

    interface Validator<T> {
        boolean validate(T value);

        default boolean validateAndLog(T value) {
            boolean result = validate(value);
            log("validate", value, result);
            return result;
        }

        private void log(String action, T value, boolean result) {
            System.out.printf("[%s] %s -> %b%n", action, value, result);
        }
    }

    static class EmailValidator implements Validator<String> {
        @Override
        public boolean validate(String value) {
            return value != null && value.contains("@") && value.contains(".");
        }
    }

    public static void main(String[] args) {
        Validator<String> validator = new EmailValidator();

        System.out.println("=== Java 9 Private Interface Methods Demo ===");
        validator.validateAndLog("student@example.com");
        validator.validateAndLog("invalid-email");
    }
}
