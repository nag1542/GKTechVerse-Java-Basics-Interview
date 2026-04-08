package com.gktechverse.corejava.multithreading.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * tryLock with timeout for graceful fallback.
 */
public class ReentrantLockTimeoutDemo {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== ReentrantLockTimeoutDemo ===");

        Thread longTask = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Long task acquired lock. Holding for 2 seconds...");
                sleep(2_000);
            } finally {
                lock.unlock();
                System.out.println("Long task released lock.");
            }
        }, "long-task");

        Thread timedTask = new Thread(() -> {
            try {
                System.out.println("Timed task trying lock for 500ms...");
                if (lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                    try {
                        System.out.println("Timed task acquired lock and processed.");
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("Timed task could not acquire lock. Fallback: queue for retry.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "timed-task");

        longTask.start();
        sleep(100);
        timedTask.start();

        longTask.join();
        timedTask.join();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
