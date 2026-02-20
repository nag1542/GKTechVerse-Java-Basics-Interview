package com.gktechverse.corejava.oops;

/**
 * Interview focus: interface vs abstract class.
 * Shows implementation differences in a compact example.
 */
public class InterfaceVsAbstractDemo {
    interface Flyable {
        void fly();
    }

    abstract static class Bird {
        abstract void sound();
    }

    static class Sparrow extends Bird implements Flyable {
        @Override
        public void fly() {
            System.out.println("Sparrow flies using wings.");
        }

        @Override
        void sound() {
            System.out.println("Sparrow chirps.");
        }
    }

    public static void main(String[] args) {
        Sparrow sparrow = new Sparrow();
        System.out.println("=== Interface vs Abstract Demo ===");
        sparrow.sound();
        sparrow.fly();
    }
}
