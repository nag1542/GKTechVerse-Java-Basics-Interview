package com.gktechverse.corejava.designpatterns.singleton;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Demonstrates 5 singleton approaches and their behavior.
 */
public class SingletonPatternsDemo {

    public static void main(String[] args) {
        System.out.println("=== Singleton Design Pattern: 5 Approaches ===");
        System.out.println("1. Eager initialization       — thread-safe, not lazy");
        System.out.println("2. Lazy initialization        — NOT thread-safe");
        System.out.println("3. Synchronized method        — thread-safe, slower on repeated calls");
        System.out.println("4. Double-checked locking     — thread-safe, fast after init");
        System.out.println("5. Enum                       — thread-safe, safest, best\n");

        demonstrateMultithreadBehavior();
        demonstrateSimpleUsage();
    }

    private static void demonstrateSimpleUsage() {
        System.out.println("--- How to call from main method ---");

        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();
        System.out.println("EagerSingleton same instance: " + (eager1 == eager2));

        LazySingleton lazy1 = LazySingleton.getInstance();
        LazySingleton lazy2 = LazySingleton.getInstance();
        System.out.println("LazySingleton same instance (single-thread): " + (lazy1 == lazy2));

        SyncSingleton sync1 = SyncSingleton.getInstance();
        SyncSingleton sync2 = SyncSingleton.getInstance();
        System.out.println("SyncSingleton same instance: " + (sync1 == sync2));

        DoubleCheckedSingleton dcl1 = DoubleCheckedSingleton.getInstance();
        DoubleCheckedSingleton dcl2 = DoubleCheckedSingleton.getInstance();
        System.out.println("DoubleCheckedSingleton same instance: " + (dcl1 == dcl2));

        String dbUrl = ConfigService.INSTANCE.get("db.url");
        System.out.println("ConfigService enum usage -> db.url: " + dbUrl + "\n");
    }

    private static void demonstrateMultithreadBehavior() {
        System.out.println("--- Multithreaded behavior ---");
        System.out.println("LazySingleton should fail in a race (can create multiple instances).");

        runConcurrentIdentityTest("LazySingleton", LazySingleton::getInstance, 100);
        runConcurrentIdentityTest("SyncSingleton", SyncSingleton::getInstance, 100);
        runConcurrentIdentityTest("DoubleCheckedSingleton", DoubleCheckedSingleton::getInstance, 100);
        runConcurrentIdentityTest("EagerSingleton", EagerSingleton::getInstance, 100);
        runConcurrentIdentityTest("ConfigService enum", () -> ConfigService.INSTANCE, 100);
    }

    private static void runConcurrentIdentityTest(String name, Supplier<Object> supplier, int threads) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        CountDownLatch ready = new CountDownLatch(threads);
        CountDownLatch start = new CountDownLatch(1);
        Set<Integer> identityHashes = ConcurrentHashMap.newKeySet();

        for (int i = 0; i < threads; i++) {
            executor.execute(() -> {
                ready.countDown();
                try {
                    start.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                Object instance = supplier.get();
                identityHashes.add(System.identityHashCode(instance));
            });
        }

        try {
            ready.await();
            start.countDown();
            executor.shutdown();
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }

        boolean isSingleInstance = identityHashes.size() == 1;
        String status = isSingleInstance ? "PASS" : "FAIL";
        System.out.println(name + " -> unique instance count: " + identityHashes.size() + " => " + status);
    }
}
