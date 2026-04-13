package com.gktechverse.corejava.solid.srp;

public class OrderRepository {

    public void save(Order order) {
        System.out.println("[Repository] Order saved to DB: " + order.getOrderId());
    }
}
