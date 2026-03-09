package com.gktechverse.corejava.collections.hashmap.internalworking;

import java.util.HashMap;
import java.util.Map;

/**
 * Explains bucket collision handling and Java 8+ treeification rules in HashMap.
 */
public class HashMapCollisionAndTreeificationDemo {

    public static void main(String[] args) {
        System.out.println("\n2) HashMap linked-list bucket and tree conversion concept");

        Map<StudentBucketKey, String> marksByStudent = new HashMap<>(128);

        for (int i = 1; i <= 10; i++) {
            StudentBucketKey key = new StudentBucketKey("A", i);
            marksByStudent.put(key, "Score-" + (70 + i));
        }

        System.out.println("Inserted 10 custom keys with same hashCode, so they land in one bucket.");
        System.out.println("Before Java 8, collision bucket was maintained as linked list only.");
        System.out.println("Java 8+ optimization: when bucket size crosses 8 and table size >= 64, bucket can become a red-black tree.");
        System.out.println("Why important: lookup in long chain can degrade toward O(n), tree nodes improve it toward O(log n).");
        System.out.println("Sample fetch from collided bucket: " + marksByStudent.get(new StudentBucketKey("A", 7)));
        System.out.println("Total entries inserted: " + marksByStudent.size());
        System.out.println("Teaching point: treeification is an internal optimization; API usage remains same.");
    }
}
