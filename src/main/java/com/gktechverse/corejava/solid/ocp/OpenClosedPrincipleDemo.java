package com.gktechverse.corejava.solid.ocp;

/**
 * Real-world use case: e-commerce discount engine.
 *
 * Problem: string-based if/else chain grows whenever a new discount is introduced.
 * Solution: strategy-based extension without touching existing service logic.
 */
public class OpenClosedPrincipleDemo {

    public static void main(String[] args) {
        Order order = new Order("ORD-OCP-101", 1000.00);

        ProblematicDiscountService badService = new ProblematicDiscountService();
        double badPremiumTotal = badService.applyDiscount(order, "PREMIUM");
        System.out.println("Before OCP (if/else): PREMIUM total = " + badPremiumTotal);

        OpenClosedDiscountService goodService = new OpenClosedDiscountService();
        System.out.println("After OCP (strategy): PREMIUM total = " + goodService.applyDiscount(order, new PremiumDiscount()));
        System.out.println("After OCP (strategy): SEASONAL total = " + goodService.applyDiscount(order, new SeasonalDiscount()));
        System.out.println("After OCP (strategy): EMPLOYEE total = " + goodService.applyDiscount(order, new EmployeeDiscount()));

        // New feature: Student discount added by creating new strategy class only.
        System.out.println("After OCP (new feature): STUDENT total = " + goodService.applyDiscount(order, new StudentDiscount()));
    }
}
