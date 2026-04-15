package com.gktechverse.corejava.designpatterns.singleton;

/**
 * Double-checked locking singleton.
 * Thread-safe and avoids synchronization overhead after initialization.
 */
public final class DoubleCheckedSingleton {

    private static volatile DoubleCheckedSingleton instance;

    private DoubleCheckedSingleton() {
    }

    public static DoubleCheckedSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckedSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedSingleton();
                }
            }
        }
        return instance;
    }
}
