package com.gktechverse.corejava.designpatterns.singleton;

/**
 * Lazy initialization singleton.
 * Not thread-safe by design (for demonstration of race condition).
 */
public final class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (instance == null) {
            // Intentionally keep a tiny delay to make race condition easier to observe.
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            instance = new LazySingleton();
        }
        return instance;
    }
}
