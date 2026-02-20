package com.gktechverse.corejava.basics;

/**
 * Interview focus: primitive and non-primitive data types.
 * Prints common types used in beginner interviews.
 */
public class DataTypesDemo {
    public static void main(String[] args) {
        byte age = 25;
        int salary = 50000;
        long population = 1_400_000_000L;
        float piApprox = 3.14f;
        double exactValue = 99.999;
        char grade = 'A';
        boolean isJavaFun = true;
        String name = "GKTechVerse";

        System.out.println("=== Data Types Demo ===");
        System.out.println("byte: " + age);
        System.out.println("int: " + salary);
        System.out.println("long: " + population);
        System.out.println("float: " + piApprox);
        System.out.println("double: " + exactValue);
        System.out.println("char: " + grade);
        System.out.println("boolean: " + isJavaFun);
        System.out.println("String (non-primitive): " + name);
    }
}
