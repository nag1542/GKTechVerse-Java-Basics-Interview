package com.gktechverse.corejava.staticmemory;

/**
 * Interview focus: Singleton pattern vs static usage.
 */
public class SingletonVsStaticDemo {
    static class AppConfig {
        private static final AppConfig INSTANCE = new AppConfig();
        private String mode = "BEGINNER";

        private AppConfig() {
        }

        static AppConfig getInstance() {
            return INSTANCE;
        }

        String getMode() {
            return mode;
        }
    }

    static class Calculator {
        static int add(int first, int second) {
            return first + second;
        }
    }

    public static void main(String[] args) {
        AppConfig first = AppConfig.getInstance();
        AppConfig second = AppConfig.getInstance();

        System.out.println("=== Singleton vs Static Demo ===");
        System.out.println("Singleton gives one object instance: " + (first == second));
        System.out.println("Singleton object mode = " + first.getMode());
        System.out.println("Static method call without object: 10 + 20 = " + Calculator.add(10, 20));
    }
}
