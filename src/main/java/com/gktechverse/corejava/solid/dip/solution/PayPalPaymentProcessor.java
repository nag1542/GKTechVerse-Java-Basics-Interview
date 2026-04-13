package com.gktechverse.corejava.solid.dip.solution;

public class PayPalPaymentProcessor implements PaymentProcessor {
    @Override
    public void pay(Order order) {
        System.out.println("[PayPal] Charged order " + order.getOrderId() + " amount " + order.getAmount());
    }
}
