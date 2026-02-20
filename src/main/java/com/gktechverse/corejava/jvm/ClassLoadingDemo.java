package com.gktechverse.corejava.jvm;

/**
 * Interview focus: class loading lifecycle.
 * Demonstrates when static block runs and class is initialized.
 */
public class ClassLoadingDemo {
    static {
        System.out.println("Static block executed: class loaded and initialized.");
    }

    public static void main(String[] args) {
        System.out.println("=== Class Loading Demo ===");
        System.out.println("Main method started after class initialization.");
    }
}
