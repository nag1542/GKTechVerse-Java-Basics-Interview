package com.gktechverse.corejava.solid.srp;

public class PricingService {

    public double calculateTotal(Order order) {
        double discount = order.getBaseAmount() > 1000 ? 100 : 0;
        double total = order.getBaseAmount() - discount;
        System.out.println("[Pricing] Base=" + order.getBaseAmount() + ", Discount=" + discount + ", Total=" + total);
        return total;
    }
}
