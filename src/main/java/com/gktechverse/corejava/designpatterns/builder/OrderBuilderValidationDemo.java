package com.gktechverse.corejava.designpatterns.builder;

import java.util.Collections;
import java.util.List;

public class OrderBuilderValidationDemo {

    public static void main(String[] args) {
        try {
            Order invalidOrder = new Order.Builder()
                    .items(Collections.emptyList())
                    .amount(-50)
                    .build();

            System.out.println(invalidOrder);
        } catch (RuntimeException ex) {
            System.out.println("Expected validation error: " + ex.getMessage());
        }

        Order validOrder = new Order.Builder()
                .items(List.of("Laptop", "Mouse"))
                .amount(1250)
                .build();

        System.out.println("Valid order created. Amount: " + validOrder.getAmount());
    }
}
