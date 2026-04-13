package com.gktechverse.corejava.solid.dip.problem;

/**
 * DIP violation: high-level module depends directly on Stripe implementation.
 */
public class OrderService {

    private final PaymentService paymentService = new StripePaymentService();

    public void processOrder(Order order) {
        paymentService.pay(order);
    }
}
