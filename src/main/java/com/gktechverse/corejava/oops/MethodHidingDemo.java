package com.gktechverse.corejava.oops;

/**
 * Interview focus: method hiding with static methods.
 * Static methods are hidden, not overridden.
 */
public class MethodHidingDemo {
    static class Parent {
        static void message() {
            System.out.println("Static method in Parent");
        }
    }

    static class Child extends Parent {
        static void message() {
            System.out.println("Static method in Child (hidden)");
        }
    }

    public static void main(String[] args) {
        Parent ref = new Child();
        System.out.println("=== Method Hiding Demo ===");
        Parent.message();
        Child.message();
        ref.message();
        System.out.println("Call using reference type resolves to Parent for static methods.");
    }
}
