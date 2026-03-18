package com.gktechverse.corejava.multithreading;

/**
 * 2) Thread life cycle with easy-to-visualize states.
 */
public class ThreadLifeCycleEnterpriseDemo {

    public static void run() {
        DemoLogger.info("\n=== 2) Thread life cycle: NEW -> RUNNABLE -> TIMED_WAITING -> TERMINATED ===");

        Thread reportThread = new Thread(() -> {
            DemoLogger.info("RUNNABLE: report generation started");
            sleep(200);
            DemoLogger.info("After sleep, back to RUNNABLE and finishing now");
        }, "report-generator-1");

        DemoLogger.info("Before start() state = " + reportThread.getState()); // NEW
        reportThread.start();

        pause(40);
        DemoLogger.info("Soon after start() state = " + reportThread.getState());

        pause(80);
        DemoLogger.info("During sleep state = " + reportThread.getState()); // TIMED_WAITING expected

        join(reportThread);
        DemoLogger.info("After completion state = " + reportThread.getState()); // TERMINATED
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private static void pause(long millis) {
        sleep(millis);
    }

    private static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
