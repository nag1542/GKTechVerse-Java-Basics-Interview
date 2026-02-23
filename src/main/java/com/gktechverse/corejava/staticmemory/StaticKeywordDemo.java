package com.gktechverse.corejava.staticmemory;

/**
 * Interview focus: static keyword basics.
 * Shows class-level data shared across objects.
 */
public class StaticKeywordDemo {
    static class Student {
        static String schoolName = "GKTechVerse";
        String name;

        Student(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        Student first = new Student("Aman");
        Student second = new Student("Riya");

        System.out.println("=== Static Keyword Demo ===");
        System.out.println("First student school: " + Student.schoolName);
        System.out.println("Second student school: " + Student.schoolName);
        System.out.println("Static belongs to class, not individual object.");
        System.out.println(first.name + " and " + second.name + " share schoolName.");
    }
}
