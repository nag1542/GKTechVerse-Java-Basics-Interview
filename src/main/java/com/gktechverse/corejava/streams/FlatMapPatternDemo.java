package com.gktechverse.corejava.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Pattern demo: flatMap to flatten nested collections.
 */
public class FlatMapPatternDemo {

    public static void main(String[] args) {
        System.out.println("\n4) flatMap pattern (flatten nested lists)");

        List<Order> orders = getOrders();

        // map() would produce Stream<List<Item>> (nested)
        List<List<Item>> nestedItems = orders.stream()
                .map(Order::getItems)
                .collect(Collectors.toList());

        // flatMap() produces Stream<Item> (flat)
        List<Item> allItems = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.toList());

        System.out.println("Orders: " + orders);
        System.out.println("map result (nested lists): " + nestedItems);
        System.out.println("flatMap result (all items): " + allItems);
    }

    private static List<Order> getOrders() {
        return Arrays.asList(
                new Order("ORD-101", Arrays.asList(
                        new Item("Laptop", 1),
                        new Item("Mouse", 2)
                )),
                new Order("ORD-102", Arrays.asList(
                        new Item("Keyboard", 1),
                        new Item("Monitor", 2)
                )),
                new Order("ORD-103", Arrays.asList(
                        new Item("USB-C Cable", 3)
                ))
        );
    }

    static class Order {
        private final String orderId;
        private final List<Item> items;

        Order(String orderId, List<Item> items) {
            this.orderId = orderId;
            this.items = items;
        }

        List<Item> getItems() {
            return items;
        }

        @Override
        public String toString() {
            return "Order{" +
                    "orderId='" + orderId + '\'' +
                    ", items=" + items +
                    '}';
        }
    }

    static class Item {
        private final String name;
        private final int quantity;

        Item(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "name='" + name + '\'' +
                    ", quantity=" + quantity +
                    '}';
        }
    }
}
