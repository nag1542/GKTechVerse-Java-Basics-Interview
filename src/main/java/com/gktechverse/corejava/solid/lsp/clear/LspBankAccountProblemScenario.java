package com.gktechverse.corejava.solid.lsp.clear;

/**
 * Clear LSP violation example using BankAccount and FixedDepositAccount.
 */
public class LspBankAccountProblemScenario {

    public static class BankAccount {
        protected double balance;

        public void deposit(double amount) {
            balance += amount;
        }

        public void withdraw(double amount) {
            balance -= amount;
        }

        public double getBalance() {
            return balance;
        }
    }

    public static class FixedDepositAccount extends BankAccount {
        @Override
        public void withdraw(double amount) {
            throw new UnsupportedOperationException("Withdrawal not allowed");
        }
    }

    public void processWithdrawal(BankAccount account) {
        account.withdraw(1000);
    }
}
