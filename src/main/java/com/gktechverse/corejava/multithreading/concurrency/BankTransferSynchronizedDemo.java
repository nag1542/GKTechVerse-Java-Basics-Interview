package com.gktechverse.corejava.multithreading.concurrency;

import java.util.concurrent.CountDownLatch;

/**
 * Real-world transfer consistency example.
 */
public class BankTransferSynchronizedDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\n=== BankTransferSynchronizedDemo ===");
        runUnsafeTransfer();
        runSafeTransfer();
    }

    private static void runUnsafeTransfer() throws InterruptedException {
        System.out.println("\n--- PROBLEM: transfer without synchronization ---");
        UnsafeAccount from = new UnsafeAccount("A", 1_000);
        UnsafeAccount to = new UnsafeAccount("B", 1_000);

        runTransferLoad(() -> unsafeTransfer(from, to, 1), () -> unsafeTransfer(to, from, 1));

        int total = from.balance + to.balance;
        System.out.println("Final A=" + from.balance + ", B=" + to.balance + ", total=" + total);
        System.out.println("// WHY IT FAILS: read-modify-write is interleaved; lost updates cause inconsistency.");
    }

    private static void runSafeTransfer() throws InterruptedException {
        System.out.println("\n--- SOLUTION: synchronized transfer ---");
        SafeAccount from = new SafeAccount("A", 1_000);
        SafeAccount to = new SafeAccount("B", 1_000);

        runTransferLoad(() -> safeTransfer(from, to, 1), () -> safeTransfer(to, from, 1));

        int total = from.getBalance() + to.getBalance();
        System.out.println("Final A=" + from.getBalance() + ", B=" + to.getBalance() + ", total=" + total);
        System.out.println("// EXPECTED OUTPUT: total remains 2000.");
    }

    private static void runTransferLoad(Runnable r1, Runnable r2) throws InterruptedException {
        int loops = 100_000;
        CountDownLatch latch = new CountDownLatch(2);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < loops; i++) r1.run();
            latch.countDown();
        }, "transfer-1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < loops; i++) r2.run();
            latch.countDown();
        }, "transfer-2");

        t1.start();
        t2.start();
        latch.await();
    }

    private static void unsafeTransfer(UnsafeAccount from, UnsafeAccount to, int amount) {
        if (from.balance >= amount) {
            int fromNew = from.balance - amount;
            sleep(0, 500_000); // widen race window
            int toNew = to.balance + amount;
            from.balance = fromNew;
            to.balance = toNew;
        }
    }

    private static void safeTransfer(SafeAccount from, SafeAccount to, int amount) {
        SafeAccount first = from.name.compareTo(to.name) < 0 ? from : to;
        SafeAccount second = first == from ? to : from;

        synchronized (first) {
            synchronized (second) {
                if (from.getBalance() >= amount) {
                    from.debit(amount);
                    sleep(0, 500_000);
                    to.credit(amount);
                }
            }
        }
    }

    private static final class UnsafeAccount {
        private final String name;
        private int balance;

        private UnsafeAccount(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }
    }

    private static final class SafeAccount {
        private final String name;
        private int balance;

        private SafeAccount(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        private int getBalance() {
            return balance;
        }

        private void debit(int amount) {
            balance -= amount;
        }

        private void credit(int amount) {
            balance += amount;
        }
    }

    private static void sleep(long millis, int nanos) {
        try {
            Thread.sleep(millis, nanos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
