package com.gktechverse.corejava.oops;

/**
 * Interview focus: super keyword.
 * Accesses parent class constructor and method.
 */
public class SuperKeywordDemo {
    static class Parent {
        Parent() {
            System.out.println("Parent constructor called.");
        }

        void show() {
            System.out.println("Parent show method.");
        }
    }

    static class Child extends Parent {
        Child() {
            super();
            System.out.println("Child constructor called.");
        }

        @Override
        void show() {
            super.show();
            System.out.println("Child show method.");
        }
    }

    public static void main(String[] args) {
        Child child = new Child();
        System.out.println("=== Super Keyword Demo ===");
        child.show();
    }
}
