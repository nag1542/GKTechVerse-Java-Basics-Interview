package com.gktechverse.corejava.oops;

/**
 * Interview focus: constructors.
 * Shows default-like and parameterized constructor usage.
 */
public class ConstructorDemo {
    private String course;

    public ConstructorDemo() {
        this.course = "Core Java";
    }

    public ConstructorDemo(String course) {
        this.course = course;
    }

    public static void main(String[] args) {
        ConstructorDemo first = new ConstructorDemo();
        ConstructorDemo second = new ConstructorDemo("Advanced Java Basics");

        System.out.println("=== Constructor Demo ===");
        System.out.println("No-arg constructor value: " + first.course);
        System.out.println("Parameterized constructor value: " + second.course);
    }
}
