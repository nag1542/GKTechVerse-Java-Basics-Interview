package com.gktechverse.corejava.basics;

/**
 * Interview focus: widening and narrowing type casting.
 * Demonstrates implicit and explicit conversions.
 */
public class TypeCastingDemo {
    public static void main(String[] args) {
        int smallNumber = 100;
        long widened = smallNumber;

        double decimal = 45.89;
        int narrowed = (int) decimal;

        System.out.println("=== Type Casting Demo ===");
        System.out.println("Widening (int to long): " + widened);
        System.out.println("Narrowing (double to int): " + narrowed + " (fraction removed)");
    }
}
