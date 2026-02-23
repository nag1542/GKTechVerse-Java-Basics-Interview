package com.gktechverse.corejava.staticmemory;

/**
 * Interview focus: difference between static variable and static block.
 */
public class StaticBlockVsVariableDemo {
    static class Config {
        static String environment;

        static {
            environment = "DEV";
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Static Block vs Static Variable Demo ===");
        System.out.println("Static variable stores class-level data: Config.environment");
        System.out.println("Static block initializes class-level data once during class loading.");
        System.out.println("Current environment value = " + Config.environment);
    }
}
