package com.gktechverse.corejava.solid.ocp;

/**
 * OCP violation: every new discount type forces modification here.
 */
public class ProblematicDiscountService {

    public double applyDiscount(Order order, String type) {
        double total = order.getTotal();

        if ("SEASONAL".equals(type)) return total * 0.85;
        if ("PREMIUM".equals(type)) return total * 0.90;
        if ("EMPLOYEE".equals(type)) return total * 0.70;

        return total;
    }
}
