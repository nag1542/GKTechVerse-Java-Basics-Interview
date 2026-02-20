package com.gktechverse.corejava.oops;

/**
 * Interview focus: abstraction.
 * Uses abstract class to hide implementation details.
 */
public class AbstractionDemo {
    abstract static class Vehicle {
        abstract void start();
    }

    static class Car extends Vehicle {
        @Override
        void start() {
            System.out.println("Car starts with a key/button.");
        }
    }

    public static void main(String[] args) {
        Vehicle vehicle = new Car();
        System.out.println("=== Abstraction Demo ===");
        vehicle.start();
    }
}
