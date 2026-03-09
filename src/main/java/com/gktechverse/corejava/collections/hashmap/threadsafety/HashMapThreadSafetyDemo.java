package com.gktechverse.corejava.collections.hashmap.threadsafety;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstrates why HashMap is not thread-safe for concurrent writes.
 */
public class HashMapThreadSafetyDemo {

    private static final int THREADS = 4;
    private static final int INCREMENTS_PER_THREAD = 10_000;

    public static void main(String[] args) {
        System.out.println("\n3) Why HashMap is not thread-safe");

        Map<String, Integer> unsafeCounterMap = new HashMap<>();
        unsafeCounterMap.put("views", 0);

        Thread[] workers = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    Integer current = unsafeCounterMap.get("views");
                    unsafeCounterMap.put("views", current + 1);
                }
            });
            workers[i].start();
        }

        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted during demo.");
            }
        }

        int expected = THREADS * INCREMENTS_PER_THREAD;
        int actual = unsafeCounterMap.get("views");

        System.out.println("Expected count: " + expected);
        System.out.println("Actual count with HashMap: " + actual);
        System.out.println("Reason: read-modify-write is not atomic and HashMap has no built-in synchronization.");
        System.out.println("Result can vary run-to-run due to race conditions.");
    }
}
