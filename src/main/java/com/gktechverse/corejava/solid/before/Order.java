package com.gktechverse.corejava.solid.before;

import java.util.List;

public class Order {
    private final String orderId;
    private final String customerEmail;
    private final List<String> items;
    private final double baseAmount;

    public Order(String orderId, String customerEmail, List<String> items, double baseAmount) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.items = items;
        this.baseAmount = baseAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public List<String> getItems() {
        return items;
    }

    public double getBaseAmount() {
        return baseAmount;
    }
}
