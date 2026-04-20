package com.gktechverse.corejava.oops.interfacevsabstract;

/**
 * Interview focus: constructor support in abstract classes.
 * Shows base initialization happening automatically for subclasses.
 */
public class AbstractClassConstructorDemo {

    abstract static class Base {
        Base() {
            System.out.println("Base initialized");
        }
    }

    static class Child extends Base {
        Child() {
            System.out.println("Child initialized");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Abstract Class Constructor Demo ===");
        new Child();
    }
}
