package com.gktechverse.corejava.solid.ocp;

/**
 * New feature added without changing existing discount service code.
 */
public class StudentDiscount implements DiscountStrategy {
    @Override
    public double apply(double total) {
        return total * 0.80;
    }
}
