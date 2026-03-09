package com.gktechverse.corejava.collections.hashmap.nullsandduplicates;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstrates null handling and duplicate key behavior in HashMap.
 */
public class HashMapNullAndDuplicateKeyDemo {

    public static void main(String[] args) {
        System.out.println("\n1) HashMap null entry + duplicate key behavior");

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
