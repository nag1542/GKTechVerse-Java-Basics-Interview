package com.gktechverse.corejava.multithreading;

import com.gktechverse.corejava.multithreading.completablefuture.CompletableFutureInterviewRunner;

/**
 * Interview-video friendly sequence of multithreading demonstrations.
 */
public class MultithreadingEnterpriseVideoRunner {

    public static void main(String[] args) {
        DemoLogger.info("Starting multithreading enterprise video demonstrations.");

        ThreadCreationEnterpriseDemo.run();
        ThreadLifeCycleEnterpriseDemo.run();
        CallableRunnableEnterpriseDemo.run();
        CompletableFutureInterviewRunner.run();

        DemoLogger.info("Completed multithreading enterprise video demonstrations.");
    }
}
