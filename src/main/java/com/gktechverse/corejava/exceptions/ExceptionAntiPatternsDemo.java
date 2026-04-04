package com.gktechverse.corejava.exceptions;

/**
 * Interview focus: common exception anti-patterns and safer alternatives.
 */
public class ExceptionAntiPatternsDemo {

    static class User {
        private final String id;

        User(String id) {
            this.id = id;
        }

        String getId() {
            return id;
        }
    }

    static class EmailException extends Exception {
        EmailException(String message) {
            super(message);
        }
    }

    static class InsufficientStockException extends Exception {
        InsufficientStockException(String message) {
            super(message);
        }
    }

    static class PaymentDeclinedException extends Exception {
        PaymentDeclinedException(String message) {
            super(message);
        }
    }

    enum OrderResult {
        OUT_OF_STOCK,
        PAYMENT_FAILED,
        FAILED,
        SUCCESS
    }

    public static void main(String[] args) {
        System.out.println("=== 3 Exception Anti-Patterns ===");

        antiPattern1SwallowingException();
        antiPattern2CatchingTooBroadly();
        antiPattern3ExceptionsForFlowControl();

        System.out.println();
    }

    private static void antiPattern1SwallowingException() {
        System.out.println("-- Anti-Pattern 1: Swallowing exception --");
        User user = new User("U-101");

        // ❌ Bad: failure gets hidden.
        try {
            sendEmail(user);
        } catch (Exception e) {
            // TODO handle
        }
        System.out.println("Bad path: Email could fail silently. No log/alert/retry.");

        // ✅ Good: catch specific exception + log + action point.
        try {
            sendEmail(user);
        } catch (EmailException e) {
            System.err.printf("[ERROR] Failed to send email to userId=%s reason=%s%n", user.getId(), e.getMessage());
            System.out.println("[METRIC] email.failure incremented");
            System.out.println("Next step: retry or notify support.");
        }
    }

    private static void antiPattern2CatchingTooBroadly() {
        System.out.println("-- Anti-Pattern 2: Catching Exception too broadly --");

        // ❌ Bad: catches bugs and normal business exceptions together.
        try {
            processOrderBad(true);
        } catch (Exception e) {
            System.err.println("[WARN] Order processing failed");
            System.out.println("Bad path result: " + OrderResult.FAILED);
        }

        // ✅ Good: catch only expected business exceptions.
        try {
            OrderResult result = processOrderGood("OUT_OF_STOCK");
            System.out.println("Good path result: " + result);
        } catch (InsufficientStockException e) {
            System.out.println("Good path result: " + OrderResult.OUT_OF_STOCK);
        } catch (PaymentDeclinedException e) {
            System.out.println("Good path result: " + OrderResult.PAYMENT_FAILED);
        }
    }

    private static void antiPattern3ExceptionsForFlowControl() {
        System.out.println("-- Anti-Pattern 3: Using exceptions for flow control --");

        String userInput = "12x";

        // ❌ Bad: using exception as if/else.
        try {
            int value = Integer.parseInt(userInput);
            process(value);
        } catch (NumberFormatException e) {
            showError("Please enter a number");
        }

        // ✅ Good: validate first.
        if (userInput.matches("\\d+")) {
            process(Integer.parseInt(userInput));
        } else {
            showError("Please enter a number");
        }
    }

    private static void sendEmail(User user) throws EmailException {
        throw new EmailException("SMTP timeout for user " + user.getId());
    }

    private static void processOrderBad(boolean simulateBug) {
        if (simulateBug) {
            throw new NullPointerException("Order mapper was null");
        }
    }

    private static OrderResult processOrderGood(String scenario)
            throws InsufficientStockException, PaymentDeclinedException {
        if ("OUT_OF_STOCK".equals(scenario)) {
            throw new InsufficientStockException("Stock unavailable");
        }
        if ("PAYMENT_DECLINED".equals(scenario)) {
            throw new PaymentDeclinedException("Card declined");
        }
        return OrderResult.SUCCESS;
    }

    private static void process(int value) {
        System.out.println("Processing value: " + value);
    }

    private static void showError(String message) {
        System.out.println("Validation message: " + message);
    }
}
