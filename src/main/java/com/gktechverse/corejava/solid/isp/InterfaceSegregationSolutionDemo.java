package com.gktechverse.corejava.solid.isp;

/**
 * ISP solution-only demo.
 */
public class InterfaceSegregationSolutionDemo {

    public static void main(String[] args) {
        com.gktechverse.corejava.solid.isp.solution.Payment upi =
                new com.gktechverse.corejava.solid.isp.solution.UpiPaymentService();
        upi.pay();

        com.gktechverse.corejava.solid.isp.solution.CreditCardService card =
                new com.gktechverse.corejava.solid.isp.solution.CreditCardService();
        card.pay();
        card.refund();
        card.generateInvoice();
    }
}
