package com.gktechverse.corejava.staticmemory;

/**
 * Interview focus: static variables, static methods, static blocks.
 */
public class StaticMembersAndBlockDemo {
    static class Counter {
        static int count = 0;

        static {
            System.out.println("Counter static block: class loaded once.");
        }

        Counter() {
            count++;
        }

        static void printCount() {
            System.out.println("Objects created = " + count);
        }
    }

    static class MathUtil {
        static int square(int value) {
            return value * value;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Static Members & Block Demo ===");
        new Counter();
        new Counter();
        Counter.printCount();

        int number = 6;
        System.out.println("Use static method for utility logic: square(" + number + ") = " + MathUtil.square(number));
    }
}
