package com.gktechverse.corejava.solid.lsp;

/**
 * Fixed deposit supports deposit/balance view, but not withdrawal before maturity.
 * It does NOT implement WithdrawableAccount, so substitution remains valid.
 */
public class FixedDepositAccount implements Account {
    private double balance;

    public FixedDepositAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public void deposit(double amount) {
        balance += amount;
    }

    @Override
    public double getBalance() {
        return balance;
    }
}
