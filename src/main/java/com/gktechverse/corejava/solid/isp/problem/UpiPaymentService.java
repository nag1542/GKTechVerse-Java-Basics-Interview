package com.gktechverse.corejava.solid.isp.problem;

public class UpiPaymentService implements FatPaymentService {

    @Override
    public void pay() {
        System.out.println("[UPI] Payment completed via UPI app.");
    }

    @Override
    public void refund() {
        throw new UnsupportedOperationException("UPI service in this module does not support refunds.");
    }

    @Override
    public void generateInvoice() {
        throw new UnsupportedOperationException("UPI service cannot generate invoice in this integration.");
    }

    @Override
    public void sendReceipt() {
        throw new UnsupportedOperationException("UPI service cannot send receipts directly.");
    }
}
