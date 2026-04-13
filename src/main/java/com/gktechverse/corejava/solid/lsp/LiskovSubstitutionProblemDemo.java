package com.gktechverse.corejava.solid.lsp;

/**
 * LSP problem-only demo.
 */
public class LiskovSubstitutionProblemDemo {

    public static void main(String[] args) {
        LspViolationBankingExample.WithdrawalProcessor badProcessor = new LspViolationBankingExample.WithdrawalProcessor();

        LspViolationBankingExample.SavingsAccount goodSubstitute =
                new LspViolationBankingExample.SavingsAccount(5000);
        badProcessor.processMonthlyCharge(goodSubstitute, 500);

        LspViolationBankingExample.SavingsAccount badSubstitute =
                new LspViolationBankingExample.FixedDepositAccount(10000);
        try {
            badProcessor.processMonthlyCharge(badSubstitute, 1000);
        } catch (UnsupportedOperationException ex) {
            System.out.println("LSP broken: " + ex.getMessage());
        }
    }
}
