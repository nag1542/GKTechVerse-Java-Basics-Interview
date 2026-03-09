package com.gktechverse.corejava.collections.concurrenthashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Demonstrates how ConcurrentHashMap supports safe concurrent updates.
 */
public class ConcurrentHashMapBenefitsDemo {

    private static final int THREADS = 4;
    private static final int INCREMENTS_PER_THREAD = 10_000;

    public static void main(String[] args) {
        System.out.println("\n4) How ConcurrentHashMap helps in multithreaded scenarios");

        Map<String, Integer> safeCounterMap = new ConcurrentHashMap<>();
        safeCounterMap.put("views", 0);

        Thread[] workers = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            workers[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    safeCounterMap.compute("views", (key, oldValue) -> oldValue + 1);
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
        int actual = safeCounterMap.get("views");

        System.out.println("Expected count: " + expected);
        System.out.println("Actual count with ConcurrentHashMap + compute: " + actual);
        System.out.println("ConcurrentHashMap uses fine-grained synchronization/CAS internally for better scalability than Hashtable.");
        System.out.println("It allows concurrent reads and supports safe atomic compound updates via compute/merge methods.");
    }
}
