package com.gktechverse.corejava.multithreading.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger uses CAS for atomic increments.
 */
public class AtomicCounterDemo {

    private static final AtomicInteger count = new AtomicInteger(0);
    private static final int INCREMENTS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== AtomicCounterDemo ===");

        Thread t1 = new Thread(AtomicCounterDemo::incrementManyTimes, "atomic-worker-1");
        Thread t2 = new Thread(AtomicCounterDemo::incrementManyTimes, "atomic-worker-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        int expected = INCREMENTS * 2;
        System.out.println("Expected count = " + expected);
        System.out.println("Actual count   = " + count.get());
        System.out.println("// SOLUTION: incrementAndGet() is atomic using CAS retry internally.");
    }

    private static void incrementManyTimes() {
        for (int i = 0; i < INCREMENTS; i++) {
            // CAS behavior (internally): read old, try swap old->new, retry if failed.
            count.incrementAndGet();
        }
    }
}
