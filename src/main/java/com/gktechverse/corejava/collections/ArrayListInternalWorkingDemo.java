package com.gktechverse.corejava.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * Session: How ArrayList works internally in Java.
 *
 * Focus areas:
 * 1) Internal structure (dynamic array backed by Object[])
 * 2) add() behavior and resize algorithm
 * 3) Big-O operation complexity
 */
public class ArrayListInternalWorkingDemo {

    public static void main(String[] args) {
        System.out.println("=== How ArrayList Works Internally in Java ===");

        basicExample();
        internalStructure();
        addAndResizeAlgorithm();
        operationComplexity();
    }

    private static void basicExample() {
        System.out.println("\n1) Basic Example");
        List<String> names = new ArrayList<>();
        names.add("Anita");
        names.add("Rahul");
        names.add("Zoya");

        System.out.println("List<String> names = new ArrayList<>();");
        System.out.println("Names stored: " + names);
    }

    private static void internalStructure() {
        System.out.println("\n2) ArrayList Internal Structure");
        System.out.println("- ArrayList is backed by a resizable array (Object[]).");
        System.out.println("- It keeps a logical size (number of elements currently present).");
        System.out.println("- It also has a capacity (actual array length). Capacity can be >= size.");
        System.out.println("- get(index) is fast because it directly uses array index lookup.");
    }

    private static void addAndResizeAlgorithm() {
        System.out.println("\n3) How add() works and resize algorithm");
        System.out.println("- add(E e) first checks whether current array has free slot.");
        System.out.println("- If yes: element is written at index=size, then size++.");
        System.out.println("- If no: ArrayList grows capacity before writing the new element.");
        System.out.println("- Typical growth formula in modern JDKs: newCapacity = oldCapacity + (oldCapacity >> 1) (~1.5x).");
        System.out.println("- Then old elements are copied into the new larger array.");

        System.out.println("Simplified flow:");
        System.out.println("  add(e) -> ensureCapacity(size + 1) -> grow() if needed -> elementData[size] = e -> size++");
        System.out.println("Capacity growth example: 10 -> 15 -> 22 -> 33 -> 49 -> 73");

        List<Integer> sample = new ArrayList<>(2);
        sample.add(10);
        sample.add(20);
        sample.add(30); // triggers resize from small initial capacity

        System.out.println("Demo with small initial capacity: new ArrayList<>(2) then add 3 elements -> " + sample);
        System.out.println("Interview takeaway: occasional resize is expensive, but amortized append remains efficient.");
    }

    private static void operationComplexity() {
        System.out.println("\n4) Operation Complexity (Interview Cheat Sheet)");
        System.out.println("- get(index): O(1)");
        System.out.println("- set(index, value): O(1)");
        System.out.println("- add(e) at end: O(1) amortized, O(n) when resize happens");
        System.out.println("- add(index, e): O(n) (shift elements)");
        System.out.println("- remove(index): O(n) (shift elements)");
        System.out.println("- contains(e): O(n)");
        System.out.println("- iteration: O(n)");
    }
}
