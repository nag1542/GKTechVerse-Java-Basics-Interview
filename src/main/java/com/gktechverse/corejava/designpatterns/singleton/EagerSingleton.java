package com.gktechverse.corejava.designpatterns.singleton;

/**
 * Eager initialization singleton.
 * Thread-safe because instance is created during class loading.
 */
public final class EagerSingleton {

    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
