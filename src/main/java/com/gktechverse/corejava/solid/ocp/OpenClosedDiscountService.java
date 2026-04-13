package com.gktechverse.corejava.solid.ocp;

/**
 * OCP-compliant service: closed for modification, open for extension via strategies.
 */
public class OpenClosedDiscountService {

    public double applyDiscount(Order order, DiscountStrategy strategy) {
        return strategy.apply(order.getTotal());
    }
}
