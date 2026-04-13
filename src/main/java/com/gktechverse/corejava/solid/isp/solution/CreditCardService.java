package com.gktechverse.corejava.solid.isp.solution;

public class CreditCardService implements Payment, Refundable, InvoiceService {

    @Override
    public void pay() {
        System.out.println("[CARD] Card payment authorized and captured.");
    }

    @Override
    public void refund() {
        System.out.println("[CARD] Refund initiated to card source.");
    }

    @Override
    public void generateInvoice() {
        System.out.println("[CARD] Tax invoice generated and stored.");
    }
}
