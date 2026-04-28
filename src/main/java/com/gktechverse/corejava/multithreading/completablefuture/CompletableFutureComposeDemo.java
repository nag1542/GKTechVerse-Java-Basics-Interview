package com.gktechverse.corejava.multithreading.completablefuture;

import com.gktechverse.corejava.multithreading.DemoLogger;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 4) thenCompose vs thenApply when next step already returns CompletableFuture.
 */
public class CompletableFutureComposeDemo {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String userId = "U-1003";
        ExecutorService ioPool = Executors.newFixedThreadPool(8);

        CompletableFuture<CompletableFutureDemoSupport.User> userCF =
                CompletableFuture.supplyAsync(() -> CompletableFutureDemoSupport.fetchUser(userId), ioPool);

        CompletableFuture<CompletableFuture<List<String>>> wrong =
                userCF.thenApply(user -> CompletableFutureDemoSupport.fetchOrdersAsync(user.id()));

        CompletableFuture<List<String>> orders =
                userCF.thenCompose(user -> CompletableFutureDemoSupport.fetchOrdersAsync(user.id()));

        CompletableFuture<CompletableFutureDemoSupport.ApiResponse> response = CompletableFuture
                .supplyAsync(() -> CompletableFutureDemoSupport.fetchUser(userId), ioPool)
                .thenCompose(user -> CompletableFutureDemoSupport.fetchOrdersAsync(user.id()))
                .thenApply(CompletableFutureDemoSupport::buildResponse)
                .exceptionally(ex -> CompletableFutureDemoSupport.ApiResponse.error(ex.getMessage()));

        DemoLogger.info("Wrong type from thenApply => CF<CF<List<String>>>: " + wrong.join().join());
        DemoLogger.info("Correct type from thenCompose => CF<List<String>>: " + orders.join());
        DemoLogger.info("Composed API response => " + response.join());

        ioPool.shutdown();
    }
}
