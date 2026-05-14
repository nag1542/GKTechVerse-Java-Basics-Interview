package com.gktechverse.corejava.multithreading.completablefuture;

import com.gktechverse.corejava.multithreading.DemoLogger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 2) Creation APIs: supplyAsync, runAsync, completedFuture, failedFuture and custom executor.
 */
public class CompletableFutureCreationDemo {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String userId = "U-1001";
        DemoLogger.info("2) CompletableFuture creation demo");

        CompletableFuture<CompletableFutureDemoSupport.User> userCF =
                CompletableFuture.supplyAsync(
                		() -> CompletableFutureDemoSupport.fetchUser(userId));

        CompletableFuture<Void> logCF =
                CompletableFuture.runAsync(() -> DemoLogger.info("Audit event written"));

        CompletableFuture<String> done = CompletableFuture.completedFuture("cached-value");
        
        CompletableFuture<String> failed = CompletableFuture.failedFuture(
        		new RuntimeException("error"));

        ExecutorService ioPool = Executors.newFixedThreadPool(20);
        CompletableFuture<CompletableFutureDemoSupport.User> userCF2 =
                CompletableFuture.supplyAsync(
                		() -> CompletableFutureDemoSupport.fetchUser(userId), ioPool);

        DemoLogger.info("Default pool for supplyAsync is ForkJoinPool.commonPool().");
        DemoLogger.info("Use custom executor for I/O heavy tasks to avoid starving CPU work.");

        userCF.thenAccept(user -> DemoLogger.info("Default pool user: " + user.name())).join();
        userCF2.thenAccept(user -> DemoLogger.info("Custom pool user: " + user.name())).join();
        logCF.join();
        DemoLogger.info("completedFuture value: " + done.join());
        DemoLogger.info("failedFuture handled value: " + failed.exceptionally(ex -> "fallback").join());

        ioPool.shutdown();
    }
}
