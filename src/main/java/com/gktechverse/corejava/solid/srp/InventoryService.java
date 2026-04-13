package com.gktechverse.corejava.solid.srp;

public class InventoryService {

    public void reduceStock(Order order) {
        System.out.println("[Inventory] Stock reduced for items: " + order.getItems());
    }
}
