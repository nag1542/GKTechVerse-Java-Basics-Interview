package com.gktechverse.corejava.strings;

/**
 * Interview focus: String pool behavior.
 * Demonstrates pooled literals and heap strings.
 */
public class StringPoolDemo {
    public static void main(String[] args) {
        String first = "java";
        String second = "java";
        String third = new String("java");

        System.out.println("=== String Pool Demo ===");
        System.out.println("first == second: " + (first == second) + " (same pooled reference)");
        System.out.println("first == third: " + (first == third) + " (new object on heap)");
    }
}
