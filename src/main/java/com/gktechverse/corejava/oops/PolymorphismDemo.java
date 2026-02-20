package com.gktechverse.corejava.oops;

/**
 * Interview focus: polymorphism.
 * Demonstrates method overloading and overriding.
 */
public class PolymorphismDemo {
    static class Calculator {
        int add(int a, int b) {
            return a + b;
        }

        double add(double a, double b) {
            return a + b;
        }
    }

    static class Parent {
        void show() {
            System.out.println("Parent method");
        }
    }

    static class Child extends Parent {
        @Override
        void show() {
            System.out.println("Child overridden method");
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Parent ref = new Child();

        System.out.println("=== Polymorphism Demo ===");
        System.out.println("Overloading add(int, int): " + calculator.add(2, 3));
        System.out.println("Overloading add(double, double): " + calculator.add(2.5, 3.5));
        ref.show();
    }
}
