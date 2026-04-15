package com.gktechverse.corejava.designpatterns.singleton;

/**
 * Synchronized method singleton.
 * Thread-safe, but synchronization cost is paid on every call.
 */
public final class SyncSingleton {

    private static SyncSingleton instance;

    private SyncSingleton() {
    }

    public static synchronized SyncSingleton getInstance() {
        if (instance == null) {
            instance = new SyncSingleton();
        }
        return instance;
    }
}
