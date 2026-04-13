package com.gktechverse.corejava.solid.ocp;

public class PremiumDiscount implements DiscountStrategy {
    @Override
    public double apply(double total) {
        return total * 0.90;
    }
}
