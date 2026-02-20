package com.gktechverse.corejava.basics;

/**
 * Interview focus: variables in Java.
 * Shows local, instance, and static variables with simple output.
 */
public class VariablesDemo {
    private int instanceVar = 10;
    private static int staticVar = 20;

    public static void main(String[] args) {
        int localVar = 5;
        VariablesDemo demo = new VariablesDemo();

        System.out.println("=== Variables Demo ===");
        System.out.println("Local variable (inside method): " + localVar);
        System.out.println("Instance variable (belongs to object): " + demo.instanceVar);
        System.out.println("Static variable (shared by class): " + staticVar);
    }
}
