package com.gktechverse.corejava.solid.isp;

/**
 * ISP problem-only demo.
 */
public class InterfaceSegregationProblemDemo {

    public static void main(String[] args) {
        com.gktechverse.corejava.solid.isp.problem.UpiPaymentService badUpi =
                new com.gktechverse.corejava.solid.isp.problem.UpiPaymentService();

        badUpi.pay();
        try {
            badUpi.generateInvoice();
        } catch (UnsupportedOperationException ex) {
            System.out.println("ISP broken (fat interface): " + ex.getMessage());
        }
    }
}
