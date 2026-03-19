package com.gktechverse.corejava.multithreading;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 3) Callable vs Runnable and related production patterns.
 */
public class CallableRunnableEnterpriseDemo {

    public static void run() {
        DemoLogger.info("\n=== 3) Callable vs Runnable (enterprise scenarios) ===");
        showReturnTypeDifference();
        showCheckedExceptionDifference();
        showExecutorIntegrationWithRunnableAndCallable();
        showSilentFailureTrapAndFixes();
        showFanOutPatternUsingInvokeAll();
        showInvokeAnyPattern();
        showCallableVsSupplier();
        showCompletableFuturePattern();
        showRunnableWithVirtualThreadsIfAvailable();
    }

    private static void showReturnTypeDifference() {
        DemoLogger.info("\n-> Return type: Runnable returns nothing, Callable returns a value");

        Runnable runnable = () -> DemoLogger.info("Runnable processed audit event");
        Callable<Integer> callable = () -> 42;

        runnable.run();
        try {
            DemoLogger.info("Callable returned KPI score = " + callable.call());
        } catch (Exception exception) {
            DemoLogger.error("Unexpected failure while calling callable", exception);
        }
    }

    private static void showCheckedExceptionDifference() {
        DemoLogger.info("\n-> Checked exception: Callable can throw checked exceptions directly");

        Callable<String> remoteCall = () -> {
            throw new IOException("CRM endpoint temporarily unavailable");
        };

        try {
            remoteCall.call();
        } catch (Exception exception) {
            DemoLogger.warn("Caught checked exception from Callable: " + exception.getMessage());
        }

        DemoLogger.info("Runnable cannot declare checked exception in run(); developers often wrap it.");
    }

