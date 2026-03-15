package com.gktechverse.corejava.collections.hashmap.nullsandduplicates;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Demonstrates null handling and duplicate key behavior in HashMap.
 */

class Student {
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // 1. Override equals: Compare objects based on 'id'
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    // 2. Override hashCode: Generate a hash based on the same 'id'
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "'}";
    }
}

public class HashMapNullAndDuplicateKeyDemo {

    public static void main(String[] args) {
        System.out.println("\n1) HashMap null entry + duplicate key behavior");
        
        
        Map<Student, String> studentGrades = new HashMap<>();

        Student s1 = new Student(101, "Alice");
        Student s2 = new Student(101, "Alice (Duplicate ID)");

        studentGrades.put(s1, "A");
        studentGrades.put(s2, "A+");

        System.out.println("Map after inserts: " + studentGrades);
        // Because we overrode equals/hashCode, s2 will find s1's entry
        System.out.println("Grade for s2: " + studentGrades.get(s2)); 
        // Output: Grade for s2: A
        
        

        Map<String, String> studentStatusById = new HashMap<>();

        studentStatusById.put(null, "Guest Student");
        studentStatusById.put("STU-101", "Registered");
        studentStatusById.put("STU-101", "Fees Paid");
        studentStatusById.put("STU-102", null);

        System.out.println("HashMap allows exactly one null key and multiple null values.");
        System.out.println("Map after inserts: " + studentStatusById);
        System.out.println("Value for null key: " + studentStatusById.get(null));
        System.out.println("Duplicate key update for STU-101: " + studentStatusById.get("STU-101"));
        System.out.println("Null value accepted for STU-102: " + studentStatusById.get("STU-102"));

        System.out.println("Teaching point: put() with existing key replaces old value, it does not create a second entry.");
    }
}
