package com.gktechverse.corejava.solid.dip.problem;

public class Order {
    private final String orderId;
    private final double amount;

    public Order(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getAmount() {
        return amount;
    }
}
