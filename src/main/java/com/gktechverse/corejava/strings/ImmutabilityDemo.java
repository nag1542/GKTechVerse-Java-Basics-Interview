package com.gktechverse.corejava.strings;

/**
 * Interview focus: String immutability.
 * Shows that modifying a String creates a new object.
 */
public class ImmutabilityDemo {
    public static void main(String[] args) {
        String original = "Core";
        String modified = original.concat(" Java");

        System.out.println("=== Immutability Demo ===");
        System.out.println("Original String: " + original);
        System.out.println("Modified String: " + modified);
        System.out.println("Original remains unchanged because String is immutable.");
    }
}
