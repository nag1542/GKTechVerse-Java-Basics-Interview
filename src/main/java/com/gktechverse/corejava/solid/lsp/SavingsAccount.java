package com.gktechverse.corejava.solid.lsp;

public class SavingsAccount implements WithdrawableAccount {
    private double balance;

    public SavingsAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        balance -= amount;
    }

    @Override
    public double getBalance() {
        return balance;
    }
}
