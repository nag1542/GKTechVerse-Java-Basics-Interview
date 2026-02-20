package com.gktechverse.corejava.oops;

/**
 * Interview focus: inheritance.
 * Child class reuses parent properties/behavior.
 */
public class InheritanceDemo {
    static class Animal {
        void eat() {
            System.out.println("Animal can eat.");
        }
    }

    static class Dog extends Animal {
        void bark() {
            System.out.println("Dog can bark.");
        }
    }

    public static void main(String[] args) {
        Dog dog = new Dog();
        System.out.println("=== Inheritance Demo ===");
        dog.eat();
        dog.bark();
    }
}
