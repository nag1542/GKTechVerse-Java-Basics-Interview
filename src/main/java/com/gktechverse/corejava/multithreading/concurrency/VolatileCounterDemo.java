package com.gktechverse.corejava.multithreading.concurrency;

/**
 * volatile gives visibility, not atomicity.
 */
public class VolatileCounterDemo {

    private static volatile int count = 0;
    private static final int INCREMENTS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== VolatileCounterDemo ===");
        System.out.println("\n// PROBLEM: volatile int with count++");

        Thread t1 = new Thread(VolatileCounterDemo::incrementManyTimes, "volatile-worker-1");
        Thread t2 = new Thread(VolatileCounterDemo::incrementManyTimes, "volatile-worker-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        int expected = INCREMENTS * 2;
        System.out.println("Expected count = " + expected);
        System.out.println("Actual count   = " + count);
        System.out.println("\n// WHY IT FAILS: count++ = read + add + write (3 steps), not atomic.");
        System.out.println("// EXPECTED OUTPUT: actual is usually less than expected.");
    }

    private static void incrementManyTimes() {
        for (int i = 0; i < INCREMENTS; i++) {
            count++; // race condition here
            if (i % 20_000 == 0) {
                sleep(1);
            }
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