    private static void showExecutorIntegrationWithRunnableAndCallable() {
        DemoLogger.info("\n-> ExecutorService integration with Runnable and Callable");

        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            Future<?> runnableFuture = executor.submit(() -> DemoLogger.info("Runnable task: flush metrics batch"));
            Future<String> callableFuture = executor.submit(() -> "Callable task: invoice summary prepared");

            runnableFuture.get();
            DemoLogger.info("Runnable future get() returns: " + runnableFuture.get()); // null
            DemoLogger.info("Callable future get() returns: " + callableFuture.get());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException exception) {
            DemoLogger.error("Executor task failed", exception.getCause());
        } finally {
            shutdown(executor);
        }
    }

    private static void showSilentFailureTrapAndFixes() {
        DemoLogger.info("\n-> Trap: Runnable + thread pool can hide failure until Future.get() is inspected");

        ExecutorService trapExecutor = Executors.newSingleThreadExecutor();
        try {
            Future<?> future = trapExecutor.submit(() -> {
                DemoLogger.info("Runnable started and will fail now");
                throw new RuntimeException("Inventory DB write failed");
            });

            DemoLogger.warn("If we never call future.get(), failure can be missed in business logs.");
            try {
                future.get();
            } catch (ExecutionException exception) {
                DemoLogger.error("Observed failure only after Future.get()", exception.getCause());
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        } finally {
            shutdown(trapExecutor);
        }

        DemoLogger.info("Fix Option 1: Set UncaughtExceptionHandler for raw threads");
        Thread thread = new Thread(() -> {
            throw new IllegalStateException("Order indexing failed");
        }, "indexer-thread");
        thread.setUncaughtExceptionHandler((t, e) -> DemoLogger.error("Uncaught exception from " + t.getName(), e));
        thread.start();
        join(thread);

        DemoLogger.info("Fix Option 2: Use Callable and inspect typed Future result/errors");
        ExecutorService callableExecutor = Executors.newSingleThreadExecutor();
        try {
            Future<String> future = callableExecutor.submit(() -> {
                throw new IOException("Profile service unreachable");
            });
            future.get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException exception) {
            DemoLogger.error("Callable propagated error cleanly", exception.getCause());
        } finally {
            shutdown(callableExecutor);
        }
    }

    private static void showFanOutPatternUsingInvokeAll() {
        DemoLogger.info("\n-> Fan-Out pattern with invokeAll (real production style)");

        UserService userService = new UserService();
        List<String> userIds = List.of("u1", "u2", "u3", "u4", "u5");

        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {
            // Build one Callable per user
            List<Callable<UserProfile>> tasks = userIds.stream()
                    .map(id -> (Callable<UserProfile>) () -> userService.fetchProfile(id))
                    .collect(Collectors.toList());

            // Submit all and wait for all (max 5 seconds)
            List<Future<UserProfile>> futures = executor.invokeAll(tasks, 5, TimeUnit.SECONDS);

            List<UserProfile> profiles = new ArrayList<>();
            for (Future<UserProfile> future : futures) {
                try {
                    profiles.add(future.get());
                } catch (ExecutionException exception) {
                    DemoLogger.error("Profile fetch failed", exception.getCause());
                    // Decision point: skip/rethrow/return partial data.
                } catch (CancellationException exception) {
                    DemoLogger.warn("Task cancelled, likely timed out");
                }
            }

            DemoLogger.info("Profiles collected (partial allowed) -> " + profiles);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        } finally {
            shutdown(executor);
        }
    }

    private static void showInvokeAnyPattern() {
        DemoLogger.info("\n-> invokeAny: first successful response wins (multi-region read)");

        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            List<Callable<String>> regionTasks = Arrays.asList(
                    () -> {
                        TimeUnit.MILLISECONDS.sleep(260);
                        return "ap-south profile cache";
                    },
                    () -> {
                        TimeUnit.MILLISECONDS.sleep(120);
                        return "eu-west profile cache";
                    },
                    () -> {
                        TimeUnit.MILLISECONDS.sleep(200);
                        return "us-east profile cache";
                    }
            );

            String winner = executor.invokeAny(regionTasks, 2, TimeUnit.SECONDS);
            DemoLogger.info("First successful region = " + winner);
        } catch (Exception exception) {
            DemoLogger.error("invokeAny failed", exception);
        } finally {
            shutdown(executor);
        }
    }

    private static void showCallableVsSupplier() {
        DemoLogger.info("\n-> Callable vs Supplier");

        Supplier<String> supplier = () -> "Supplier used in synchronous/lazy composition";
        Callable<String> callable = () -> "Callable used with ExecutorService + can throw checked exceptions";

        DemoLogger.info(supplier.get());
        try {
            DemoLogger.info(callable.call());
        } catch (Exception exception) {
            DemoLogger.error("Callable failed unexpectedly", exception);
        }
    }

    private static void showCompletableFuturePattern() {
        DemoLogger.info("\n-> CompletableFuture: non-blocking pipeline for recommendation API");

        UserService userService = new UserService();
        CompletableFuture<String> recommendationFuture = CompletableFuture
                .supplyAsync(() -> userService.fetchRecommendation("u2"))
                .thenApply(value -> value + " | enrichment=top-picks")
                .exceptionally(exception -> "fallback-recommendation");

        DemoLogger.info("CompletableFuture final response = " + recommendationFuture.join());
    }

    private static void showRunnableWithVirtualThreadsIfAvailable() {
        DemoLogger.info("\n-> Runnable with virtual threads (available in Java 21+) ");

        try {
            Method ofVirtual = Thread.class.getMethod("ofVirtual");
            Object builder = ofVirtual.invoke(null);
            Method name = builder.getClass().getMethod("name", String.class);
            Object namedBuilder = name.invoke(builder, "virtual-order-notifier");
            Method start = namedBuilder.getClass().getMethod("start", Runnable.class);

            Runnable task = () -> DemoLogger.info("Virtual thread sent notification to customer");
            Thread virtualThread = (Thread) start.invoke(namedBuilder, task);
            join(virtualThread);
            DemoLogger.info("Virtual thread demo completed.");
        } catch (NoSuchMethodException exception) {
            DemoLogger.warn("Virtual threads are not available on this runtime (likely Java 17).");
        } catch (Exception exception) {
            DemoLogger.error("Virtual thread demo failed", exception);
        }
    }

    private static void shutdown(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(3, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
