package com.gktechverse.corejava.multithreading.concurrency;

/**
 * Demonstrates visibility issue and volatile fix.
 */
public class VisibilityProblemDemo {

    // PROBLEM: not volatile, no synchronization
    private static boolean ready = false;

    // SOLUTION: volatile guarantees visibility
    private static volatile boolean volatileReady = false;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== VisibilityProblemDemo ===");
        runWithoutVolatile();
        runWithVolatile();
    }

    private static void runWithoutVolatile() throws InterruptedException {
        System.out.println("\n--- PROBLEM: no volatile, no synchronization ---");
        ready = false;

        Thread reader = new Thread(() -> {
            long start = System.currentTimeMillis();
            while (!ready) {
                // WHY IT FAILS:
                // JVM/CPU can keep reading cached value; update may not become visible quickly.
                Thread.onSpinWait();
                if (System.currentTimeMillis() - start > 2_000) {
                    System.out.println("Reader timed out after 2s. Update was delayed/not visible in time.");
                    return;
                }
            }
            System.out.println("Reader observed ready=true (may be delayed). ");
        }, "reader-no-volatile");

        Thread writer = new Thread(() -> {
            sleep(200);
            ready = true;
            System.out.println("Writer set ready=true");
        }, "writer-no-volatile");

        reader.start();
        writer.start();
        reader.join();
        writer.join();

        // EXPECTED OUTPUT:
        // Sometimes reader sees update quickly, sometimes delayed (platform dependent).
    }

    private static void runWithVolatile() throws InterruptedException {
        System.out.println("\n--- SOLUTION: volatile for visibility ---");
        volatileReady = false;

        Thread reader = new Thread(() -> {
            while (!volatileReady) {
                Thread.onSpinWait();
            }
            System.out.println("Reader immediately observed volatileReady=true");
        }, "reader-volatile");

        Thread writer = new Thread(() -> {
            sleep(200);
            volatileReady = true;
            System.out.println("Writer set volatileReady=true");
        }, "writer-volatile");

        reader.start();
        writer.start();
        reader.join();
        writer.join();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
