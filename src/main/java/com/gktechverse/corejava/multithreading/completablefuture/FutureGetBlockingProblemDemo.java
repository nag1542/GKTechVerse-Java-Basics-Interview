package com.gktechverse.corejava.multithreading.completablefuture;

import com.gktechverse.corejava.multithreading.DemoLogger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 1) Shows why plain Future#get() code is hard to compose and still blocks the caller thread.
 */
public class FutureGetBlockingProblemDemo {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String userId = "U-1001";
        DemoLogger.info("1) Future#get() demo: async submission, but blocking collection.");

        ExecutorService pool = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();

        try {
            Future<CompletableFutureDemoSupport.User> userFuture =
                    pool.submit(() -> CompletableFutureDemoSupport.fetchUser(userId));
            Future<CompletableFutureDemoSupport.Orders> ordersFuture =
                    pool.submit(() -> CompletableFutureDemoSupport.fetchOrders(userId));
            Future<CompletableFutureDemoSupport.Prefs> prefsFuture =
                    pool.submit(() -> CompletableFutureDemoSupport.fetchPrefs(userId));

            DemoLogger.info("Main thread now waits on get(). No non-blocking continuation pipeline.");

            CompletableFutureDemoSupport.User user = userFuture.get();
            CompletableFutureDemoSupport.Orders orders = ordersFuture.get();
            CompletableFutureDemoSupport.Prefs prefs = prefsFuture.get();

            long tookMs = System.currentTimeMillis() - start;
            DemoLogger.info("Collected with get(): user=" + user.name()
                    + ", items=" + orders.items().size()
                    + ", theme=" + prefs.theme()
                    + ", elapsed=" + tookMs + "ms");
            DemoLogger.info("Issue: ExecutorService + Future gives no easy thenApply/thenCompose/whenComplete chain.");
        } catch (Exception e) {
            DemoLogger.error("Future#get demo failed", e);
        } finally {
            pool.shutdown();
        }
    }
}
