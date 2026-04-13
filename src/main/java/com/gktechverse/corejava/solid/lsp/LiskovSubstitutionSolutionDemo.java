package com.gktechverse.corejava.solid.lsp;

/**
 * LSP solution-only demo.
 */
public class LiskovSubstitutionSolutionDemo {

    public static void main(String[] args) {
        WithdrawalService goodProcessor = new WithdrawalService();

        WithdrawableAccount savings = new SavingsAccount(8000);
        goodProcessor.processMonthlyCharge(savings, 1200);

        Account fixedDeposit = new FixedDepositAccount(15000);
        fixedDeposit.deposit(5000);
        System.out.println("Fixed deposit balance (no withdrawal contract): " + fixedDeposit.getBalance());
    }
}
