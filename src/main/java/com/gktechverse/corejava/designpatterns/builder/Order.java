package com.gktechverse.corejava.designpatterns.builder;

import java.util.List;

public class Order {
    private final List<String> items;
    private final double amount;

    private Order(Builder builder) {
        this.items = builder.items;
        this.amount = builder.amount;
    }

    public List<String> getItems() {
        return items;
    }

    public double getAmount() {
        return amount;
    }

    public static class Builder {
        private List<String> items;
        private double amount;

        public Builder items(List<String> items) {
            this.items = items;
            return this;
        }

        public Builder amount(double amount) {
            this.amount = amount;
            return this;
        }

        public Order build() {
            if (items == null || items.isEmpty()) {
                throw new IllegalStateException("Order must have items");
            }

            if (amount <= 0) {
                throw new IllegalArgumentException("Invalid amount");
            }

            return new Order(this);
        }
    }
}
