package com.gktechverse.corejava.multithreading;

/**
 * 1) How to create a thread in enterprise-style scenarios.
 */
public class ThreadCreationEnterpriseDemo {

    public static void run() {
        DemoLogger.info("\n=== 1) Thread creation: payment, audit, and notification flows ===");

        // Approach A: Extend Thread (rare in enterprise code, but interview important).
        Thread paymentThread = new PaymentSettlementThread("ORD-7781");

        // Approach B: Pass Runnable to Thread (common when task and thread are separated).
        Runnable auditRunnable = () -> {
            DemoLogger.info("Audit trail persisted for ORD-7781");
            sleep(120);
            DemoLogger.info("Audit trail async publish completed");
        };
        Thread auditThread = new Thread(auditRunnable, "audit-worker-1");

        paymentThread.start();
        auditThread.start();

        join(paymentThread);
        join(auditThread);
        DemoLogger.info("Thread creation demo completed.");
    }

    private static final class PaymentSettlementThread extends Thread {
        private final String orderId;

        private PaymentSettlementThread(String orderId) {
            super("payment-settlement-1");
            this.orderId = orderId;
        }

        @Override
        public void run() {
            DemoLogger.info("Started settlement for order " + orderId);
            try {
				sleep(150);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            DemoLogger.info("Settlement done for order " + orderId);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
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
