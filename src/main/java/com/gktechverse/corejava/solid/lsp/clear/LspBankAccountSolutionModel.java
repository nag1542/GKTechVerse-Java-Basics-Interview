package com.gktechverse.corejava.solid.lsp.clear;

/**
 * LSP solution using capability interfaces.
 */
public class LspBankAccountSolutionModel {

    public interface Account {
        void deposit(double amount);
    }

    public interface Withdrawable {
        void withdraw(double amount);
    }

    public static class SavingsAccount implements Account, Withdrawable {
        private double balance;

        @Override
        public void deposit(double amount) {
            balance += amount;
        }

        @Override
        public void withdraw(double amount) {
            balance -= amount;
        }

        public double getBalance() {
            return balance;
        }
    }

    public static class FixedDepositAccount implements Account {
        private double balance;

        @Override
        public void deposit(double amount) {
            balance += amount;
        }

        public double getBalance() {
            return balance;
        }
    }

    public void processWithdrawal(Withdrawable account) {
        account.withdraw(1000);
    }
}
