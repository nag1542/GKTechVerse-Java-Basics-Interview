package com.gktechverse.corejava.solid.ocp;

public class Order {
    private final String orderId;
    private final double total;

    public Order(String orderId, double total) {
        this.orderId = orderId;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getTotal() {
        return total;
    }
}
