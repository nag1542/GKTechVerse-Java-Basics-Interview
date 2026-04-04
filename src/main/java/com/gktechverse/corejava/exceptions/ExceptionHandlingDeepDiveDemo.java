package com.gktechverse.corejava.exceptions;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Interview focus: broad exception swallowing vs production-safe handling in payment flow.
 */
public class ExceptionHandlingDeepDiveDemo {

    record Order(String orderId, BigDecimal amount, BigDecimal walletBalance) {}

    public static void main(String[] args) {
        System.out.println("=== Exception Handling Deep Dive in Java ===\n");

        Order lowBalanceOrder = new Order(UUID.randomUUID().toString(), BigDecimal.valueOf(2499.00), BigDecimal.valueOf(1200));
        Order gatewayFailureOrder = new Order(UUID.randomUUID().toString(), BigDecimal.valueOf(500), BigDecimal.valueOf(2000));

        badExample(lowBalanceOrder);
        goodExample(lowBalanceOrder);
        goodExample(gatewayFailureOrder);
    }

    private static void badExample(Order order) {
        System.out.println("--- BAD: Swallowing exception and returning false success ---");

        boolean orderPlaced = true;
        try {
            processPayment(order);
        } catch (Exception e) {
            // TODO: handle this later
        }

        if (orderPlaced) {
            System.out.println("User message: Order placed successfully.");
        }

        System.out.println("Reality: Payment failed silently. No log. No alert. No trace.\n");
    }

    private static void goodExample(Order order) {
        System.out.println("--- GOOD: Catch domain-specific exceptions and respond clearly ---");

        boolean orderPlaced = false;
        try {
            processPayment(order);
            orderPlaced = true;
        } catch (InsufficientFundsException e) {
            System.err.printf("[WARN] Payment rejected | orderId=%s code=%s required=%s available=%s%n",
                    order.orderId(), e.getErrorCode(), e.getRequired(), e.getAvailable());
            System.out.println("User message: Insufficient balance. Please add funds and retry.");
        } catch (PaymentGatewayException e) {
            System.err.printf("[ERROR] Gateway failure | orderId=%s code=%s message=%s%n",
                    order.orderId(), e.getErrorCode(), e.getMessage());
            System.err.printf("[TRACE] rootCause=%s%n", e.getCause());
            System.out.println("User message: Payment service is temporarily unavailable. Please retry later.");
        }

        if (orderPlaced) {
            System.out.println("User message: Order placed successfully.");
        }

        System.out.println();
    }

    private static void processPayment(Order order) {
        if (order.walletBalance().compareTo(order.amount()) < 0) {
            throw new InsufficientFundsException(order.amount(), order.walletBalance());
        }

        RuntimeException gatewayTimeout = new RuntimeException("HTTP 504 from payment provider");
        throw new PaymentGatewayException("Gateway timeout while charging card for order " + order.orderId(), gatewayTimeout);
    }
}
