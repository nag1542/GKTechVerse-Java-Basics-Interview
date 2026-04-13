package com.gktechverse.corejava.solid.srp;

public class Customer {
    private final String customerId;
    private final String email;

    public Customer(String customerId, String email) {
        this.customerId = customerId;
        this.email = email;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getEmail() {
        return email;
    }
}
