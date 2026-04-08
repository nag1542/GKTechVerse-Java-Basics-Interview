package com.gktechverse.corejava.multithreading.concurrency;

/**
 * Fixing lost updates with synchronized.
 */
public class SynchronizedCounterDemo {

    private int count = 0;
    private static final int INCREMENTS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== SynchronizedCounterDemo ===");
        SynchronizedCounterDemo demo = new SynchronizedCounterDemo();

        Thread t1 = new Thread(() -> demo.incrementManyTimes(), "sync-worker-1");
        Thread t2 = new Thread(() -> demo.incrementManyTimes(), "sync-worker-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        int expected = INCREMENTS * 2;
        System.out.println("Expected count = " + expected);
        System.out.println("Actual count   = " + demo.getCount());
        System.out.println("\n// SOLUTION: synchronized ensures one thread enters critical section at a time.");
        System.out.println("// EXPECTED OUTPUT: actual equals expected.");
    }

    // SOLUTION
    private synchronized void increment() {
        count++;
    }

    private void incrementManyTimes() {
        for (int i = 0; i < INCREMENTS; i++) {
            increment();
        }
    }

    private synchronized int getCount() {
        return count;
    }
}
