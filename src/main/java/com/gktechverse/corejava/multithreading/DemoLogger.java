package com.gktechverse.corejava.multithreading;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Small logger utility to keep console output readable for interview videos.
 */
public final class DemoLogger {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private DemoLogger() {
    }

    public static void info(String message) {
        log("INFO", message, null);
    }

    public static void warn(String message) {
        log("WARN", message, null);
    }

    public static void error(String message, Throwable throwable) {
        log("ERROR", message, throwable);
    }

    private static void log(String level, String message, Throwable throwable) {
        String line = String.format("%s [%s] [thread=%s] %s",
                LocalTime.now().format(TIME_FORMATTER),
                level,
                Thread.currentThread().getName(),
                message);
        System.out.println(line);
        if (throwable != null) {
            System.out.println("    cause=" + throwable.getClass().getSimpleName() + " -> " + throwable.getMessage());
        }
    }
}
