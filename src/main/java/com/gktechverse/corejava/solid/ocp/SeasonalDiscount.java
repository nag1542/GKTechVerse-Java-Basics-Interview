package com.gktechverse.corejava.solid.ocp;

public class SeasonalDiscount implements DiscountStrategy {
    @Override
    public double apply(double total) {
        return total * 0.85;
    }
}
