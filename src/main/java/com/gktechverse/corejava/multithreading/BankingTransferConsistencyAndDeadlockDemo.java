package com.gktechverse.corejava.multithreading;

/**
 * Demo-2: Banking transfer case for race condition (without lock), bad locking (deadlock),
 * and good locking (ordered lock acquisition).
 */
public class BankingTransferConsistencyAndDeadlockDemo {

    public static void main(String[] args) {
        System.out.println("\n=== Demo-2: Banking transfers at same millisecond ===");
        System.out.println("Imagine customer A and customer B sending money to each other at the same time.\n");

        runWithoutLockInconsistencyDemo();
        runBadLockDeadlockDemo();
        runGoodOrderedLockDemo();
    }

    private static void runWithoutLockInconsistencyDemo() {
        System.out.println("--- 1) WITHOUT LOCK: Balance inconsistency demo ---");
        System.out.println("Problem: debit/credit is read-modify-write; concurrent updates can overwrite each other.");

        boolean inconsistencyFound = false;

        for (int attempt = 1; attempt <= 8; attempt++) {
            Account a = new Account("A", 1_000);
            Account b = new Account("B", 1_000);

            Thread t1 = new Thread(() -> transferUnsafe(a, b, 10, 3_000), "Unsafe-A-to-B");
            Thread t2 = new Thread(() -> transferUnsafe(b, a, 10, 3_000), "Unsafe-B-to-A");

            t1.start();
            t2.start();
            join(t1, 6000);
            join(t2, 6000);

            int finalTotal = a.balance + b.balance;
            if (finalTotal != 2_000) {
                inconsistencyFound = true;
                System.out.println("Inconsistency observed at attempt " + attempt + ":");
                System.out.println("A=" + a.balance + ", B=" + b.balance + ", Total=" + finalTotal + " (expected 2000)");
                break;
            }
        }

        if (!inconsistencyFound) {
            System.out.println("No mismatch observed in this run, but code is still unsafe and can fail under load.");
        }
    }

    private static void runBadLockDeadlockDemo() {
        System.out.println("\n--- 2) BAD LOCKING: Deadlock demo ---");
        System.out.println("Problem: A->B locks A then B, while B->A locks B then A.");

        Account a = new Account("A", 1_000);
        Account b = new Account("B", 1_000);

        Thread t1 = new Thread(() -> transferWithBadLockOrder(a, b, 100), "BadLock-A-to-B");
        Thread t2 = new Thread(() -> transferWithBadLockOrder(b, a, 100), "BadLock-B-to-A");

        t1.setDaemon(true);
        t2.setDaemon(true);

        t1.start();
        t2.start();

        join(t1, 800);
        join(t2, 800);

        if (t1.isAlive() && t2.isAlive()) {
            System.out.println("Deadlock happened: both threads are waiting forever.");
            System.out.println("Business issue: transfers stop, requests timeout, customer trust drops.");
        }
    }

    private static void runGoodOrderedLockDemo() {
        System.out.println("\n--- 3) GOOD LOCKING: Global lock order solution ---");
        System.out.println("Solution: always lock accounts in deterministic order (by account id).");

        Account a = new Account("A", 1_000);
        Account b = new Account("B", 1_000);

        Thread t1 = new Thread(() -> transferWithOrderedLocks(a, b, 100), "GoodLock-A-to-B");
        Thread t2 = new Thread(() -> transferWithOrderedLocks(b, a, 100), "GoodLock-B-to-A");

        t1.start();
        t2.start();

        join(t1, 2000);
        join(t2, 2000);

        System.out.println("Transfer completed safely without deadlock.");
        System.out.println("Final balances: A=" + a.balance + ", B=" + b.balance + ", Total=" + (a.balance + b.balance));
    }

    private static void transferUnsafe(Account from, Account to, int amount, int iterations) {
        for (int i = 0; i < iterations; i++) {
            int fromBalance = from.balance;
            pause(1);
            from.balance = fromBalance - amount;

            int toBalance = to.balance;
            pause(1);
            to.balance = toBalance + amount;
        }
    }

    private static void transferWithBadLockOrder(Account from, Account to, int amount) {
        synchronized (from.lock) {
            pause(150);
            synchronized (to.lock) {
                from.balance -= amount;
                to.balance += amount;
            }
        }
    }

    private static void transferWithOrderedLocks(Account from, Account to, int amount) {
        Account first = from.id.compareTo(to.id) <= 0 ? from : to;
        Account second = first == from ? to : from;

        synchronized (first.lock) {
            pause(80);
            synchronized (second.lock) {
                from.balance -= amount;
                to.balance += amount;
            }
        }
    }

    private static final class Account {
        private final String id;
        private int balance;
        private final Object lock = new Object();

        private Account(String id, int balance) {
            this.id = id;
            this.balance = balance;
        }
    }

    private static void join(Thread thread, long millis) {
        try {
            thread.join(millis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private static void pause(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
