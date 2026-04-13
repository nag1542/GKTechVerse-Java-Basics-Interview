package com.gktechverse.corejava.solid.lsp;

public class WithdrawalService {

    public void processMonthlyCharge(WithdrawableAccount account, double amount) {
        account.withdraw(amount);
        System.out.println("Charge processed. Remaining balance: " + account.getBalance());
    }
}
