package com.gktechverse.corejava.multithreading.completablefuture;

import com.gktechverse.corejava.multithreading.DemoLogger;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Shared fake service methods and data models for CompletableFuture demos.
 */
final class CompletableFutureDemoSupport {

    private CompletableFutureDemoSupport() {
    }

    static User fetchUser(String userId) {
        sleep(350);
        return new User(userId, "Riya Sharma");
    }

    static Orders fetchOrders(String userId) {
        sleep(200);
        return new Orders(List.of("Keyboard", "Mouse", "USB-C Hub"));
    }

    static Prefs fetchPrefs(String userId) {
        sleep(120);
        return new Prefs("dark", "en-US");
    }

    static Metrics fetchMetrics(String userId) {
        sleep(100);
        return new Metrics(42, 91.2);
    }

    static CompletableFuture<List<String>> fetchOrdersAsync(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(180);
            return List.of("Order-" + userId + "-101", "Order-" + userId + "-102");
        });
    }

    static ApiResponse buildResponse(List<String> orderIds) {
        return new ApiResponse(true, "Fetched " + orderIds.size() + " orders", orderIds);
    }

    static User guestUser() {
        return new User("guest", "Guest User");
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            DemoLogger.warn("Thread interrupted while simulating I/O");
        }
    }

    record User(String id, String name) {
    }

    record Orders(List<String> items) {
    }

    record Prefs(String theme, String locale) {
    }

    record Metrics(int loginCount, double score) {
    }

    record UserProfile(User user, Prefs prefs) {
    }

    record Dashboard(User user, Orders orders, Metrics metrics) {
    }

    record ApiResponse(boolean success, String message, List<String> data) {
        static ApiResponse error(String message) {
            return new ApiResponse(false, message, List.of());
        }
    }
}
