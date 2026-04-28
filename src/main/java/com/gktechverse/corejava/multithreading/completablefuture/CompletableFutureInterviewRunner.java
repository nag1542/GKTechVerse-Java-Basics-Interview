package com.gktechverse.corejava.multithreading.completablefuture;

import com.gktechverse.corejava.multithreading.DemoLogger;

/**
 * Runs CompletableFuture interview demos in sequence.
 */
public class CompletableFutureInterviewRunner {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        DemoLogger.info("Starting CompletableFuture interview demonstrations.");

        FutureGetBlockingProblemDemo.run();
        CompletableFutureCreationDemo.run();
        CompletableFutureTransformationDemo.run();
        CompletableFutureComposeDemo.run();
        CompletableFutureCombiningDemo.run();
        CompletableFutureErrorHandlingDemo.run();
        CompletableFutureThreadPoolBehaviorDemo.run();

        DemoLogger.info("Completed CompletableFuture interview demonstrations.");
    }
}
