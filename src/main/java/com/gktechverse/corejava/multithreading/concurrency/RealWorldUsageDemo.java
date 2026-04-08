package com.gktechverse.corejava.multithreading.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Production-style quick patterns.
 */
public class RealWorldUsageDemo {

    private static volatile boolean shutdownRequested = false; // volatile -> visibility
    private static final AtomicInteger requestCounter = new AtomicInteger(); // atomic increments
    private static final LongAdder metricsCounter = new LongAdder(); // high-contention metrics

    private final ReentrantLock processingLock = new ReentrantLock();
    private int walletBalance = 1_000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== RealWorldUsageDemo ===");
        volatileShutdownFlagExample();
        atomicRequestCounterExample();
        longAdderMetricsExample();

        RealWorldUsageDemo demo = new RealWorldUsageDemo();
        demo.synchronizedTransactionExample();
        demo.reentrantLockTimeoutProcessingExample();

        // Summary table:
        // volatile      -> visibility only
        // synchronized  -> atomic + blocking
        // AtomicInteger -> atomic + lock-free (CAS)
        // LongAdder     -> high contention counters
        // ReentrantLock -> advanced control (timeout/interrupt/fairness)
    }

    private static void volatileShutdownFlagExample() throws InterruptedException {
        System.out.println("\n[volatile] shutdown flag example");
        shutdownRequested = false;

        Thread worker = new Thread(() -> {
            while (!shutdownRequested) {
                sleep(50);
            }
            System.out.println("Worker observed shutdown flag and stopped.");
        }, "service-worker");

        worker.start();
        sleep(200);
        shutdownRequested = true;
        worker.join();
    }

    private static void atomicRequestCounterExample() throws InterruptedException {
        System.out.println("\n[AtomicInteger] request counter example");
        requestCounter.set(0);
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 1_000; j++) {
                    requestCounter.incrementAndGet();
                }
            }, "request-thread-" + i);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Total processed requests = " + requestCounter.get());
    }

    private static void longAdderMetricsExample() throws InterruptedException {
        System.out.println("\n[LongAdder] metrics counter example");
        metricsCounter.reset();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 5_000; j++) {
                    metricsCounter.increment();
                }
            }, "metric-thread-" + i);
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        System.out.println("Total metric events = " + metricsCounter.sum());
    }

    private synchronized void transferInWallet(int amount) {
        // synchronized for multi-step transaction consistency
        int oldBalance = walletBalance;
        sleep(10);
        walletBalance = oldBalance + amount;
    }

    private void synchronizedTransactionExample() throws InterruptedException {
        System.out.println("\n[synchronized] multi-step wallet transaction");
        walletBalance = 1_000;

        Thread t1 = new Thread(() -> transferInWallet(200), "tx-1");
        Thread t2 = new Thread(() -> transferInWallet(300), "tx-2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final wallet balance = " + walletBalance + " (expected 1500)");
    }

    private void reentrantLockTimeoutProcessingExample() throws InterruptedException {
        System.out.println("\n[ReentrantLock] timeout-based processing");

        Thread busyWorker = new Thread(() -> {
            processingLock.lock();
            try {
                System.out.println("Busy worker holds processing lock for 1.5s");
                sleep(1_500);
            } finally {
                processingLock.unlock();
            }
        }, "busy-worker");

        Thread apiWorker = new Thread(() -> {
            try {
                if (processingLock.tryLock()) {
                    try {
                        System.out.println("API worker got lock and processed request.");
                    } finally {
                        processingLock.unlock();
                    }
                } else {
                    System.out.println("API worker failed fast. Return 429/retry suggestion.");
                }
            } catch (Exception e) {
                System.out.println("Unexpected: " + e.getMessage());
            }
        }, "api-worker");

        busyWorker.start();
        sleep(100);
        apiWorker.start();

        busyWorker.join();
        apiWorker.join();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
