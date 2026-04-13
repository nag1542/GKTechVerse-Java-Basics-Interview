package com.gktechverse.corejava.solid.lsp.clear;

/**
 * Solution demo: withdrawal flow accepts only withdraw-capable accounts.
 */
public class LspBankAccountSolutionDemo {

    public static void main(String[] args) {
        LspBankAccountSolutionModel model = new LspBankAccountSolutionModel();

        LspBankAccountSolutionModel.SavingsAccount savings = new LspBankAccountSolutionModel.SavingsAccount();
        savings.deposit(8000);
        model.processWithdrawal(savings);
        System.out.println("Savings balance after withdrawal: " + savings.getBalance());

        LspBankAccountSolutionModel.FixedDepositAccount fixedDeposit = new LspBankAccountSolutionModel.FixedDepositAccount();
        fixedDeposit.deposit(12000);
        System.out.println("Fixed deposit balance (withdraw not exposed): " + fixedDeposit.getBalance());
    }
}
