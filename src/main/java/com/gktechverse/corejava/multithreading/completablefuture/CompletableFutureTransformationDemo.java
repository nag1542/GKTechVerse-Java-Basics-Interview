package com.gktechverse.corejava.multithreading.completablefuture;

import com.gktechverse.corejava.multithreading.DemoLogger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 3) Transformation APIs: thenApply, thenAccept, thenRun, and async variants.
 */
public class CompletableFutureTransformationDemo {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String userId = "U-1002";
        ExecutorService ioPool = Executors.newFixedThreadPool(4);

        CompletableFuture<CompletableFutureDemoSupport.User> userCF =
                CompletableFuture.supplyAsync(() -> CompletableFutureDemoSupport.fetchUser(userId));

        CompletableFuture<String> nameCF = userCF.thenApply(CompletableFutureDemoSupport.User::name);

        CompletableFuture<String> result = userCF
                .thenApply(CompletableFutureDemoSupport.User::name)
                .thenApply(String::toUpperCase)
                .thenApply(name -> "Hello, " + name);

        CompletableFuture<Void> printed =
                nameCF.thenAccept(name -> DemoLogger.info("User: " + name));

        CompletableFuture<Void> logged =
                userCF.thenRun(() -> DemoLogger.info("metrics.increment(user.fetch.count)"));

        CompletableFuture<String> asyncName =
                userCF.thenApplyAsync(CompletableFutureDemoSupport.User::name, ioPool);

        DemoLogger.info("Transformed greeting = " + result.join());
        printed.join();
        logged.join();
        DemoLogger.info("Async transformed name = " + asyncName.join());

        ioPool.shutdown();
    }
}
