package com.gktechverse.corejava.solid.srp;

import java.util.List;

public class Order {
    private final String orderId;
    private final Customer customer;
    private final List<String> items;
    private final double baseAmount;

    public Order(String orderId, Customer customer, List<String> items, double baseAmount) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = items;
        this.baseAmount = baseAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<String> getItems() {
        return items;
    }

    public double getBaseAmount() {
        return baseAmount;
    }
}
