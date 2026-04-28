package com.gktechverse.corejava.multithreading.completablefuture;

import com.gktechverse.corejava.multithreading.DemoLogger;

import java.util.concurrent.CompletableFuture;

/**
 * 6) Error handling APIs: exceptionally, handle, whenComplete and stage placement.
 */
public class CompletableFutureErrorHandlingDemo {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String userId = "U-ERROR";

        CompletableFuture<CompletableFutureDemoSupport.User> safe = CompletableFuture
                .supplyAsync(() -> {
                    throw new RuntimeException("User service unavailable for " + userId);
                })
                .exceptionally(ex -> {
                    DemoLogger.warn("Failed to fetch user " + userId + ", falling back to guest.");
                    return CompletableFutureDemoSupport.guestUser();
                });

        CompletableFuture<String> result = CompletableFuture
                .supplyAsync(() -> CompletableFutureDemoSupport.fetchUser("U-2001"))
                .handle((user, ex) -> ex != null ? "Error: " + ex.getMessage() : user.name());

        CompletableFuture<CompletableFutureDemoSupport.User> withLog = CompletableFuture
                .supplyAsync(() -> {
                    throw new RuntimeException("Timeout while fetching user");
                })
                .whenComplete((user, ex) -> {
                    if (ex != null) {
                        DemoLogger.warn("metrics.increment(user.fetch.error)");
                    } else {
                        DemoLogger.info("metrics.increment(user.fetch.success)");
                    }
                })
                .exceptionally(ex -> CompletableFutureDemoSupport.guestUser());

        CompletableFuture<String> risky = CompletableFuture
                .supplyAsync(() -> CompletableFutureDemoSupport.fetchUser("U-2002"))
                .thenApply(user -> {
                    throw new RuntimeException("NPE-like transformation issue");
                })
                .exceptionally(ex -> "Unknown");

        DemoLogger.info("exceptionally fallback user => " + safe.join());
        DemoLogger.info("handle outcome => " + result.join());
        DemoLogger.info("whenComplete observed pipeline result => " + withLog.join());
        DemoLogger.info("exceptionally catches earlier stage failures => " + risky.join());
    }
}
