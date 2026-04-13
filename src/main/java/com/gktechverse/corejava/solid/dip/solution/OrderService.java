package com.gktechverse.corejava.solid.dip.solution;

/**
 * DIP-compliant: high-level module depends on abstraction.
 */
public class OrderService {

    private final PaymentProcessor paymentProcessor;

    public OrderService(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void processOrder(Order order) {
        paymentProcessor.pay(order);
    }
}
