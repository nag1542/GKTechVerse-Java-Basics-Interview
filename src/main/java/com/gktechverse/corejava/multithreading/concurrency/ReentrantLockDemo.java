package com.gktechverse.corejava.multithreading.concurrency;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock basic usage.
 */
public class ReentrantLockDemo {

    private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;
    private static final int INCREMENTS = 100_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== ReentrantLockDemo ===");
        ReentrantLockDemo demo = new ReentrantLockDemo();

        Thread t1 = new Thread(() -> demo.incrementManyTimes(), "lock-worker-1");
        Thread t2 = new Thread(() -> demo.incrementManyTimes(), "lock-worker-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Expected count = " + (INCREMENTS * 2));
        System.out.println("Actual count   = " + demo.count);
    }

    private void incrementManyTimes() {
        for (int i = 0; i < INCREMENTS; i++) {
            lock.lock();
            try {
                // SOLUTION: explicit lock + try/finally unlock
                count++;
            } finally {
                lock.unlock();
            }
        }
    }
}
