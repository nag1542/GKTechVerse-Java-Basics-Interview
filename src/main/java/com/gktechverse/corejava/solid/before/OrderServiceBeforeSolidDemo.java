package com.gktechverse.corejava.solid.before;

import java.util.List;

/**
 * Entry point to run the pre-SOLID design issue demo.
 */
public class OrderServiceBeforeSolidDemo {

    public static void main(String[] args) {
        OrderService orderService = new OrderService();

        Order order = new Order("ORD-1001", "customer@gktechverse.com", List.of("Laptop", "Mouse"), 1500.00);
        orderService.processOrder(order);
    }
}
