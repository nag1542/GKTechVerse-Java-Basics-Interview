package com.gktechverse.corejava.solid.lsp;

/**
 * Problem example (LSP violation):
 * FixedDepositAccount cannot truly behave as a SavingsAccount because withdraw is not valid.
 */
public class LspViolationBankingExample {

    static class SavingsAccount {
        protected double balance;

        SavingsAccount(double initialBalance) {
            this.balance = initialBalance;
        }

        void deposit(double amount) {
            balance += amount;
        }

        void withdraw(double amount) {
            if (amount > balance) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            balance -= amount;
        }

        double getBalance() {
            return balance;
        }
    }

    static class FixedDepositAccount extends SavingsAccount {
        FixedDepositAccount(double initialBalance) {
            super(initialBalance);
        }

        @Override
        void withdraw(double amount) {
            throw new UnsupportedOperationException("Withdrawals are not allowed before maturity.");
        }
    }

    static class WithdrawalProcessor {
        void processMonthlyCharge(SavingsAccount account, double amount) {
            account.withdraw(amount);
            System.out.println("Charge processed. Remaining balance: " + account.getBalance());
        }
    }
}
