package com.gktechverse.corejava.solid.srp;

public class OrderValidator {

    public void validate(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }
        if (order.getBaseAmount() <= 0) {
            throw new IllegalArgumentException("Order amount must be greater than zero.");
        }

        System.out.println("[Validator] Order is valid: " + order.getOrderId());
    }
}
