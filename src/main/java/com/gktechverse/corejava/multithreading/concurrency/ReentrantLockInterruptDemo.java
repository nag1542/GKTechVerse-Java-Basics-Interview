package com.gktechverse.corejava.multithreading.concurrency;

import java.util.concurrent.locks.ReentrantLock;

/**
 * lockInterruptibly for cancellation support.
 */
public class ReentrantLockInterruptDemo {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== ReentrantLockInterruptDemo ===");

        Thread holder = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("Holder thread acquired lock for 3 seconds.");
                sleep(3_000);
            } finally {
                lock.unlock();
                System.out.println("Holder thread released lock.");
            }
        }, "holder");

        Thread waiting = new Thread(() -> {
            try {
                System.out.println("Waiting thread calling lockInterruptibly()...");
                lock.lockInterruptibly();
                try {
                    System.out.println("Waiting thread acquired lock.");
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                System.out.println("Waiting thread interrupted while waiting. Graceful exit.");
                Thread.currentThread().interrupt();
            }
        }, "waiting");

        holder.start();
        sleep(100);
        waiting.start();

        sleep(700);
        System.out.println("Main thread interrupts waiting thread.");
        waiting.interrupt();

        holder.join();
        waiting.join();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
