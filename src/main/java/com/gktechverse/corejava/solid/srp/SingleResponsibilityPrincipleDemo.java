package com.gktechverse.corejava.solid.srp;

import java.util.List;

/**
 * SRP solution demo:
 * Each class has one responsibility, and OrderPlacementService coordinates them.
 */
public class SingleResponsibilityPrincipleDemo {

    public static void main(String[] args) {
        OrderPlacementService orderPlacementService = new OrderPlacementService(
                new OrderValidator(),
                new PricingService(),
                new PaymentService(),
                new NotificationService(),
                new OrderRepository(),
                new InventoryService()
        );

        Customer customer = new Customer("C-100", "buyer@gktechverse.com");
        Order order = new Order("ORD-2001", customer, List.of("Laptop", "Mouse"), 1500.00);
        Payment payment = new Payment("UPI", "TXN-887766");

        orderPlacementService.placeOrder(order, payment);

        System.out.println("SRP achieved: changes in validation/pricing/payment won't impact other classes.");
    }
}
