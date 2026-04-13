package com.gktechverse.corejava.solid.lsp.clear;

/**
 * Problem demo: demonstrates how substitution breaks at runtime.
 */
public class LspBankAccountProblemDemo {

    public static void main(String[] args) {
        LspBankAccountProblemScenario scenario = new LspBankAccountProblemScenario();

        LspBankAccountProblemScenario.BankAccount savings = new LspBankAccountProblemScenario.BankAccount();
        savings.deposit(5000);
        scenario.processWithdrawal(savings);
        System.out.println("Savings balance after withdrawal: " + savings.getBalance());

        LspBankAccountProblemScenario.BankAccount fixedDeposit = new LspBankAccountProblemScenario.FixedDepositAccount();
        fixedDeposit.deposit(10000);

        try {
            scenario.processWithdrawal(fixedDeposit);
        } catch (UnsupportedOperationException ex) {
            System.out.println("LSP broken: " + ex.getMessage());
        }
    }
}
