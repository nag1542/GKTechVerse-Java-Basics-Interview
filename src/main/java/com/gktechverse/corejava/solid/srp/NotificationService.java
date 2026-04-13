package com.gktechverse.corejava.solid.srp;

public class NotificationService {

    public void sendConfirmation(Customer customer, Order order) {
        System.out.println("[Notification] Confirmation sent to " + customer.getEmail() + " for order " + order.getOrderId());
    }
}
