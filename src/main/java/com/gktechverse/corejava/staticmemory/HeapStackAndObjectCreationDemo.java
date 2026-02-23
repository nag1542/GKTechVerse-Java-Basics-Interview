package com.gktechverse.corejava.staticmemory;

/**
 * Interview focus: Heap vs Stack and what happens on object creation.
 */
public class HeapStackAndObjectCreationDemo {
    static class Employee {
        int id;
        String name;

        Employee(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static void main(String[] args) {
        int localValue = 10; // stack
        Employee employee = new Employee(101, "Aman"); // reference on stack, object on heap

        System.out.println("=== Heap, Stack & Object Creation Demo ===");
        System.out.println("Stack stores local variables and references. localValue = " + localValue);
        System.out.println("Heap stores object data. employee.name = " + employee.name);
        System.out.println("When new is used: class metadata checked -> heap memory allocated -> defaults set -> constructor runs -> reference assigned.");
    }
}
