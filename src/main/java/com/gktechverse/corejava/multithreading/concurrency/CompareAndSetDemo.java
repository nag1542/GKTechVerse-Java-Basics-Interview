package com.gktechverse.corejava.multithreading.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manual compareAndSet success/failure and retry logging.
 */
public class CompareAndSetDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== CompareAndSetDemo ===");

        AtomicInteger stock = new AtomicInteger(10);

        boolean success = stock.compareAndSet(10, 9);
        System.out.println("CAS #1 expected=10 -> 9, success=" + success + ", current=" + stock.get());

        boolean failure = stock.compareAndSet(10, 8);
        System.out.println("CAS #2 expected=10 -> 8, success=" + failure + ", current=" + stock.get());

        System.out.println("\n--- Simulated CAS retry loop with two buyer threads ---");
        stock.set(5);

        Runnable buyer = () -> {
            String name = Thread.currentThread().getName();
            for (int attempt = 1; attempt <= 5; attempt++) {
                int observed = stock.get();
                if (observed <= 0) {
                    System.out.println(name + " sees sold out.");
                    return;
                }
                sleep(40);
                boolean updated = stock.compareAndSet(observed, observed - 1);
                System.out.println(name + " attempt " + attempt + ": observed=" + observed
                        + ", CAS success=" + updated + ", now=" + stock.get());
                if (updated) {
                    return;
                }
            }
            System.out.println(name + " gave up after retries.");
        };

        Thread t1 = new Thread(buyer, "buyer-1");
        Thread t2 = new Thread(buyer, "buyer-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
