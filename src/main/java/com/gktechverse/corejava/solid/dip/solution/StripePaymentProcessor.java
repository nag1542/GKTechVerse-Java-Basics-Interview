package com.gktechverse.corejava.solid.dip.solution;

public class StripePaymentProcessor implements PaymentProcessor {
    @Override
    public void pay(Order order) {
        System.out.println("[Stripe] Charged order " + order.getOrderId() + " amount " + order.getAmount());
    }
}
