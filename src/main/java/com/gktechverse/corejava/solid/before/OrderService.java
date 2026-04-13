package com.gktechverse.corejava.solid.before;

/**
 * Problem before SOLID:
 * One service is doing too many responsibilities.
 *
 * Real-world scenario:
 * E-commerce checkout where validation, pricing, discounting, payment,
 * notification, persistence, and inventory update are tightly coupled.
 */
public class OrderService {

    public void processOrder(Order order) {
        validate(order);
        double total = calculateTotal(order);
        applyDiscount(order);
        chargePayment(order);
        sendEmail(order);
        saveToDatabase(order);
        updateInventory(order);

        System.out.println("Order processed with total: " + total);
        System.out.println("Issue: this single class violates SRP and is hard to test/change.");
    }

    private void validate(Order order) {
        System.out.println("Validating order: " + order.getOrderId());
    }

    private double calculateTotal(Order order) {
        System.out.println("Calculating total for items: " + order.getItems());
        return order.getBaseAmount();
    }

    private void applyDiscount(Order order) {
        System.out.println("Applying discount rules for order: " + order.getOrderId());
    }

    private void chargePayment(Order order) {
        System.out.println("Charging payment for order: " + order.getOrderId());
    }

    private void sendEmail(Order order) {
        System.out.println("Sending confirmation email to: " + order.getCustomerEmail());
    }

    private void saveToDatabase(Order order) {
        System.out.println("Saving order in database: " + order.getOrderId());
    }

    private void updateInventory(Order order) {
        System.out.println("Updating inventory for items: " + order.getItems());
    }
}
