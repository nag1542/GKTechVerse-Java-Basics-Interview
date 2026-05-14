package com.gktechverse.corejava.multithreading.completablefuture;

import com.gktechverse.corejava.multithreading.DemoLogger;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 7) Thread-pool behavior rules for CompletableFuture stages.
 */
public class CompletableFutureThreadPoolBehaviorDemo {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String id = "U-POOL-1";
        DemoLogger.info("7) CompletableFuture thread-pool behavior rules");
        
        CompletableFuture.supplyAsync(
        		() -> CompletableFutureDemoSupport.fetchUser(id))
        .thenApply(CompletableFutureDemoSupport.User::name)
        .thenAccept(System.out::println);

        ruleOneSupplyAsyncDefaultPool(id);
        ruleTwoThenApplyExecutionRule(id);
        ruleThreeThenApplyAsyncControlledPool(id);
        productionPatternSeparatePools(id);
        virtualThreadPattern(id);
    }

    private static void ruleOneSupplyAsyncDefaultPool(String id) {
        CompletableFuture<CompletableFutureDemoSupport.User> userCF 
        = CompletableFuture.supplyAsync(() -> {
            DemoLogger.info("Rule 1: supplyAsync thread = " + Thread.currentThread().getName());
            return CompletableFutureDemoSupport.fetchUser(id);
        });
        userCF.join();
    }

    private static void ruleTwoThenApplyExecutionRule(String id) {
        CompletableFuture<CompletableFutureDemoSupport.User> notCompleted =
                CompletableFuture.supplyAsync(() -> CompletableFutureDemoSupport.fetchUser(id));

        CompletableFuture<String> nameFromCompletingThread = notCompleted.thenApply(user -> {
            DemoLogger.info("Rule 2a: thenApply runs on completing thread = " + Thread.currentThread().getName());
            return user.name();
        });
        nameFromCompletingThread.join();

        CompletableFuture<CompletableFutureDemoSupport.User> alreadyDone =
                CompletableFuture.completedFuture(new CompletableFutureDemoSupport.User(id, "Already Done"));

        CompletableFuture<String> nameFromCallerThread = alreadyDone.thenApply(user -> {
            DemoLogger.info("Rule 2b: thenApply runs on caller when stage already complete = "
                    + Thread.currentThread().getName());
            return user.name();
        });
        nameFromCallerThread.join();
    }

    private static void ruleThreeThenApplyAsyncControlledPool(String id) {
        ExecutorService httpPool = Executors.newFixedThreadPool(4, r -> new Thread(r, "http-thread"));
        try {
            CompletableFuture<String> controlled = CompletableFuture
                    .supplyAsync(() -> CompletableFutureDemoSupport.fetchUser(id))
                    .thenApplyAsync(user -> {
                        DemoLogger.info("Rule 3: thenApplyAsync thread = " + Thread.currentThread().getName());
                        return user.name();
                    }, httpPool);

            controlled.join();
        } finally {
            httpPool.shutdown();
        }
    }

    private static void productionPatternSeparatePools(String id) {
        ExecutorService dbPool = Executors.newFixedThreadPool(20, r -> new Thread(r, "db-thread"));
        ExecutorService httpPool = Executors.newFixedThreadPool(50, r -> new Thread(r, "http-thread"));

        try {
            CompletableFuture<String> pipeline = CompletableFuture
                    .supplyAsync(() -> {
                        DemoLogger.info("DB stage on " + Thread.currentThread().getName());
                        CompletableFutureDemoSupport.sleep(80);
                        return "row-for-" + id;
                    }, dbPool)
                    .thenApplyAsync(row -> {
                        DemoLogger.info("HTTP stage on " + Thread.currentThread().getName());
                        CompletableFutureDemoSupport.sleep(90);
                        return "response-for-" + row;
                    }, httpPool)
                    .thenApply(response -> {
                        DemoLogger.info("Parse stage on completing thread " + Thread.currentThread().getName());
                        return response.toUpperCase();
                    });

            DemoLogger.info("Production pattern result => " + pipeline.join());
        } finally {
            dbPool.shutdown();
            httpPool.shutdown();
        }
    }

    private static void virtualThreadPattern(String id) {
        ExecutorService vtPool = tryCreateVirtualThreadExecutor();
        if (vtPool == null) {
            DemoLogger.warn("Virtual threads require Java 21+. Skipping demo on current runtime.");
            return;
        }

        try {
            CompletableFuture<CompletableFutureDemoSupport.User> cf =
                    CompletableFuture.supplyAsync(() -> CompletableFutureDemoSupport.fetchUser(id), vtPool)
                            .whenComplete((user, ex) -> DemoLogger.info(
                                    "Virtual thread stage on " + Thread.currentThread().getName()));
            cf.join();
        } finally {
            vtPool.shutdown();
        }
    }

    private static ExecutorService tryCreateVirtualThreadExecutor() {
        try {
            Method method = Executors.class.getMethod("newVirtualThreadPerTaskExecutor");
            Object executor = method.invoke(null);
            if (executor instanceof ExecutorService service) {
                return service;
            }
        } catch (Exception ignored) {
            // Intentionally ignored: method absent on pre-Java-21 runtimes.
        }
        return null;
    }
}
