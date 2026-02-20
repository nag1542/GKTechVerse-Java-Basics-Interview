package com.gktechverse.corejava.jvm;

/**
 * Interview focus: JVM memory areas.
 * Prints a conceptual view of Stack, Heap, and Method Area usage.
 */
public class MemoryAreasDemo {
    private static String classLevelData = "Stored with class metadata";

    public static void main(String[] args) {
        int localValue = 10; // Stack
        MemoryAreasDemo object = new MemoryAreasDemo(); // reference on stack, object on heap

        System.out.println("=== JVM Memory Areas Demo ===");
        System.out.println("Local variable in method -> Stack: " + localValue);
        System.out.println("Object instance -> Heap: " + object);
        System.out.println("Static field -> Method Area/Metaspace concept: " + classLevelData);
    }
}
