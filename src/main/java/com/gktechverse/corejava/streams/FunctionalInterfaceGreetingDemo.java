package com.gktechverse.corejava.streams;

/**
 * Functional Interface demo showing old anonymous class style vs lambda style.
 */
public class FunctionalInterfaceGreetingDemo {

    // Functional Interface — exactly ONE abstract method
    @FunctionalInterface
    interface Greeting {
        String greet(String name); // the ONE abstract method

        default void printGreet(String name) { // default method does NOT count
            System.out.println(greet(name));
        }
    }

    public static void main(String[] args) {
        System.out.println("\n--- Functional Interface: Greeting Demo ---");

        // Old way — anonymous class
        Greeting g1 = new Greeting() {
            @Override
            public String greet(String name) {
                return "Hello " + name;
            }
        };

        // New way — lambda (identical result)
        Greeting g2 = name -> "Hello " + name;

        System.out.println("Old anonymous class output:");
        g1.printGreet("Alice");

        System.out.println("Lambda output:");
        g2.printGreet("Alice");
    }
}
