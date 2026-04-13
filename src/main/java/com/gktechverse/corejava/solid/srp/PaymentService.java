package com.gktechverse.corejava.solid.srp;

public class PaymentService {

    public void charge(Payment payment, double amount) {
        System.out.println("[Payment] Charging " + amount + " using " + payment.getMethod() + " (ref=" + payment.getReference() + ")");
    }
}
