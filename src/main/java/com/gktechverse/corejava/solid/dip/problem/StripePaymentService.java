package com.gktechverse.corejava.solid.dip.problem;

public class StripePaymentService implements PaymentService {
    @Override
    public void pay(Order order) {
        System.out.println("[Stripe] Charged order " + order.getOrderId() + " amount " + order.getAmount());
    }
}
