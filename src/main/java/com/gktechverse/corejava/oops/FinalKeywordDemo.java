package com.gktechverse.corejava.oops;

/**
 * Interview focus: final keyword.
 * Demonstrates final variable, method, and class concepts.
 */
public class FinalKeywordDemo {
    static final class Utility {
        final int max = 100;

        final void printLimit() {
            System.out.println("Final variable max = " + max);
        }
    }

    public static void main(String[] args) {
        Utility utility = new Utility();
        System.out.println("=== Final Keyword Demo ===");
        utility.printLimit();
        System.out.println("Final class cannot be extended, final method cannot be overridden.");
    }
}
