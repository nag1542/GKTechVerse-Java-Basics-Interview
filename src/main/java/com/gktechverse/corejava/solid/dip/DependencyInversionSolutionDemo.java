package com.gktechverse.corejava.solid.dip;

/**
 * Solution-only demo for Dependency Inversion Principle.
 */
public class DependencyInversionSolutionDemo {

    public static void main(String[] args) {
        com.gktechverse.corejava.solid.dip.solution.Order order =
                new com.gktechverse.corejava.solid.dip.solution.Order("ORD-DIP-200", 3999.00);

        com.gktechverse.corejava.solid.dip.solution.OrderService stripeOrderService =
                new com.gktechverse.corejava.solid.dip.solution.OrderService(
                        new com.gktechverse.corejava.solid.dip.solution.StripePaymentProcessor());
        stripeOrderService.processOrder(order);

        com.gktechverse.corejava.solid.dip.solution.OrderService payPalOrderService =
                new com.gktechverse.corejava.solid.dip.solution.OrderService(
                        new com.gktechverse.corejava.solid.dip.solution.PayPalPaymentProcessor());
        payPalOrderService.processOrder(order);

        System.out.println("DIP achieved: OrderService can switch payment provider without modification.");
    }
}
