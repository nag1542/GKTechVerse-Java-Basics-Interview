package com.gktechverse.corejava.jvm;

/**
 * Interview focus: autoboxing and unboxing.
 * Converts between primitives and wrapper classes.
 */
public class BoxingUnboxingDemo {
    public static void main(String[] args) {
        int primitive = 50;
        Integer boxed = primitive; // autoboxing
        int unboxed = boxed; // unboxing

        System.out.println("=== Boxing/Unboxing Demo ===");
        System.out.println("Primitive int: " + primitive);
        System.out.println("Autoboxed Integer: " + boxed);
        System.out.println("Unboxed int: " + unboxed);
    }
}
