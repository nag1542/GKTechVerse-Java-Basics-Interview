package com.gktechverse.corejava.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * Separate class for memory-focused ArrayList usage patterns.
 */
public class ArrayListMemoryOptimizationPatternsDemo {

    public static void main(String[] args) {
        System.out.println("=== ArrayList Memory Optimization Patterns ===");
        patternOnePreSize();
        patternTwoEnsureCapacity();
        patternThreeTrimToSize();
    }

    private static void patternOnePreSize() {
        System.out.println("\nPattern 1 - Pre-size when you know the count");
        System.out.println("- Default constructor may trigger multiple resizes for large loads.");
        System.out.println("- Better: new ArrayList<>(expectedCount) to avoid repeated Array.copyOf during add loop.");
        System.out.println("- Example growth with default strategy: 10 -> 15 -> 22 -> 33 -> 49 -> 73 -> 109 ...");

        int expectedCount = 5;
        List<String> preSized = new ArrayList<>(expectedCount);
        for (int i = 1; i <= expectedCount; i++) {
            preSized.add("user-" + i);
        }
        System.out.println("Demo pre-sized list: " + preSized);
    }

    private static void patternTwoEnsureCapacity() {
        System.out.println("\nPattern 2 - ensureCapacity() for batch adds");
        ArrayList<String> results = new ArrayList<>();
        int batchSize = 5;
        results.ensureCapacity(batchSize);

        for (int i = 1; i <= batchSize; i++) {
            results.add("item-" + i);
        }
        System.out.println("Demo ensureCapacity list: " + results);
    }

    private static void patternThreeTrimToSize() {
        System.out.println("\nPattern 3 - trimToSize() after bulk loading");
        System.out.println("- Use when list is built once and then mostly read-only.");
        System.out.println("- Avoid if list will continue growing frequently.");

        ArrayList<String> catalogue = new ArrayList<>();
        catalogue.ensureCapacity(20);
        catalogue.add("P-101");
        catalogue.add("P-102");
        catalogue.add("P-103");
        catalogue.trimToSize();

        System.out.println("Demo trimToSize list: " + catalogue);
    }
}
