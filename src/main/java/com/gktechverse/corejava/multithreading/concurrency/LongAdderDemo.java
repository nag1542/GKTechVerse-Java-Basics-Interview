package com.gktechverse.corejava.multithreading.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder for high-contention counters.
 */
public class LongAdderDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== LongAdderDemo ===");

        int threads = 8;
        int incrementsPerThread = 100_000;

        LongAdder longAdder = new LongAdder();
        AtomicInteger atomicInteger = new AtomicInteger();

        long adderTime = runLoad(threads, incrementsPerThread, longAdder::increment);
        long atomicTime = runLoad(threads, incrementsPerThread, atomicInteger::incrementAndGet);

        int expected = threads * incrementsPerThread;
        System.out.println("Expected total       = " + expected);
        System.out.println("LongAdder total      = " + longAdder.sum() + " (time=" + adderTime + " ms)");
        System.out.println("AtomicInteger total  = " + atomicInteger.get() + " (time=" + atomicTime + " ms)");

        // LongAdder reduces contention by spreading updates across cells and summing later.
        // AtomicInteger is great for exact atomic operations but can contend more under very high write load.
    }

    private static long runLoad(int threads, int loops, Runnable incrementLogic) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(threads);
        long start = System.currentTimeMillis();

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                for (int j = 0; j < loops; j++) {
                    incrementLogic.run();
                }
                latch.countDown();
            }, "counter-thread-" + i).start();
        }

        latch.await();
        return System.currentTimeMillis() - start;
    }
}
