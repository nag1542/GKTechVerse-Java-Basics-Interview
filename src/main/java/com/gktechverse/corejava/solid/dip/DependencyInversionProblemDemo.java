package com.gktechverse.corejava.solid.dip;

/**
 * Problem-only demo for Dependency Inversion Principle.
 */
public class DependencyInversionProblemDemo {

    public static void main(String[] args) {
        com.gktechverse.corejava.solid.dip.problem.OrderService orderService =
                new com.gktechverse.corejava.solid.dip.problem.OrderService();

        com.gktechverse.corejava.solid.dip.problem.Order order =
                new com.gktechverse.corejava.solid.dip.problem.Order("ORD-DIP-100", 2499.00);

        orderService.processOrder(order);
        System.out.println("Issue: OrderService is tightly coupled to Stripe implementation.");
    }
}
