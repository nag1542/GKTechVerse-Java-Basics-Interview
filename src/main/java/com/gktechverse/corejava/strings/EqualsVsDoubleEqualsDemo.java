package com.gktechverse.corejava.strings;

/**
 * Interview focus: equals() vs == for Strings.
 * == compares reference, equals compares content.
 */
public class EqualsVsDoubleEqualsDemo {
    public static void main(String[] args) {
        String a = new String("interview");
        String b = new String("interview");

        System.out.println("=== equals() vs == Demo ===");
        System.out.println("a == b: " + (a == b));
        System.out.println("a.equals(b): " + a.equals(b));
    }
}
