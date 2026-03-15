package com.gktechverse.corejava.multithreading;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Enterprise-style deadlock demonstrations with bad and good coding approaches.
 */
public class DeadlockEnterpriseUseCasesDemo {

    public static void main(String[] args) {
        System.out.println("\n=== Multithreading: DEADLOCK Detailed Explanation ===");
        explainDeadlockBasics();

        runCase1BadCode();
        runCase1GoodCode();

        runCase2BadCode();
        runCase2GoodCode();
    }

    private static void explainDeadlockBasics() {
        System.out.println("\nWhat is deadlock?");
        System.out.println("Deadlock is a state where two or more threads wait forever for each other to release resources.");
        System.out.println("Why do threads wait?");
        System.out.println("- Because critical resources must be protected with locks to avoid data corruption.");
        System.out.println("- A thread can hold lock A and wait for lock B, while another thread holds lock B and waits for lock A.");
        System.out.println("Result: No thread can progress; request processing stalls.");
    }

    // ----------------------------------------------------------------
    // Use Case 1: Banking settlement service
    // ----------------------------------------------------------------

    private static void runCase1BadCode() {
        System.out.println("\n--- Use Case 1 (BAD): Inter-bank settlement deadlock ---");
        System.out.println("Scenario: Transfer jobs lock source and destination ledger in opposite order.");

        Object bankALedgerLock = new Object();
        Object bankBLedgerLock = new Object();

        Thread transferAtoB = new Thread(() -> {
            synchronized (bankALedgerLock) {
                sleep(120);
                synchronized (bankBLedgerLock) {
                    System.out.println("A->B transfer completed (this line should not print in deadlock run).");
                }
            }
        }, "Transfer-A-to-B");

        Thread transferBtoA = new Thread(() -> {
            synchronized (bankBLedgerLock) {
                sleep(120);
                synchronized (bankALedgerLock) {
                    System.out.println("B->A transfer completed (this line should not print in deadlock run).");
                }
            }
        }, "Transfer-B-to-A");

        transferAtoB.setDaemon(true);
        transferBtoA.setDaemon(true);

        transferAtoB.start();
        transferBtoA.start();

        join(transferAtoB, 450);
        join(transferBtoA, 450);

        if (transferAtoB.isAlive() && transferBtoA.isAlive()) {
            System.out.println("Deadlock detected in demo: both transfer threads are still waiting.");
            System.out.println("Business impact: payment queue grows, settlement SLA is breached.");
        }
    }

    private static void runCase1GoodCode() {
        System.out.println("\n--- Use Case 1 (GOOD): Consistent lock ordering ---");
        System.out.println("Fix: Acquire locks in one global order (alphabetical/resource id order).\n");

        Ledger bankA = new Ledger("BANK-A");
        Ledger bankB = new Ledger("BANK-B");

        Thread transferAtoB = new Thread(() -> orderedTransfer(bankA, bankB, "A->B"), "Safe-Transfer-A-to-B");
        Thread transferBtoA = new Thread(() -> orderedTransfer(bankB, bankA, "B->A"), "Safe-Transfer-B-to-A");

        transferAtoB.start();
        transferBtoA.start();

        join(transferAtoB, 1500);
        join(transferBtoA, 1500);

        System.out.println("Both transfers finished without deadlock using deterministic lock order.");
    }

    private static void orderedTransfer(Ledger from, Ledger to, String transferLabel) {
        Ledger first = from.name.compareTo(to.name) <= 0 ? from : to;
        Ledger second = first == from ? to : from;

        synchronized (first.lock) {
            sleep(50);
            synchronized (second.lock) {
                System.out.println("Transfer " + transferLabel + " completed safely.");
            }
        }
    }

    private static final class Ledger {
        private final String name;
        private final Object lock = new Object();

        private Ledger(String name) {
            this.name = name;
        }
    }

    // ----------------------------------------------------------------
    // Use Case 2: E-commerce order + inventory microservices
    // ----------------------------------------------------------------

    private static void runCase2BadCode() {
        System.out.println("\n--- Use Case 2 (BAD): Order and inventory update deadlock ---");
        System.out.println("Scenario: Order thread locks ORDER then INVENTORY, reconciliation thread locks INVENTORY then ORDER.");

        Object orderLock = new Object();
        Object inventoryLock = new Object();

        Thread placeOrderThread = new Thread(() -> {
            synchronized (orderLock) {
                sleep(100);
                synchronized (inventoryLock) {
                    System.out.println("Order placed (unexpected in deadlock run).");
                }
            }
        }, "Place-Order");

        Thread reconcileThread = new Thread(() -> {
            synchronized (inventoryLock) {
                sleep(100);
                synchronized (orderLock) {
                    System.out.println("Reconciliation done (unexpected in deadlock run).");
                }
            }
        }, "Reconcile-Inventory");

        placeOrderThread.setDaemon(true);
        reconcileThread.setDaemon(true);

        placeOrderThread.start();
        reconcileThread.start();

        join(placeOrderThread, 450);
        join(reconcileThread, 450);

        if (placeOrderThread.isAlive() && reconcileThread.isAlive()) {
            System.out.println("Deadlock detected in demo: checkout and reconciliation are blocked.");
            System.out.println("Business impact: users cannot place orders, stock appears frozen.");
        }
    }

    private static void runCase2GoodCode() {
        System.out.println("\n--- Use Case 2 (GOOD): Timed lock + retry/backoff ---");
        System.out.println("Fix: Use tryLock with timeout, release partial lock, then retry.");

        Lock orderLock = new ReentrantLock();
        Lock inventoryLock = new ReentrantLock();

        Thread placeOrderThread = new Thread(() -> processWithTimeout("PlaceOrder", orderLock, inventoryLock),
                "Safe-Place-Order");
        Thread reconcileThread = new Thread(() -> processWithTimeout("Reconcile", inventoryLock, orderLock),
                "Safe-Reconcile");

        placeOrderThread.start();
        reconcileThread.start();

        join(placeOrderThread, 3000);
        join(reconcileThread, 3000);

        System.out.println("Both business flows complete without infinite waiting using timeout + retry.");
    }

    private static void processWithTimeout(String processName, Lock firstLock, Lock secondLock) {
        for (int attempt = 1; attempt <= 6; attempt++) {
            boolean firstAcquired = false;
            boolean secondAcquired = false;
            try {
                firstAcquired = firstLock.tryLock(250, TimeUnit.MILLISECONDS);
                if (!firstAcquired) {
                    continue;
                }

                sleep(40);
                secondAcquired = secondLock.tryLock(250, TimeUnit.MILLISECONDS);
                if (secondAcquired) {
                    System.out.println(processName + " completed on attempt " + attempt + " using both locks.");
                    return;
                }
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                System.out.println(processName + " interrupted.");
                return;
            } finally {
                if (secondAcquired) {
                    secondLock.unlock();
                }
                if (firstAcquired) {
                    firstLock.unlock();
                }
            }

            sleep(70L * attempt);
        }

        System.out.println(processName + " could not complete after retries, but system avoided hard deadlock.");
    }

    private static void join(Thread thread, long timeoutMillis) {
        try {
            thread.join(timeoutMillis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.out.println("Main thread interrupted while waiting for " + thread.getName());
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }
}
